package com.github.kskelm.baringo.test;

import java.util.List;

import org.junit.Test;
import junit.framework.TestCase;

import com.github.kskelm.baringo.model.Account;
import com.github.kskelm.baringo.model.Album;
import com.github.kskelm.baringo.model.Comment;
import com.github.kskelm.baringo.model.GalleryImage;
import com.github.kskelm.baringo.model.GalleryItem;
import com.github.kskelm.baringo.model.GalleryProfile;
import com.github.kskelm.baringo.model.Trophy;
import com.github.kskelm.baringo.util.ImgurApiException;


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
		System.err.println("TODO: must be logged in to testGetFavorites" );
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
		assertEquals( "First object is GalleryImage", list.get( 0 ).getClass(), GalleryImage.class );
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
		System.err.println("TODO: must be logged in to testGetAccountSettings" );
	}
	// TODO: test case
	@Test
	public void testSetAccountSettings() throws ImgurApiException {
		System.err.println("TODO: must be logged in to testSetAccountSettings" );
	}
	// TODO: test case
	@Test
	public void testIsVerified() throws ImgurApiException {
		System.err.println("TODO: must be logged in to testIsVerified" );
	}
	// TODO: test case
	@Test
	public void testSendVerificationEmail() throws ImgurApiException {
		System.err.println("TODO: must be logged in to testSendVerificationEmail" );
	}

	@Test
	public void testListAlbums() throws ImgurApiException {
		Setup setup = new Setup();
		List<Album> list = setup.getClient()
				.accountService().listAlbums( Setup.TEST_USER_NAME, 0);

		assertEquals( "Albums come back", list.size(), 1 );
	}

	@Test
	public void testListAlbumIds() throws ImgurApiException {
		Setup setup = new Setup();
		List<String> list = setup.getClient()
				.accountService().listAlbumIds( Setup.TEST_USER_NAME, 0);

		assertEquals( "Album IDs come back", list.size(), 1 );
	}

	@Test
	public void testGetAlbumCount() throws ImgurApiException {
		Setup setup = new Setup();
		int count = setup.getClient()
				.accountService().getAlbumCount( Setup.TEST_USER_NAME);

		assertEquals( "Album count is right", count, 1 );
	}

	@Test
	public void testListComments() throws ImgurApiException {
		Setup setup = new Setup();
		List<Comment> list = setup.getClient()
				.accountService()
				.listComments( Setup.TEST_USER_NAME, Comment.Sort.Newest, 0 );

		assertEquals( "Comments come back", list.size(), 1 );
	}

	@Test
	public void testListCommentIds() throws ImgurApiException {
		Setup setup = new Setup();
		List<Integer> list = setup.getClient()
				.accountService()
				.listCommentIds( Setup.TEST_USER_NAME, Comment.Sort.Newest, 0 );

		assertEquals( "Comments come back", list.size(), 1 );
	}

	@Test
	public void testGetCommentCount() throws ImgurApiException {
		Setup setup = new Setup();
		int count = setup.getClient()
				.accountService().getCommentCount( Setup.TEST_USER_NAME);

		assertEquals( "Comment count is right", count, 1 );
	}
	// TODO: test case
	@Test
	public void testListImages() throws ImgurApiException {
		System.err.println("TODO: must be logged in to testListImages" );
	}
	// TODO: test case
	@Test
	public void testListImageIds() throws ImgurApiException {
		System.err.println("TODO: must be logged in to testListImageIds" );
	}
	// TODO: test case
	@Test
	public void testGetImageCount() throws ImgurApiException {
		System.err.println("TODO: must be logged in to testGetImageCount" );
	}
	// TODO: test case
	@Test
	public void testListReplies() throws ImgurApiException {
		System.err.println("TODO: must be logged in to testListReplies");
	}
}
