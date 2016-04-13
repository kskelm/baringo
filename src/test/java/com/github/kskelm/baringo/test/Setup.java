/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
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

	public static final String TEST_MEME_IMAGE_ID = "sJKiHrC";

	public static final String TEST_SUBREDDIT_NAME = "funny";
	public static final String TEST_SUBREDDIT_IMAGE_ID = "xocejLi";

	public static final String TEST_IMAGE_ID = "bHEb5Sw";
	public static final String TEST_IMAGE_ID_2 = "wH8KZXs";
	public static final String TEST_IMAGE_ID_3 = "WVaAF7k"; 
	public static final String TEST_IMAGE_ID_4 = "itYIjWG"; // owned by kevinkelm for faving
	
	public static final String TEST_IMAGE_URL = "https://i.imgur.com/h5s04TC.png";
	
	public static final String TEST_IMAGE_WITH_TAGS = "obJXifJ";
		
	public static final long   TEST_COMMENT_ID = 620866057L;
	public static final long   TEST_COMMENT_ID_2 = 617736106L;
	
	public static final int    TEST_TOPIC_ID = 29;
	
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

	public Setup switchToUserAuth() throws BaringoAuthException {
        String refreshToken = System.getProperty( "baringoclient.refreshtoken" );
        client.authService().setRefreshToken( refreshToken );
		return this;
	}

    
}
