/**
 * Model object for items found in a gallery.
 * This is an abstract superclass for GalleryImage
 * and GalleryAlbum, which are both returned
 * by various Imgur APIs and they share a notable
 * number of characteristics.  Check the class
 * type of an instance and cast it to get the
 * appropriate version.
 */
package com.github.kskelm.imgurapi.model;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import com.github.kskelm.imgurapi.util.Utils;
import com.google.gson.annotations.SerializedName;

public class GalleryItem {

	/**
	 * When requesting a gallery, this is the section
	 * 
	 */
	public enum Section {
		@SerializedName("hot") Hot,  // default
		@SerializedName("top") Top,
		@SerializedName("user") User;

	}
	
	/**
	 * When requesting a gallery, this is the sort to use
	 */
	public enum Sort {
		@SerializedName("viral") Viral,  // default
		@SerializedName("top") Top,
		@SerializedName("time") Time,
		@SerializedName("rising") Rising;  // valid only with user section

	}
	
	/**
	 * When requesting the section type "top",
	 * this changes the date range of the request.
	 * The default is Day.
	 */
	public enum Window {
		@SerializedName("day") Day,  // default
		@SerializedName("week") Week,
		@SerializedName("month") Month,
		@SerializedName("year") Year;
		
		@Override public String toString() {
			return this.toString().toLowerCase();
		}
	}

// TODO: COMMENT METHODS
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the insertedDate
	 */
	public Date getInsertedDate() {
		return insertedDate;
	}

	/**
	 * @return the views
	 */
	public int getViews() {
		return views;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @return the vote
	 */
	public String getVote() {
		return vote;
	}

	/**
	 * @return the ups
	 */
	public int getUps() {
		return ups;
	}

	/**
	 * @return the downs
	 */
	public int getDowns() {
		return downs;
	}

	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @return the favorite
	 */
	public boolean isFavorite() {
		return favorite;
	}

	/**
	 * @return the nsfw
	 */
	public boolean isNsfw() {
		return nsfw;
	}

	/**
	 * @return the commentCount
	 */
	public int getCommentCount() {
		return commentCount;
	}

	/**
	 * @return the commentPreview
	 */
	public List<Comment> getCommentPreview() {
		return commentPreview;
	}

	/**
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * @return the topicId
	 */
	public int getTopicId() {
		return topicId;
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @return the accountId
	 */
	public int getAccountId() {
		return accountId;
	}

	
	
	// =========================================================
	
	public GalleryItem() { }

	protected GalleryItem( GalleryItemProxy p ) {
		copyFrom( p, GalleryItem.class );
	} // lame copy constructor
	
	protected String id;
	protected String title;
	protected String description;
	@SerializedName("datetime")
	protected Date insertedDate;
	protected int views;
	protected String link;
	protected String vote;
	protected int ups;
	protected int downs;
	protected int points;
	protected int score;
	protected boolean favorite;
	protected boolean nsfw;
	@SerializedName("comment_count")
	protected int commentCount;
	@SerializedName("comment_preview")
	protected List<Comment> commentPreview;
	protected String topic;
	@SerializedName("topic_id")
	protected int topicId;
	@SerializedName("account_url")
	protected String accountName;
	@SerializedName("account_id")
	protected int accountId;
	
	public String toString() {
		return Utils.toString( this );
	} // toString

	/**
	 * (INTERNAL) Shallow copy the fields from one object to another, given
	 * named fields in a specific level of the inheritance.
	 * This makes me sad.
	 * @param from - destination object
	 */
	public void copyFrom( GalleryItemProxy from, @SuppressWarnings("rawtypes") Class cls ) {
		for( Field toField : cls.getDeclaredFields() ) {
			try {
				Field fromField = from.getClass().getField( toField.getName() );
				if( fromField == null ) { // doesn't exist in the source
					continue;
				} // if
				Object val = fromField.get( from );
				toField.set( this, val );
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		} // for
	} // copyFrom
}
