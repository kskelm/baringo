package com.github.kskelm.baringo.test;

import java.util.List;

import org.junit.Test;
import junit.framework.TestCase;

import com.github.kskelm.baringo.model.Account;
import com.github.kskelm.baringo.model.AccountSettings;
import com.github.kskelm.baringo.model.Album;
import com.github.kskelm.baringo.model.ChangedAccountSettings;
import com.github.kskelm.baringo.model.Comment;
import com.github.kskelm.baringo.model.GalleryImage;
import com.github.kskelm.baringo.model.GalleryItem;
import com.github.kskelm.baringo.model.GalleryProfile;
import com.github.kskelm.baringo.model.Image;
import com.github.kskelm.baringo.model.Notification;
import com.github.kskelm.baringo.model.Trophy;
import com.github.kskelm.baringo.util.BaringoApiException;


public class AccountTest extends TestCase {
	public AccountTest( String testName ) {
		super( testName );
	}

	@Test
	public void testGetAccount() throws BaringoApiException {
		Setup setup = new Setup();

		Account acct = setup.getClient().accountService().getAccount( Setup.TEST_USER_NAME );

		assertEquals( "Bio is set", acct.getBio(), "Test profile" );
		assertEquals( "Account id is set", acct.getId(), 33527752 );
		assertEquals( "Created is set", acct.getCreated().getTime(), 1459093737000L );	
	}

	@Test
	public void testListGalleryFavorites() throws BaringoApiException {
		Setup setup = new Setup();
		List<GalleryItem> items = setup.getClient().accountService()
				.listGalleryFavorites(
						Setup.TEST_USER_NAME, 0, Account.GallerySort.newest );

		assertEquals( "Found 2 gallery favorites", items.size(), 2 );
	}

	@Test
	public void testListFavorites() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();

		List<GalleryItem> items = setup.getClient().accountService()
				.listFavorites();

		assertEquals( "Found 2 favorites", items.size(), 2 );
	}

	@Test
	public void testListSubmissions() throws BaringoApiException {
		Setup setup = new Setup();
		List<GalleryItem> list = setup.getClient()
				.accountService().listSubmissions( Setup.TEST_USER_NAME_2, 0 );

		assertEquals( "Submissions come back", list.size(), 4 );
		assertEquals( "First object is image", list.get(0).isAlbum(), false );
		assertEquals( "First object is GalleryImage", list.get( 0 ).getClass(), GalleryImage.class );
	}

	@Test
	public void testGetGalleryProfile() throws BaringoApiException {
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

	@Test
	public void testGetAccountSettings() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();

		AccountSettings settings = setup.getClient()
				.accountService().getAccountSettings();

		assertEquals( "Mature works", settings.isShowMature(), true );
		assertNotNull( "GetUserName works", settings.getUserName() );
		assertNotNull( "Email works", settings.getEmail() );
		assertEquals( "Album privacy works", settings.getAlbumPrivacy(), Album.Privacy.Public );
	}

	@Test
	public void testSetAccountSettings() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();

		AccountSettings settings = setup.getClient()
				.accountService().getAccountSettings();
		Account acct = setup.getClient().getAuthenticatedAccount();

		ChangedAccountSettings changedSettings = new ChangedAccountSettings(settings, acct );

		setup.getClient().accountService().setAccountSettings(changedSettings);
	}

	@Test
	public void testIsVerified() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();

		// just calling it without dying is cool
		setup.getClient().accountService().isVerified();
	}

// I checked, this works. I don't really want a thousand test emails.
//	@Test
//	public void testSendVerificationEmail() throws BaringoApiException {
//		Setup setup = new Setup();
//		setup.switchToUserAuth();
//
//		// just calling it without dying is cool
//		setup.getClient().accountService().sendVerificationEmail();
//	}

	@Test
	public void testListAlbums() throws BaringoApiException {
		Setup setup = new Setup();
		List<Album> list = setup.getClient()
				.accountService().listAlbums( Setup.TEST_USER_NAME, 0);

		assertEquals( "Albums come back", list.size(), 1 );
	}

	@Test
	public void testListAlbumIds() throws BaringoApiException {
		Setup setup = new Setup();
		List<String> list = setup.getClient()
				.accountService().listAlbumIds( Setup.TEST_USER_NAME, 0);

		assertEquals( "Album IDs come back", list.size(), 1 );
	}

	@Test
	public void testGetAlbumCount() throws BaringoApiException {
		Setup setup = new Setup();
		int count = setup.getClient()
				.accountService().getAlbumCount( Setup.TEST_USER_NAME);

		assertEquals( "Album count is right", count, 1 );
	}

	@Test
	public void testListComments() throws BaringoApiException {
		Setup setup = new Setup();
		List<Comment> list = setup.getClient()
				.accountService()
				.listComments( Setup.TEST_USER_NAME, Comment.Sort.Newest, 0 );

		assertEquals( "Comments come back", list.size(), 1 );
	}

	@Test
	public void testListCommentIds() throws BaringoApiException {
		Setup setup = new Setup();
		List<Integer> list = setup.getClient()
				.accountService()
				.listCommentIds( Setup.TEST_USER_NAME, Comment.Sort.Newest, 0 );

		assertEquals( "Comments come back", list.size(), 1 );
	}

	@Test
	public void testGetCommentCount() throws BaringoApiException {
		Setup setup = new Setup();
		int count = setup.getClient()
				.accountService().getCommentCount( Setup.TEST_USER_NAME);

		assertEquals( "Comment count is right", count, 1 );
	}

	@Test
	public void testListImages() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();

		List<Image> list = setup.getClient()
				.accountService().listImages( 0 );
		
		assertEquals( "Image count is right", list.size(), 3 );
	}

	@Test
	public void testListImageIds() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();

		List<String> list = setup.getClient()
				.accountService().listImageIds( 0 );
		
		assertEquals( "Image count is right", list.size(), 3 );
	}

	@Test
	public void testGetImageCount() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();

		int count = setup.getClient()
				.accountService().getImageCount();
		
		assertEquals( "Image count is right", count, 3 );

	}

	@Test
	public void testListNotifications() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();

		List<Notification> list = setup.getClient()
				.accountService().listNotifications( false );
		
		assertNotNull( "Notification list came back", list );

	}
}
