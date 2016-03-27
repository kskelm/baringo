/**
 * 
 */
package com.github.kskelm.imgurapi.model;

import java.util.Date;
import java.util.HashMap;

import com.github.kskelm.imgurapi.util.Utils;
import com.google.gson.annotations.SerializedName;

public class Image {
	
	
	/**
	 * A list of the available thumbnail sizes for images
	 */
	public enum ThumbnailType {
		/**
		 * Small square 90x90 (not proportional)
		 */
		SmallSquare,
		/**
		 * Big square 160x160 (not proportional)
		 */
		BigSquare,
		/**
		 * Small 160x160
		 */
		Small,
		/**
		 * Medium 320x320
		 */
		Medium,
		/**
		 * Large 640x640
		 */
		Large,
		/**
		 * Huge 1024x1024
		 */
		Huge
	}
	
	/**
	 * Given a thumbnail size, returns the link for the
	 * corresponding thumbnail file.
	 * @param tt - the thumbnail time
	 * @return thumbnail link
	 */
	public String getThumbnailLink( ThumbnailType tt ) {
		int dotAt = link.lastIndexOf( '.' );
		String suffix = suffixes.get(tt);
		return link.substring( 0, dotAt ) + suffix + link.substring( dotAt );
	} // getThumbnailLink
	
	/**
	 * The ID for the image
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * The title of the image.
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Description of the image.
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Date/time of upload
	 * @return the uploadDate
	 */
	public Date getUploadDate() {
		return uploadDate;
	}

	/**
	 *  	Image MIME type.
	 *  @return the mimeType
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 *  	is the image animated
	 *  @return the animated
	 */
	public boolean isAnimated() {
		return animated;
	}

	/**
	 *  	The width of the image in pixels
	 *  @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 *  	The height of the image in pixels
	 *  @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * The size of the image in bytes
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * The number of image views
	 * @return the views
	 */
	public int getViews() {
		return views;
	}

	/**
	 * Bandwidth consumed by the image in bytes
	 * @return the bandwidth
	 */
	public long getBandwidth() {
		return bandwidth;
	}

	/**
	 *  	OPTIONAL, the deletehash, if you're logged in as the image owner
	 * @return the deletehash
	 */
	public String getDeletehash() {
		return deletehash;
	}

	/**
	 *  OPTIONAL, the original filename, if you're logged in as the image owner
	 *  @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * If the image has been categorized by the backend then this will contain the section the image belongs in. (funny, cats, adviceanimals, wtf, etc) link
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * The direct link to the the image. (Note: if fetching an animated GIF that was over 20MB in original size, a .gif thumbnail will be returned)
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * OPTIONAL, The .gifv link. Only available if the image is animated and type is 'image/gif'.
	 * @return the gifv
	 */
	public String getGifv() {
		return gifv;
	}

	/**
	 * OPTIONAL, The direct link to the .mp4. Only available if the image is animated and type is 'image/gif'.
	 * @return the mp4
	 */
	public String getMp4() {
		return mp4;
	}

	/**
	 *  	OPTIONAL, The direct link to the .webm. Only available if the image is animated and type is 'image/gif'.
	 *  @return the webm
	 */
	public String getWebm() {
		return webm;
	}

	/**
	 * OPTIONAL, Whether the image has a looping animation. Only available if the image is animated and type is 'image/gif'.
	 * @return the looping
	 */
	public boolean isLooping() {
		return looping;
	}

	/**
	 * Indicates if the image has been marked as nsfw or not. Defaults to false if information is not available.
	 * @return the nsfw
	 */
	public boolean isNsfw() {
		return nsfw;
	}

	/**
	 * Indicates if the current user favorited the image. Defaults to false if not signed in.
	 * @return the favorite
	 */
	public boolean isFavorite() {
		return favorite;
	}

	/**
	 *  	The current user's vote on the album. null if not signed in, if the user hasn't voted on it, or if not submitted to the gallery.
	 *  @return the vote
	 */
	public String getVote() {
		return vote;
	}

	public String toString() {
		return Utils.toString( this );
	} // toString
	
	// =====================================================
	
	private String id;
	private String title;
	private String description;
	@SerializedName("datetime")
	private Date uploadDate;
	@SerializedName("type")
	private String mimeType;
	private boolean animated;
	private int width;
	private int height;
	private int size;
	private int views;
	private long bandwidth;
	private String deletehash;
	private String name;
	private String section;
	private String link;
	private String gifv;
	private String mp4;
	private String webm;
	private boolean looping;
	private boolean nsfw;
	private boolean favorite;
	private String vote;

	private static HashMap<ThumbnailType,String> suffixes = new HashMap<>();
	static {
		suffixes.put( ThumbnailType.SmallSquare, "s" );
		suffixes.put( ThumbnailType.BigSquare, "b" );
		suffixes.put( ThumbnailType.Small, "t" );
		suffixes.put( ThumbnailType.Medium, "m" );
		suffixes.put( ThumbnailType.Large, "l" );
		suffixes.put( ThumbnailType.Huge, "h" );
	}
}
