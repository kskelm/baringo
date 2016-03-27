/**
 * Model object for images found in a gallery.
 * This is a subclass of GalleryItemProxy because
 * GalleryImage and GalleryAlbum are both returned
 * by various Imgur APIs and they share a notable
 * number of characteristics.  Check the class
 * type of an instance and cast it to get the
 * appropriate version.
 */
package com.github.kskelm.imgurapi.model;

import java.util.HashMap;

import com.github.kskelm.imgurapi.util.Utils;
import com.google.gson.annotations.SerializedName;

/**
 * @author kskelm
 *
 */
public class GalleryImage extends GalleryItem {

	
	// TODO: COMMENT METHODS
	
	/**
	 * @return the mimeType
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * @return the animated
	 */
	public boolean isAnimated() {
		return animated;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @return the bandwidth
	 */
	public long getBandwidth() {
		return bandwidth;
	}

	/**
	 * @return the deleteHash
	 */
	public String getDeleteHash() {
		return deleteHash;
	}

	/**
	 * @return the gifvLink
	 */
	public String getGifvLink() {
		return gifvLink;
	}

	/**
	 * @return the mp4Link
	 */
	public String getMp4Link() {
		return mp4Link;
	}

	/**
	 * @return the webmLink
	 */
	public String getWebmLink() {
		return webmLink;
	}

	/**
	 * @return the looping
	 */
	public boolean isLooping() {
		return looping;
	}

	/**
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
	
	// INTERNAL ONLY
	public GalleryImage() { }
	
	// INTERNAL ONLY
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
