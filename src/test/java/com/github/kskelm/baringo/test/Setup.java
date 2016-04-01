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
	public static final String TEST_ENDPOINT = "http://localhost:8080";
	
    public Setup() {
        String clientId = System.getProperty( BaringoClient.PROPERTY_CLIENT_ID );
        String clientSecret = System.getProperty( BaringoClient.PROPERTY_CLIENT_SECRET );
        try {
			client = new BaringoClient.Builder()
					.clientAuth( clientId, clientSecret )
					.build();
		} catch (BaringoApiException e) {
			// TODO Auto-generated catch block
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
