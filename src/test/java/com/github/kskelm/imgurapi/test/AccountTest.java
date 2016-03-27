package com.github.kskelm.imgurapi.test;

import org.junit.Test;
import junit.framework.TestCase;

import com.github.kskelm.imgurapi.model.Account;
import com.github.kskelm.imgurapi.util.ImgurApiException;


public class AccountTest extends TestCase {
	public AccountTest( String testName ) {
		super( testName );
	}
	
	@Test
	public void testGetAccount() throws ImgurApiException {
		Setup setup = new Setup();
		
		Account acct = setup.getClient().accountService().getAccount( Setup.TEST_USER_NAME );

		assertEquals( "Bio is set", acct.getBio(), "Test profile" );
		assertEquals( "Account id is set", acct.getId(), 33527752 );
		assertEquals( "Created is set", acct.getCreated().getTime(), 1459093737000L );	
	}

	@Test
	public void testGetAccountVerified() throws ImgurApiException {
		System.err.println("TODO: TEST getAccountVerified" );
	}

	@Test
	public void testGetAccountGalleryFavorites() throws ImgurApiException {
		System.err.println("TODO: TEST getAccountGalleryFavorites" );
	}
	
	
}
