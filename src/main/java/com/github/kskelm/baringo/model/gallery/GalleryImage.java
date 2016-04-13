/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo.model.gallery;

import java.util.HashMap;

import com.github.kskelm.baringo.util.Utils;
import com.google.gson.annotations.SerializedName;

/**
 * Model object for images found in a gallery.
 * This is a subclass of GalleryItem because
 * GalleryImage and GalleryAlbum are both returned
 * by various Imgur APIs and they share a notable
 * number of characteristics.  In any list of 
 * GalleryItems coming back from an API call,
 * isAlbum() will determine whether it can be
 * cast to GalleryAlbum or GalleryImage.
 * @author Kevin Kelm (triggur@gmail.com)
 *
 */
public class GalleryImage extends GalleryItem {

	
	/**
	 * Return the mimeType of the image
	 * @return the mimeType
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * Returns true if the image is animated
	 * @return the animated
	 */
	public boolean isAnimated() {
		return animated;
	}

	/**
	 * Returns the width of the image in pixels
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the height of the image in pixels
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Returns the size of the image in bytes
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Returns the total number of bytes of Internet bandwidth this image has wasted
	 * @return the bandwidth
	 */
	public long getBandwidth() {
		return bandwidth;
	}

	/**
	 * If the client is currently logged into a user and that user owns this image, this is the hash that can be used to delete the image, else null.
	 * @return the deleteHash
	 */
	public String getDeleteHash() {
		return deleteHash;
	}

	/**
	 * If the image is animated, this is a link to a gifv formatted version
	 * @return the gifvLink
	 */
	public String getGifvLink() {
		return gifvLink;
	}

	/**
	 * If the image is animated, this is a link to an mp4 formatted version
	 * @return the mp4Link
	 */
	public String getMp4Link() {
		return mp4Link;
	}

	/**
	 * If the image is animated, this is a link to a webm formatted version
	 * @return the webmLink
	 */
	public String getWebmLink() {
		return webmLink;
	}

	/**
	 * Returns true if the image is animated and it loops continuously
	 * @return the looping
	 */
	public boolean isLooping() {
		return looping;
	}

	/**
	 * If the backend has classified this image into a section like cat, wtf, etc, that value is here.
	 * @return the section
	 */
	public GalleryItem.Section getSection() {
		return section;
	}

	
	public String toString() {
		HashMap<String,Object> fields = Utils.toHashMap( this, GalleryImage.class );
		fields.putAll( Utils.toHashMap( this, GalleryItem.class ) );
		return Utils.toString( fields, GalleryImage.class.getSimpleName() );
	} // toString
	
	// ======================================================
	
	/**
	 * Internal only
	 */
	public GalleryImage() { }
	
	/**
	 * Internal only
	 * @param p proxy object
	 */
	public GalleryImage( GalleryItemProxy p ) {
		super( p );
		copyFrom( p, GalleryImage.class );
	} // lame copy constructor
	
	@SerializedName("type")
	protected String mimeType;
	protected boolean animated;
	protected int width;
	protected int height;
	protected int size;
	protected long bandwidth;
	@SerializedName("deletehash")
	protected String deleteHash;
	protected String gifvLink;
	protected String mp4Link;
	protected String webmLink;
	protected boolean looping;
	protected GalleryItem.Section section;


} // GalleryImage
