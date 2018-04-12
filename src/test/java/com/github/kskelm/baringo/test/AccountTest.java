/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo.test;

import java.util.List;

import org.junit.Test;
import junit.framework.TestCase;

import com.github.kskelm.baringo.model.Account;
import com.github.kskelm.baringo.model.AccountSettings;
import com.github.kskelm.baringo.model.Album;
import com.github.kskelm.baringo.model.ChangedAccountSettings;
import com.github.kskelm.baringo.model.Comment;
import com.github.kskelm.baringo.model.Image;
import com.github.kskelm.baringo.model.Trophy;
import com.github.kskelm.baringo.model.gallery.GalleryImage;
import com.github.kskelm.baringo.model.gallery.GalleryItem;
import com.github.kskelm.baringo.model.gallery.GalleryProfile;
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
	public void testGetAccountWithId() throws BaringoApiException
	{
		Setup setup = new Setup();

		Account acct = setup.getClient().accountService().getAccount( 33527752 );

		assertEquals( "Bio is set", acct.getBio(), "Test profile" );
		assertEquals( "Account id is set", acct.getUserName(), Setup.TEST_USER_NAME );
		assertEquals( "Created is set", acct.getCreated().getTime(), 1459093737000L );
	}

	@Test
	public void testListGalleryFavorites() throws BaringoApiException {
		Setup setup = new Setup();
		List<GalleryItem> items = setup.getClient().accountService()
				.listGalleryFavorites(
						Setup.TEST_USER_NAME, 0, Account.GallerySort.newest );

		assertTrue( "Found gallery favorites", items.size() > 0 );
	}

	@Test
	public void testListFavorites() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();

		List<GalleryItem> items = setup.getClient().accountService()
				.listFavorites();

		assertTrue( "Found favorites", items.size() > 0 );
	}

	@Test
	public void testListSubmissions() throws BaringoApiException {
		Setup setup = new Setup();
		List<GalleryItem> list = setup.getClient()
				.accountService().listSubmissions( Setup.TEST_USER_NAME_2, 0 );

		assertTrue( "Submissions come back", list.size() > 0 );
	}

	@Test
	public void testGetGalleryProfile() throws BaringoApiException {
		Setup setup = new Setup();
		GalleryProfile prof = setup.getClient()
				.accountService().getGalleryProfile( Setup.TEST_USER_NAME_2 );

		assertTrue( "Favorite count works", prof.getFavoriteCount() > 0 );
		assertTrue( "Comment count works", prof.getCommentCount() > 0 );
		assertTrue( "Submission count works", prof.getSubmissionCount() > 0 );
		assertTrue( "Trophies works", prof.getTrophies().size() > 0 );
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

		assertTrue( "Albums come back", list.size() > 0 );
	}

	@Test
	public void testListAlbumIds() throws BaringoApiException {
		Setup setup = new Setup();
		List<String> list = setup.getClient()
				.accountService().listAlbumIds( Setup.TEST_USER_NAME, 0);

		assertTrue( "Album IDs come back", list.size() > 0 );
	}

	@Test
	public void testGetAlbumCount() throws BaringoApiException {
		Setup setup = new Setup();
		int count = setup.getClient()
				.accountService().getAlbumCount( Setup.TEST_USER_NAME);

		assertTrue( "Album count not zero", count > 0 );
	}

	@Test
	public void testListComments() throws BaringoApiException {
		Setup setup = new Setup();
		List<Comment> list = setup.getClient()
				.accountService()
				.listComments( Setup.TEST_USER_NAME, Comment.Sort.Newest, 0 );

		assertTrue( "Comments come back", list.size() > 0 );
	}

	@Test
	public void testListCommentIds() throws BaringoApiException {
		Setup setup = new Setup();
		List<Integer> list = setup.getClient()
				.accountService()
				.listCommentIds( Setup.TEST_USER_NAME, Comment.Sort.Newest, 0 );

		assertTrue( "Comments come back", list.size() > 0 );
	}

	@Test
	public void testGetCommentCount() throws BaringoApiException {
		Setup setup = new Setup();
		int count = setup.getClient()
				.accountService().getCommentCount( Setup.TEST_USER_NAME);

		assertTrue( "Comments come back", count > 0 );
	}

	@Test
	public void testListImages() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();

		List<Image> list = setup.getClient()
				.accountService().listImages( 0 );
		
		assertTrue( "Image count is set", list.size() > 0);
	}

	@Test
	public void testListImageIds() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();

		List<String> list = setup.getClient()
				.accountService().listImageIds( 0 );
		
		assertTrue( "Image count is set", list.size() > 0 );
	}

	@Test
	public void testGetImageCount() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();

		int count = setup.getClient()
				.accountService().getImageCount();
		
		assertTrue( "Image count is set", count > 0 );

	}

}
