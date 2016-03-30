package com.github.kskelm.baringo.test;


import com.github.kskelm.baringo.ImgurClient;

/**
 * Parent class for tests that sets up client
 */
public class Setup {
	public static final String TEST_USER_NAME = "kskelmapitest";
	public static final String TEST_USER_NAME_2 = "kevinkelm";
	public static final String TEST_ENDPOINT = "http://localhost:8080";
	
    public Setup() {
//        ImgurClient.setEndpoint( TEST_ENDPOINT );
        String clientId = System.getProperty( ImgurClient.PROPERTY_CLIENT_ID );
        String clientSecret = System.getProperty( ImgurClient.PROPERTY_CLIENT_SECRET );
        client = new ImgurClient( clientId, clientSecret );       
    }

	/**
	 * @return the client
	 */
	public ImgurClient getClient() {
		return client;
	}

	private ImgurClient client = null;

    
}
