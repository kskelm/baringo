/**
 * Model object for albums found in a gallery.
 * This is a subclass of GalleryItemProxy because
 * GalleryImage and GalleryAlbum are both returned
 * by various Imgur APIs and they share a notable
 * number of characteristics.  Check the class
 * type of an instance and cast it to get the
 * appropriate version.
 */
package com.github.kskelm.imgurapi.model;

import java.util.HashMap;
import java.util.List;

import com.github.kskelm.imgurapi.util.Utils;
import com.google.gson.annotations.SerializedName;

public class GalleryAlbum extends GalleryItem {

	/**
	 * A GalleryAlbum has a privacy level. {@link https://help.imgur.com/hc/en-us/articles/201746817-Image-and-album-privacy-explained-}
	 */
	public enum Privacy {
		@SerializedName("public") Public,
		@SerializedName("hidden") Hidden,
		@SerializedName("secret") Secret
	}

	/**
	 * A GalleryAlbum has a layout. {@link https://help.imgur.com/hc/en-us/articles/201746817-Image-and-album-privacy-explained-}
	 */
	public enum Layout {
		@SerializedName("blog") Blog,
		@SerializedName("grid") Grid,
		@SerializedName("horizontal") Horizontal,
		@SerializedName("vertical") Vertical
	}
	
	
	
	// TODO: COMMENT METHODS
	/**
	 * @return the cover
	 */
	public String getCover() {
		return cover;
	}

	/**
	 * @return the coverWidth
	 */
	public int getCoverWidth() {
		return coverWidth;
	}

	/**
	 * @return the coverHeight
	 */
	public int getCoverHeight() {
		return coverHeight;
	}

	/**
	 * @return the privacy
	 */
	public Privacy getPrivacy() {
		return privacy;
	}

	/**
	 * @return the layout
	 */
	public Layout getLayout() {
		return layout;
	}
	
	/**
	 * @return the imageCount
	 */
	public int getImageCount() {
		return imageCount;
	}

	/**
	 * Returns the list of Image objects
	 * in the GalleryAlbum.
	 * NOTE: This will only exist if the
	 * gallery album was requested
	 * directly.
	 * @return the images
	 */
	public List<Image> getImages() {
		return images;
	}

	
	public String toString() {
		HashMap<String,Object> fields = Utils.toHashMap( this, GalleryAlbum.class );
		fields.putAll( Utils.toHashMap( this, GalleryItem.class ) );
		return Utils.toString( fields, GalleryAlbum.class.getSimpleName() );
	} // toString
	
	// =========================================================

	// INTERNAL ONLY
	public GalleryAlbum() { }

	// INTERNAL ONLY
	public GalleryAlbum( GalleryItemProxy p ) {
		super( p );
		copyFrom( p, GalleryAlbum.class );
	} // lame copy constructor
	
	protected String cover;
	protected int coverWidth;
	protected int coverHeight;
	protected Privacy privacy;
	protected Layout layout;
	@SerializedName("images_count")
	protected int imageCount;
	protected List<Image> images;

} // GalleryAlbum
