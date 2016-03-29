package com.github.kskelm.imgurapi.test;

import java.util.List;

import org.junit.Test;
import junit.framework.TestCase;

import com.github.kskelm.imgurapi.model.Account;
import com.github.kskelm.imgurapi.model.GalleryImage;
import com.github.kskelm.imgurapi.model.GalleryItem;
import com.github.kskelm.imgurapi.model.GalleryProfile;
import com.github.kskelm.imgurapi.model.Trophy;
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

	@Test
	public void testListSubmissions() throws ImgurApiException {
		Setup setup = new Setup();
		List<GalleryItem> list = setup.getClient()
				.accountService().listSubmissions( Setup.TEST_USER_NAME_2, 0 );

		assertEquals( "Submissions come back", list.size(), 4 );
		assertEquals( "First object is image", list.get(0).isAlbum(), false );
		GalleryImage img = (GalleryImage)list.get( 0 );
	}

	@Test
	public void testGetGalleryProfile() throws ImgurApiException {
		Setup setup = new Setup();
		GalleryProfile prof = setup.getClient()
				.accountService().getGalleryProfile( Setup.TEST_USER_NAME_2 );

		assertEquals( "Favorite count works", prof.getFavoriteCount(), 1 );
		assertEquals( "Comment count works", prof.getCommentCount(),  4);
		assertEquals( "Submission count works", prof.getSubmissionCount(), 4 );
		assertEquals( "Trophies works", prof.getTrophies().size(), 1 );
		// The first trophy is just for being old. It doesn't have an
		// earned link or id.
		Trophy t = prof.getTrophies().get( 0 );
		assertNotNull( "Trophy has description", t.getDescription() );
		assertNotNull( "Trophy has earned date", t.getEarnedDate() );
		assertNotNull( "Trophy has image link", t.getImageLink() );
		assertNotNull( "Trophy has name", t.getName() );
		assertNotNull( "Trophy has type", t.getType() );
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
		// TODO: test this on a different account than the test
		// account first to make sure it's not specifically
		// for the authenticated user instead of just any user
		// (then simplify the call if necessary)
		System.err.println("TODO: testListAlbums" );
	}
	// TODO: test case
	@Test
	public void testListAlbumIds() throws ImgurApiException {
		// TODO: test this on a different account than the test
		// account first to make sure it's not specifically
		// for the authenticated user instead of just any user
		// (then simplify the call if necessary)
		System.err.println("TODO: testListAlbumIds" );
	}
	// TODO: test case
	@Test
	public void testGetAlbumCount() throws ImgurApiException {
		// TODO: test this on a different account than the test
		// account first to make sure it's not specifically
		// for the authenticated user instead of just any user
		// (then simplify the call if necessary)
		System.err.println("TODO: testGetAlbumCount" );
	}
	// TODO: test case
	@Test
	public void testListComments() throws ImgurApiException {
		// TODO: test this on a different account than the test
		// account first to make sure it's not specifically
		// for the authenticated user instead of just any user
		// (then simplify the call if necessary)
		System.err.println("TODO: testListComments" );
	}
	// TODO: test case
	@Test
	public void testListCommentIds() throws ImgurApiException {
		// TODO: test this on a different account than the test
		// account first to make sure it's not specifically
		// for the authenticated user instead of just any user
		// (then simplify the call if necessary)
		System.err.println("TODO: testListCommentIds" );
	}
	// TODO: test case
	@Test
	public void testGetCommentCount() throws ImgurApiException {
		// TODO: test this on a different account than the test
		// account first to make sure it's not specifically
		// for the authenticated user instead of just any user
		// (then simplify the call if necessary)
		System.err.println("TODO: testGetCommentCount" );
	}
	// TODO: test case
	@Test
	public void testListImages() throws ImgurApiException {
		System.err.println("TODO: testListImages" );
	}
	// TODO: test case
	@Test
	public void testListImageIds() throws ImgurApiException {
		System.err.println("TODO: testListImageIds" );
	}
	// TODO: test case
	@Test
	public void testGetImageCount() throws ImgurApiException {
		System.err.println("TODO: testGetImageCount" );
	}
	// TODO: test case
	@Test
	public void testListReplies() throws ImgurApiException {
		// TODO: test this on a different account than the test
		// account first to make sure it's not specifically
		// for the authenticated user instead of just any user
		// (then simplify the call if necessary)
		System.err.println("TODO: testListReplies");
	}
}
