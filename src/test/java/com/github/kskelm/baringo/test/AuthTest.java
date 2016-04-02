package com.github.kskelm.baringo.test;

import org.junit.Test;

import junit.framework.TestCase;

import com.github.kskelm.baringo.util.BaringoApiException;
import com.github.kskelm.baringo.util.BaringoAuthException;


public class AuthTest extends TestCase {
	public AuthTest( String testName ) {
		super( testName );
	}

	@Test
	public void testInvalidAuthCode() throws BaringoApiException {
		Setup setup = new Setup();

		try {
			setup.getClient().authService().setAuthorizationCode( "xyz this won't work");
			fail( "Setting invalid auth code should have caused an exception");
		} catch( BaringoAuthException e ) {
			// do nothing, yay
		}
	}

	@Test
	public void testInvalidRefreshToken() throws BaringoApiException {
		Setup setup = new Setup();
		try {
			setup.getClient().authService().setRefreshToken( "xyz this won't work");
			fail( "Setting invalid refresh token should have caused an exception");
		} catch( BaringoAuthException e ) {
			// do nothing, yay
		}
	}

}
