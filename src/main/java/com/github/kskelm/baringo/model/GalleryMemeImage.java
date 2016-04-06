/**
 * Model object for images found in the meme gallery,
 * extended with meme metadata.
 */
package com.github.kskelm.baringo.model;

import java.util.HashMap;

import com.github.kskelm.baringo.util.Utils;
import com.google.gson.annotations.SerializedName;

/**
 * @author kskelm
 * UPDATE: apparently Imgur doesn't really support this anymore at least
 * as of 6/24/15.  https://groups.google.com/forum/#!msg/imgur/BEyZryAhGi0/yfOFyixuPy4J
 * ... MARK THIS CLASS FOR DELETION
 *
 */

public class GalleryMemeImage extends GalleryImage {

	public String toString() {
		HashMap<String,Object> fields = Utils.toHashMap( this, GalleryMemeImage.class );
		fields.putAll( Utils.toHashMap( this, GalleryImage.class ) );
		fields.putAll( Utils.toHashMap( this, GalleryItem.class ) );
		return Utils.toString( fields, GalleryMemeImage.class.getSimpleName() );
	} // toString
	
	// ======================================================
	
	// INTERNAL ONLY
	public GalleryMemeImage() { }
	
	// INTERNAL ONLY
	public GalleryMemeImage( GalleryItemProxy p ) {
		super( p );
		copyFrom( p, GalleryMemeImage.class );
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
	@SerializedName("meme_metadata")
	protected MemeMetaData memeMetaData;

}
