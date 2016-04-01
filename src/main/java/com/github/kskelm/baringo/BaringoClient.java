/**
 * Imgur API service
 */
package com.github.kskelm.baringo;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import com.github.kskelm.baringo.model.Account;
import com.github.kskelm.baringo.model.ImgurResponseWrapper;
import com.github.kskelm.baringo.util.BaringoApiException;
import com.github.kskelm.baringo.util.RetrofittedImgur;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.Response;


/**
 * @author kskelm
 *
 */
public class BaringoClient {

	public static final String PROPERTY_CLIENT_ID     = "baringoclient.clientid";
	public static final String PROPERTY_CLIENT_SECRET = "baringoclient.clientsecret";

	/**
	 * Returns the AccountService object used to execute account-related operations
	 * @return the account service
	 */
	public AccountService accountService() {
		return aSvc;
	} // accountService

	/**
	 * Returns the ImageService object used to execute image-related operations
	 * @return the image service
	 */
	public ImageService imageService() {
		return iSvc;
	} // imageService

	/**
	 * Returns the GalleryService object used to execute gallery-related operations
	 * @return the gallery service
	 */
	public GalleryService galleryService() {
		return gSvc;
	} // galleryService

	/**
	 * Returns the AuthService object used to execute gallery-related operations
	 * @return the gallery service
	 */
	public AuthService authService() {
		return authSvc;
	} // authService

	/**
	 * Returns an object that describes the remaining quotas left over for this client
	 * @return quota information
	 */
	public Quota getQuota() {
		return quota;
	} // getQuota

	/**
	 * As a convenience measure, return the username of the logged-in user
	 * @return user name or null if none
	 */
	public String getAuthenticatedUserName() {
		return authSvc.getAuthenticatedUserName();
	}

	/**
	 * Returns the Account object for the account that's currently
	 * authenticated via OAuth2, or null if none.  This is a
	 * convenience method that caches, since it seems like something
	 * that might be requested frequently.
	 * @return the current Account
	 * @throws BaringoApiException 
	 */
	public Account getAuthenticatedAccount() throws BaringoApiException {
		return authSvc.getAuthenticatedAccount();
	}


	/**
	 * This is used to construct a new BaringoClient
	 * @author kskelm
	 *
	 */
	public static class Builder {

		/**
		 * Sets the client id and secret, which are the minimum kind
		 * of Imgur authentication.  They give you access to only the
		 * publicly-accessible features of the site, not private details
		 * in a specific user's account. {Link http://api.imgur.com/}
		 * @param clientId - the client_id assigned by Imgur
		 * @param clientSecret - the client_secret assigned by Imgur
		 * @return This builder object
		 */
		public Builder clientAuth( String clientId, String clientSecret ) {
			this._clientId = clientId;
			this._clientSecret = clientSecret;

			return this;
		} // clientAuth


		/**
		 * Constructs the BaringoClient and returns it
		 * @return The Baringo client
		 * @throws BaringoApiException Unable to build the client
		 */
		public BaringoClient build() throws BaringoApiException {
			BaringoClient client = new BaringoClient( _clientId, _clientSecret );

			return client;
		} // build

		private String _clientId = null;
		private String _clientSecret = null;
	}

	// =========================================================

	//	/**
	//	 * Construct a client.  This is necessary before using
	//	 * any of the API calls.  It is advised to store clientId
	//	 * and clientSecret somewhere other than in your code.
	//	 * Note that logging in a user is a separate step that comes
	//	 * later.
	//	 * @param clientId - the clientID string for your client. If you haven't got one yet, <a href="https://api.imgur.com/oauth2/addclient">register</a>. You'll need to register as OAuth 2 without a callback URL.
	//	 * @param clientSecret- the clientID string for your client. If you haven't got one yet, <a href="https://api.imgur.com/oauth2/addclient">register</a>. You'll need to register as OAuth 2 without a callback URL.  THIS IS A SECRET- DO NOT SHARE IT. STORE THIS IN A SECURE PLACE.
	//	 * @throws BaringoApiException 
	//	 */
	protected BaringoClient( String clientId, String clientSecret ) throws BaringoApiException {
		if( clientId == null || clientSecret == null ) {
			throw new BaringoApiException( "Must have clientId and clientSecret to run Baringo.  See http://api.imgur.com/");
		} // if

		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.api = create();
	} // constructor


	protected RetrofittedImgur getApi() {
		return api;
	} // getApi

	protected <T> void throwOnWrapperError( Response<ImgurResponseWrapper<T>> resp ) throws BaringoApiException {
		if( resp.code() != 200 ) {
			throw new BaringoApiException( resp.raw().request().urlString()
					+ ": " +  resp.message(), resp.code() );
		} // if
		if( resp.body() == null ) {
			throw new BaringoApiException( "No response body found", 0 );
		} // if
		if( resp.body().getStatus() != 200 || !resp.body().isSuccess() ) {
			throw new BaringoApiException( "Unknown error", resp.body().getStatus() );
		} // if
	} // throwOnWrapperError

	private RetrofittedImgur create() {
		OkHttpClient client = new OkHttpClient();
		client.interceptors().add(new ImgurInterceptor());

		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateAdapter());

		// we pause right here to create the various topic-specific
		// services, giving them a chance to register any Gson
		// type adapters they're going to need.
		this.aSvc = new AccountService( this, gsonBuilder );
		this.iSvc = new ImageService( this, gsonBuilder );
		this.gSvc = new GalleryService( this, gsonBuilder );

