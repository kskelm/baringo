
package com.github.kskelm.baringo.testauth;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.github.kskelm.baringo.BaringoClient;
import com.github.kskelm.baringo.model.OAuth2;
import com.github.kskelm.baringo.util.BaringoApiException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


/**
 * This is a totally kit-bashed test HTTP server meant for
 * experimentation with receiving OAuth2 authorization codes
 * and tokens.
 * 
 * Once you've got an Imgur account and you've gone through
 * the process of registering your application, log into your
 * Imgur account and go to the settings menu item from the
 * upper right.  Select "applications" from the sections on the
 * left and click "edit" on the app client you've set up.
 * 
 * This will give you a dialog that lets you set the redirect
 * URL users go to once they've authorized your application to
 * use their account.  Ultimately this should be a page in your
 * application probably.
 * 
 * For now, this is just a test running on localhost so that you
 * can capture a test authorization to play with.
 * 
 * Run this on your computer.  http://localhost:54321/test should
 * be the URL you enter in the dialog box on Imgur.  Click the
 * "update" button.
 *
 */
@SuppressWarnings("restriction")
public class BaringoAuthDemo {

	public BaringoAuthDemo() {
		HttpServer server = null;
		try {
			server = HttpServer.create(new InetSocketAddress(54321), 0);
			System.out.println( "BaringoAuthDemo server now running on http://localhost:54321 on all interfaces.");
			System.out.println( "Visit that URL in your browser to test your Imgur access keys.");
			System.out.println( "-------------------" );
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		server.createContext("/assets/", new AssetHandler() );
		server.createContext("/gotcode", new ProcessCodeHandler() );
		server.createContext("/gettokens", new GetTokensHandler() );
		server.createContext("/", new StartHandler() );
		server.setExecutor(null); // creates a default executor
		server.start();
	}

	public static void main(String[] args) throws Exception {
		new BaringoAuthDemo();
	}

	class StartHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println( "Received " + t.getRequestMethod() + " " + t.getRequestURI().getPath() );
			HashMap<String,String> variables = parseVariables( t.getRequestURI().getQuery() );
			String out = generatePage( "index.html", variables );
			respondText( t, out );
		}
	}

	class ProcessCodeHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println( "Received " + t.getRequestMethod() + " " + t.getRequestURI().getPath() );
			HashMap<String,String> variables = new HashMap<>();
			variables.put( "error", "" );
			variables.putAll( parseVariables( t.getRequestURI().getQuery() ) );
			String out = generatePage( "gotcode.html", variables );
			respondText( t, out );
		}
	} 

	class GetTokensHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t)  {
			System.out.println( "Received " + t.getRequestMethod() + " " + t.getRequestURI().getPath() );
			HashMap<String,String> variables = parseVariables( t.getRequestURI().getQuery() );
			String out = null;
			if( variables.get("client_id") == null
					|| variables.get("client_secret") == null
					|| variables.get("code") == null ) {
				variables.put( "error", "ERROR: You need to fill in all the values.." );
				out = generatePage( "gotcode.html", variables );
				respondText( t, out );
				return;
			} // if
			BaringoClient client = null;
			try {
				client = new BaringoClient.Builder()
						.clientAuth( variables.get("client_id"), variables.get("client_secret" ) )
						.build();
			} catch (BaringoApiException e) {
				variables.put( "error", "ERROR: Could not create client; client_id and/or client_secret were invalid." );
				out = generatePage( "gotcode.html", variables );
				respondText( t, out );
				return;
			}
			try {
				client.authService().setAuthorizationCode( variables.get( "code" ) );
			} catch (BaringoApiException e) {
				variables.put( "error", "ERROR: Could not retrieve tokens from auth code. It could be you waited too long on this page.  Go back to the last page and try again." );
				out = generatePage( "gotcode.html", variables );
				respondText( t, out );
				return;
			}
			
			OAuth2 auth = client.authService().getOAuth2();
			variables.put( "access_token", auth.getAccessToken() );
			variables.put( "refresh_token", auth.getRefreshToken() );
			variables.put( "expires_on", "" + auth.getExpiresOn() );
			variables.put( "expires_in", "" + auth.getExpiresIn() );
			variables.put( "token_type", auth.getTokenType().toString() );
			variables.put( "user_id", "" + auth.getUserId() );
			variables.put( "user_name", auth.getUserName() );
			
			out = generatePage( "gottokens.html", variables );

			respondText( t, out );
		} // handle 
	} // GetTokensHandler

	class AssetHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			String path = t.getRequestURI().getPath();
			int dotAt = path.lastIndexOf( '.' );
			if( dotAt == -1 ) { // no extension
				respondError( t, 404, "File not found" );
				return;
			} else if( path.contains( ".." ) ) { // path funny-games
				respondError( t, 404, "File not found" );
				return;
			} // if-else
			String ext = path.substring( dotAt + 1 );
			String mimeType = mimeTypes.get( ext );
			if( mimeType == null ) {
				respondError( t, 404, "File not found" );
				return;
			} // if
			t.getResponseHeaders().add( "Content-type", mimeType );
			InputStream in = 
					getClass().getResourceAsStream( t.getRequestURI().getPath() );

			if( in == null ) {
				respondError( t, 404, "File not found" );
				return;
			}  // if
			t.sendResponseHeaders( 200, 0 );

			try {
				OutputStream out = t.getResponseBody();
				byte[] buffer = new byte[1024];
				int len;
				while ((len = in.read(buffer)) != -1) {
					out.write(buffer, 0, len);
				} // while
				out.close();		
			} catch (FileNotFoundException ex){
				ex.printStackTrace();
			}
		}
	}

	String clientId = null;

	private HashMap<String,String> parseVariables( String query ) {
		HashMap<String,String> vars = new HashMap<>();
		if( query == null ) {
			return vars;
		} // if
		String[] pairs = query.split( "&" );
		for( String pair : pairs ) {
			String[] parts = pair.split( "=" );
			String key = parts[0];
			String value = parts.length == 1 ? "" : parts[1];
			vars.put( key, value );
		} // for

		return vars;
	}

	protected String generatePage( String path, HashMap<String,String> variables ) {
		StringBuffer buf = new StringBuffer();
		InputStream in = 
				getClass().getResourceAsStream( "/" + path );
		BufferedReader br;
		try {
			br = new BufferedReader( new InputStreamReader(in, "utf-8") );
			String aLine = null;
			while ((aLine = br.readLine()) != null) {
				buf.append( aLine );
			}
		} catch (IOException e) {
			e.printStackTrace();
		} // try-catch

		String out = buf.toString();
		// this won't be efficient...
		for( String key : variables.keySet() ) {
			String value = variables.get( key );
			if( value == null ) {
				value = "<null>";
			} // if
			out = out.replaceAll( "\\{" + key + "\\}", value );
		} // for
		return out;
	}

	protected void respondText( HttpExchange t, String out ) {
		try {
			t.sendResponseHeaders(200, out.length());
			OutputStream os = t.getResponseBody();
			os.write( out.getBytes());
			os.close();		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // respondText

	protected void respondError( HttpExchange t, int code, String text ) {
		try {
			t.sendResponseHeaders(code, text.length());
			OutputStream os = t.getResponseBody();
			os.write( text.getBytes());
			os.close();		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // respondError
	
	static HashMap<String,String> mimeTypes = new HashMap<>();
	static {
		mimeTypes.put( "css", "text/css" );
		mimeTypes.put( "gif", "image/gif");
		mimeTypes.put( "jpg", "image/jpeg");
		mimeTypes.put( "js", "application/javascript");
		mimeTypes.put( "png", "image/png" );

	} // static init
}