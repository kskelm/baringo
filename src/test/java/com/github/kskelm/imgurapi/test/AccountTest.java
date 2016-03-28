package com.github.kskelm.imgurapi.test;

import java.util.List;

import org.junit.Test;
import junit.framework.TestCase;

import com.github.kskelm.imgurapi.model.Account;
import com.github.kskelm.imgurapi.model.GalleryItem;
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
	public void testListGalleryFavorites() throws ImgurApiException {
		Setup setup = new Setup();
		List<GalleryItem> items = setup.getClient().accountService()
				.listGalleryFavorites(
						Setup.TEST_USER_NAME, 0, Account.GallerySort.newest );

		assertEquals( "Found 2 gallery favorites", items.size(), 2 );
	}
	// TODO: test case
	@Test
	public void testListFavorites() throws ImgurApiException {
		System.err.println("TODO: test getFavorites" );
		//		Setup setup = new Setup();
		//		List<GalleryItem> items = setup.getClient().accountService()
		//				.listFavorites();
		//		
		//		assertEquals( "Found 2 favorites", items.size(), 2 );
	}
	// TODO: test case
	@Test
	public void testListSubmissions() throws ImgurApiException {
		System.err.println("TODO: test getSubmissions" );
		//		Setup setup = new Setup();
		//		List<GalleryItem> items = setup.getClient().accountService()
		//				.listSubmissions( Setup.TEST_USER_NAME, 0 );
		//		
		//		assertEquals( "Found 2 submissions", items.size(), 2 );
	}

	// TODO: test case
	@Test
	public void testGetGalleryProfile() throws ImgurApiException {
		System.err.println("TODO: testGetGalleryProfile" );
	}
	// TODO: test case
	@Test
	public void testGetAccountSettings() throws ImgurApiException {
		System.err.println("TODO: testGetAccountSettings" );
	}
	// TODO: test case
	@Test
	public void testSetAccountSettings() throws ImgurApiException {
		System.err.println("TODO: testSetAccountSettings" );
	}
	// TODO: test case
	@Test
	public void testIsVerified() throws ImgurApiException {
		System.err.println("TODO: testIsVerified" );
	}
	// TODO: test case
	@Test
	public void testSendVerificationEmail() throws ImgurApiException {
		System.err.println("TODO: testSendVerificationEmail" );
	}
	// TODO: test case
	@Test
	public void testListAlbums() throws ImgurApiException {
		System.err.println("TODO: testGetAlbums" );
	}
	// TODO: test case
	@Test
	public void testListAlbumIds() throws ImgurApiException {
		System.err.println("TODO: testGetAlbumIds" );
	}
	// TODO: test case
	@Test
	public void testGetAlbumCount() throws ImgurApiException {
		System.err.println("TODO: testGetAlbumCount" );
	}
	// TODO: test case
	@Test
	public void testListComments() throws ImgurApiException {
		System.err.println("TODO: testGetComments" );
	}
	// TODO: test case
	@Test
	public void testListCommentIds() throws ImgurApiException {
		System.err.println("TODO: testGetCommentIds" );
	}
	// TODO: test case
	@Test
	public void testGetCommentCount() throws ImgurApiException {
		System.err.println("TODO: testGetCommentCount" );
	}
	// TODO: test case
	@Test
	public void testListImages() throws ImgurApiException {
		System.err.println("TODO: testGetImages" );
	}
	// TODO: test case
	@Test
	public void testListImageIds() throws ImgurApiException {
		System.err.println("TODO: testGetImageIds" );
	}
	// TODO: test case
	@Test
	public void testGetImageCount() throws ImgurApiException {
		System.err.println("TODO: testGetImageCount" );
	}
// TODO: test case
	@Test
	public void testListReplies() throws ImgurApiException {
		System.err.println("TODO: testGetReplies");
	}
}