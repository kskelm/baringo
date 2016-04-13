/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo.test;

import org.junit.Test;
import junit.framework.TestCase;

import com.github.kskelm.baringo.model.gallery.CustomGallery;
import com.github.kskelm.baringo.model.gallery.GalleryItem;
import com.github.kskelm.baringo.util.BaringoApiException;


public class CustomGalleryTest extends TestCase {
	public CustomGalleryTest( String testName ) {
		super( testName );
	}
	
	@Test
	public void testListCustomGallery() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();
		
		CustomGallery gal = setup
				.getClient()
				.customGalleryService()
				.getCustomGallery(
						GalleryItem.Sort.Top,
						GalleryItem.Window.Month,
						0 );
		
		assertEquals( "user name", gal.getUserName(), Setup.TEST_USER_NAME );
		assertNotNull( "link", gal.getLink() );
		assertTrue( "list size", gal.getItems().size() > 0 );
	}

	@Test
	public void testListFilteredGallery() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();
		
		CustomGallery gal = setup
				.getClient()
				.customGalleryService()
				.getFilteredGallery(
						GalleryItem.Sort.Time,
						GalleryItem.Window.Year,
						0 );
		
		assertEquals( "user name", gal.getUserName(), Setup.TEST_USER_NAME );
		assertNotNull( "link", gal.getLink() );
	}


	@Test
	public void testAddCustomGalleryTags() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();
		
		// just test that we don't bomb out
		setup.getClient().customGalleryService().addCustomGalleryTag( "astronomy" );
	}

	@Test
	public void testDeleteCustomGalleryTags() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();
		
		// just test that we don't bomb out
		setup.getClient().customGalleryService().deleteCustomGalleryTag( "junktag" );
	}

	@Test
	public void testBlockGalleryTags() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();
		
		// just test that we don't bomb out
		setup.getClient().customGalleryService().blockGalleryTag( "ovipositor");
	}

	@Test
	public void testUnblockGalleryTags() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();

		// just test that we don't bomb out
		setup.getClient().customGalleryService().unblockGalleryTag( "ovipositor" );
	}

}
