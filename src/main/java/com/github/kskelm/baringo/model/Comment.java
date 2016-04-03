package com.github.kskelm.baringo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.kskelm.baringo.util.Utils;
import com.google.gson.annotations.SerializedName;

public class Comment {

	
	/**
	 * When requesting a list of comments this is the order.
	 * Note that loading comments for an image/album is done
	 * from those services, not this one.
	 */
	public enum Sort {
		/**
		 * Sort comments with the newest ones first. Default
		 */
		@SerializedName("newest") Newest,  // default
		/**
		 * Sort comments with the oldest first
		 */
		@SerializedName("oldest") Oldest,
		/**
		 * Sort comments with the top-rated ones first
		 */
		@SerializedName("best") Best,
		/**
		 * Sort comments with the worst-rated ones first (why?)
		 */
		@SerializedName("worst") Worst;
	}

	/**
	 * When reporting a comment as inappropriate, this is
	 * the reason code to pass in.
	 */
	public enum ReportReason {
		/**
		 * Ambiguously rejected
		 */
		@SerializedName("1") DoesntBelongOnImgur,
		/**
		 * The image is advertising spam
		 */
		@SerializedName("2") Spam,
		/**
		 * The image includes abusive or hateful content
		 */
		@SerializedName("3") Abusive,
		/**
		 * Not safe for work, but not marked that way
		 */
		@SerializedName("4") ShouldBeMarkedNsfw,
		/**
		 * Ambiguously rejected
		 */
		@SerializedName("5") Pornography,
	}

	/**
	 * When voting, these are the two values you can use.
	 * Removing a vote means sending the same value again.
	 */
	public enum Vote {
		@SerializedName("up") Up,
		@SerializedName("down") Down
	}
	
	/**
	 * The ID of the comment
	 * @return the id
	 */
	public long getId() {
		return id;
	}


	/**
	 * The ID of the image the comment is for
	 * @return the imageId
	 */
	public String getImageId() {
		return imageId;
	}


	/**
	 * The comment itself
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}


	/**
	 * The userName of the author
	 * @return the author
	 */
	public String getAuthorName() {
		return authorName;
	}


	/**
	 * The account ID of the author
	 * @return the authorId
	 */
	public int getAuthorId() {
		return authorId;
	}


	/**
	 * True if the comment was made on an album
	 * @return the onAlbum
	 */
	public boolean isOnAlbum() {
		return onAlbum;
	}


	/**
	 * The ID of the album cover image to display with this comment, if any
	 * @return the albumCover
	 */
	public String getAlbumCover() {
		return albumCover;
	}


	/**
	 * Number of upvotes for the comment
	 * @return the ups
	 */
	public int getUps() {
		return ups;
	}


	/**
	 * Number of downvotes for the comment
	 * @return the downs
	 */
	public int getDowns() {
		return downs;
	}


	/**
	 * Number of upvotes - downvotes (math is hard)
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}


	/**
	 * Date/time the comment was created
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}


	/**
	 * If this is a reply, the comment ID it is replying to
	 * @return the parentId, or 0 if none
	 */
	public long getParentId() {
		return parentId;
	}


	/**
	 * True if this comment has been deleted
	 * @return the deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}


	/**
	 * The current authenticated user's vote on this comment.
	 * Null if not currently signed in or if the user hasn't offered
	 * an opinion yet.
	 * @return the vote
	 */
	public Vote getVote() {
		return vote;
	}


	/**
	 * Get all of the replies for this comment.  If none, this
	 * will be an empty list rather than a null.
	 * @return the children
	 */
	public List<Comment> getChildren() {
		return children;
	}


	// ================================================
	private long id;
	@SerializedName("image_id")
	private String imageId;
	private String comment;
	@SerializedName("author")
	private String authorName;
	@SerializedName("author_id")
	private int authorId;
	@SerializedName("on_album")
	private boolean onAlbum;
	@SerializedName("album_cover")
	private String albumCover;
	private int ups;
	private int downs;
	private int points;
	@SerializedName("datetime")
	private Date createdAt;
	@SerializedName("parent_id")
	private long parentId;
	private boolean deleted;
	private Vote vote;
	private List<Comment> children = new ArrayList<>();


	public String toString() {
		return Utils.toString( this );
	} // toString
}
