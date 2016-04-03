package com.github.kskelm.baringo.test;


import com.github.kskelm.baringo.BaringoClient;
import com.github.kskelm.baringo.util.BaringoApiException;
import com.github.kskelm.baringo.util.BaringoAuthException;

/**
 * Parent class for tests that sets up client
 */
public class Setup {
	public static final String TEST_USER_NAME = "kskelmapitest";
	public static final String TEST_USER_NAME_2 = "kevinkelm";
	
	public static final String TEST_ALBUM_ID = "tZz2i";

	public static final String TEST_GALLERY_ID = "G3xkE";

	public static final String TEST_IMAGE_ID = "bHEb5Sw";
	public static final String TEST_IMAGE_ID_2 = "wH8KZXs";
	
	public static final long   TEST_COMMENT_ID = 620866057L;
	public static final long   TEST_COMMENT_ID_2 = 617736106L;
	
	// for use with mocking only
	public static final String TEST_ENDPOINT = "http://localhost:8080";
	
    public Setup() {
        String clientId = System.getProperty( BaringoClient.PROPERTY_CLIENT_ID );
        String clientSecret = System.getProperty( BaringoClient.PROPERTY_CLIENT_SECRET );
        try {
			client = new BaringoClient.Builder()
					.clientAuth( clientId, clientSecret )
					.build();
		} catch (BaringoApiException e) {
			e.printStackTrace();
		}       
    }

	/**
	 * @return the client
	 */
	public BaringoClient getClient() {
		return client;
	}

	private BaringoClient client = null;

	public void switchToUserAuth() throws BaringoAuthException {
        String refreshToken = System.getProperty( "baringoclient.refreshtoken" );
        client.authService().setRefreshToken( refreshToken );
		
	}

    
}
