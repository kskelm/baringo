/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo.test;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import junit.framework.TestCase;

import com.github.kskelm.baringo.model.Image;
import com.github.kskelm.baringo.model.Image.ThumbnailType;
import com.github.kskelm.baringo.util.BaringoApiException;


public class ImageTest extends TestCase {

	@Test
	public void testGetImageInfo() throws BaringoApiException {
		Setup setup = new Setup();

		Image image = setup.getClient()
				.imageService().getImageInfo( Setup.TEST_IMAGE_ID );

		assertNotNull( "image came back", image );

		testImageObject( image );
	}

	@Test
	public void testUploadUrlImage() throws BaringoApiException {
		Setup setup = new Setup();

		Image image = setup.getClient().imageService().uploadUrlImage(
				Setup.TEST_IMAGE_URL,
				"tee hee.png",
				null,
				"tiny giraffe",
				"is tiny" );

		assertNotNull( "image came back", image );

		setup.getClient().imageService().deleteImage( image.getDeleteHash() );

		ImageTest.testImageObject( image );
	}

	@Test
	public void testUploadLocalImage() throws BaringoApiException, IOException {
		Setup setup = new Setup();
		
		// first, download an image we can operate on
		Image image = setup.getClient()
				.imageService().getImageInfo( Setup.TEST_IMAGE_ID );

		File file = File.createTempFile( image.getId(), ".png");
		String fileName = file.getAbsolutePath();

		setup.getClient().imageService()
				.downloadImage( Setup.TEST_IMAGE_URL, fileName );

		assertNotNull( "image came back", image );
		
		// now upload that new temporary file as a new image...
//		BufferedInputStream inStream = new BufferedInputStream(
//				new FileInputStream( fileName ) );
		
		image = setup.getClient().imageService().uploadLocalImage(
				null,    // infer mime type from filename
				fileName,
				null,
				"tiny giraffe",
				"is tiny" );

		assertNotNull( "image came back", image );
		ImageTest.testImageObject( image );

		// huzzah! now delete it.
		boolean success = setup.getClient()
				.imageService().deleteImage( image.getDeleteHash() );

		assertTrue( "delete worked", success );

		file.delete();		
	}


	@Test
	public void testDownloadImage() throws BaringoApiException, IOException {
		Setup setup = new Setup();

		Image image = setup.getClient()
				.imageService().getImageInfo( Setup.TEST_IMAGE_ID );

		File file = File.createTempFile( image.getId(), ".tmp");
		String fileName = file.getAbsolutePath();

		long length = setup.getClient().imageService()
				.downloadImage( image.getLink(), fileName );

		file.delete();
		
		assertTrue( length == image.getSize() );
	}	

	@Test
	public void testUpdateImageInfo() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();

		Image image = setup.getClient()
				.imageService().getImageInfo( Setup.TEST_IMAGE_ID );

		String firstTitle = image.getTitle();
		String firstDesc = image.getDescription();
		
		String newTitle = image.getTitle().equals( "foo" ) ? "bar" : "foo";
		String newDesc= image.getDescription().equals( "foo" ) ? "bar" : "foo";
		
		boolean success = setup.getClient()
				.imageService().updateImage( image.getId(), newTitle, newDesc );
		assertTrue( "success came back form image update 1", success );

		Image image2 = setup.getClient()
				.imageService().getImageInfo( Setup.TEST_IMAGE_ID );

		assertFalse( "title changed at remote", image2.getTitle().equals( firstTitle ) );
		assertFalse( "description changed at remote", image2.getDescription().equals( firstDesc ) );
		
		success = setup.getClient()
				.imageService().updateImage( image.getId(), firstTitle, firstDesc );
		
		assertTrue( "success came back from image update 2", success );

	}	

	@Test
	public void testDeleteImage() throws BaringoApiException {
		Setup setup = new Setup();

		Image image = setup.getClient().imageService().uploadUrlImage(
        			 Setup.TEST_IMAGE_URL, null, null, null, null );

		boolean success = setup.getClient()
				.imageService().deleteImage( image.getDeleteHash() );

		assertTrue( "delete worked", success );
	}	

	@Test
	public void testToggleFavoriteImage() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();
		
		Image image = setup.getClient().imageService().getImageInfo( Setup.TEST_IMAGE_ID_4 );

		assertNotNull( "image came back", image );
		
		Image image2 = null;
		if( image.isFavorite() ) {
			image2 = setup.getClient().imageService().unfavoriteImage( image );
		} else {
			image2 = setup.getClient().imageService().favoriteImage( image );
		} // if-else
		
		assertFalse( "returned image toggled", image2.isFavorite() == image.isFavorite() );
	}	

	// ===================================================

	public static void testImageObject( Image img ) {

		assertNotNull( "id set", img.getId() );
		assertTrue( "date set", img.getUploadDate().getTime() > 0 );
		assertNotNull( "mime type set", img.getMimeType() );
		assertTrue( "width set", img.getWidth() > 0 );
		assertTrue( "height set", img.getHeight() > 0 );
		assertTrue( "size set", img.getSize() > 0 );
		assertNotNull( "link set", img.getLink()  );

		assertNotNull( "thumb link works",
				img.getThumbnailLink( ThumbnailType.BigSquare ) );	
	}

}