		this.authSvc = new AuthService( this, clientId, clientSecret );

		// build the gson object
		final Gson gson = gsonBuilder.create();

		// start up the API client
		GsonConverterFactory gcf = GsonConverterFactory.create( gson );
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl( apiEndpoint )
				.addConverterFactory( gcf )
				.client(client)
				.build();

		return retrofit.create(RetrofittedImgur.class);
	}

	/**
	 * This handles our authentication and logging, mostly.
	 * @author kskelm
	 *
	 */
	private class ImgurInterceptor implements Interceptor {

		public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
			Request  request  = chain.request();

			log.fine( "API Call: " + request.url().toString() );
			request = authService().buildAuthenticatedRequest( request );
			//			request = request.newBuilder()
			//					.header( "Authorization", "Client-ID " + clientId )
			//					.header( "Authorization", "Bearer " + "9d1ec1e02c6b6fc1df19ec36146b3b8ca01b24de" )
			//					.build();

			com.squareup.okhttp.Response response = chain.proceed(request);

			updateQuota( response );
			return response;
		}
	}

	/**
	 * These define the headers that return relevant quota information
	 */
	private static final String HEADER_USER_CREDIT_RESET_DATE = "X-RateLimit-UserReset";
	private static final String HEADER_USER_CREDITS_ALLOCATED = "X-RateLimit-UserLimit";
	private static final String HEADER_USER_CREDITS_AVAILABLE = "X-RateLimit-UserRemaining";
	private static final String HEADER_APPLICATION_CREDITS_AVAILABLE = "X-RateLimit-ClientRemaining";
	private static final String HEADER_APPLICATION_CREDITS_ALLOCATED = "X-RateLimit-ClientLimit";
	private static final String HEADER_POST_CREDIT_RESET_DATE = "X-Post-Rate-Limit-Reset";
	private static final String HEADER_POST_CREDITS_ALLOCATED = "X-Post-Rate-Limit-Limit";
	private static final String HEADER_POST_CREDITS_AVAILABLE = "X-Post-Rate-Limit-Remaining";

	private void updateQuota( com.squareup.okhttp.Response response ) {
		String val = response.header( HEADER_USER_CREDIT_RESET_DATE );
		if( val != null ) {
			int valInt = Integer.parseInt( val );
			quota.setUserCreditResetDate( new Date( valInt ) );
		} // if
		val = response.header( HEADER_USER_CREDITS_ALLOCATED );
		if( val != null ) {
			int valInt = Integer.parseInt( val );
			quota.setUserCreditsAllocated(valInt);
		} // if
		val = response.header( HEADER_USER_CREDITS_AVAILABLE );
		if( val != null ) {
			int valInt = Integer.parseInt( val );
			quota.setUserCreditsAvailable(valInt);
		} // if
		val = response.header( HEADER_APPLICATION_CREDITS_AVAILABLE );
		if( val != null ) {
			int valInt = Integer.parseInt( val );
			quota.setApplicationCreditsAvailable(valInt);
		} // if
		val = response.header( HEADER_APPLICATION_CREDITS_ALLOCATED );
		if( val != null ) {
			int valInt = Integer.parseInt( val );
			quota.setApplicationCreditsAllocated(valInt);
		} // if
		val = response.header( HEADER_POST_CREDIT_RESET_DATE );
		if( val != null ) {
			int valInt = Integer.parseInt( val );
			quota.setPostCreditResetDate(new Date(valInt));
		} // if
		val = response.header( HEADER_POST_CREDITS_ALLOCATED );
		if( val != null ) {
			int valInt = Integer.parseInt( val );
			quota.setPostCreditsAllocated(valInt);
		} // if
		val = response.header( HEADER_POST_CREDITS_AVAILABLE );
		if( val != null ) {
			int valInt = Integer.parseInt( val );
			quota.setPostCreditsAvailable(valInt);
		} // if
	} // updateQuota

	/**
	 * Apparently standard Gson can't tolerate a unix timestamp
	 * representing a date object.  That's pretty much all we care
	 * about, so we're subclassing it.
	 */
	class DateAdapter extends TypeAdapter<Date> {

		@Override
		public void write(JsonWriter out, Date value) throws IOException {
			if( value == null ){
				out.nullValue();
				return;
			} // if
			out.value( value.getTime() / 1000);
		}

		@Override
		public Date read(JsonReader in) throws IOException {
			if (in.peek() == JsonToken.NULL) {
				in.nextNull();
				return null;
			} else if ( in.peek() == JsonToken.BOOLEAN ) {
				in.nextBoolean();  // throw it away
				return null;
			} // if-else

			return new Date( in.nextLong() * 1000 );
		}
	} // DateAdapter

	/**
	 * Used only for mocking during tests
	 * @param url - new endpoint
	 */
	public static void setEndpoint( String url ) {
		BaringoClient.apiEndpoint = url;
	} // setEndpoint

	// =============================================
	private RetrofittedImgur api = null;
	private String clientId = null;
	private String clientSecret = null;
	//	private Account authenticatedAccount = null;
	private static final Logger log = Logger.getLogger( BaringoClient.LOG_NAME );
	private Quota quota = new Quota();

	private AccountService aSvc = null;
	private ImageService   iSvc = null;
	private GalleryService gSvc = null;

	private AuthService authSvc = null;

	public static final String DEFAULT_IMGUR_BASE_URL = "https://api.imgur.com/";
	public static final String LOG_NAME = "ImgurApi";


	private static String apiEndpoint = DEFAULT_IMGUR_BASE_URL;

} // class BaringoClient
