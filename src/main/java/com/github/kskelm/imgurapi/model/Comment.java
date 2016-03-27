package com.github.kskelm.imgurapi.model;

import java.util.ArrayList;
import java.util.List;

import com.github.kskelm.imgurapi.util.Utils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {

	@SerializedName("id")
	@Expose
	private int id;
	@SerializedName("image_id")
	@Expose
	private String imageId;
	@SerializedName("comment")
	@Expose
	private String comment;
	@SerializedName("author")
	@Expose
	private String author;
	@SerializedName("author_id")
	@Expose
	private int authorId;
	@SerializedName("on_album")
	@Expose
	private boolean onAlbum;
	@SerializedName("album_cover")
	@Expose
	private String albumCover;
	@SerializedName("ups")
	@Expose
	private int ups;
	@SerializedName("downs")
	@Expose
	private int downs;
	@SerializedName("points")
	@Expose
	private int points;
	@SerializedName("datetime")
	@Expose
	private int datetime;
	@SerializedName("parent_id")
	@Expose
	private int parentId;
	@SerializedName("deleted")
	@Expose
	private boolean deleted;
	@SerializedName("vote")
	@Expose
	private Object vote;
	@SerializedName("children")
	@Expose
	private List<Comment> children = new ArrayList<Comment>();

	/**
	 *
	 * @return
	 * The id
	 */
	public int getId() {
		return id;
	}

	/**
	 *
	 * @param id
	 * The id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 *
	 * @return
	 * The imageId
	 */
	public String getImageId() {
		return imageId;
	}

	/**
	 *
	 * @param imageId
	 * The image_id
	 */
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	/**
	 *
	 * @return
	 * The comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 *
	 * @param comment
	 * The comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 *
	 * @return
	 * The author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 *
	 * @param author
	 * The author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 *
	 * @return
	 * The authorId
	 */
	public int getAuthorId() {
		return authorId;
	}

	/**
	 *
	 * @param authorId
	 * The author_id
	 */
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	/**
	 *
	 * @return
	 * The onAlbum
	 */
	public boolean isOnAlbum() {
		return onAlbum;
	}

	/**
	 *
	 * @param onAlbum
	 * The on_album
	 */
	public void setOnAlbum(boolean onAlbum) {
		this.onAlbum = onAlbum;
	}

	/**
	 *
	 * @return
	 * The albumCover
	 */
	public String getAlbumCover() {
		return albumCover;
	}

	/**
	 *
	 * @param albumCover
	 * The album_cover
	 */
	public void setAlbumCover(String albumCover) {
		this.albumCover = albumCover;
	}

	/**
	 *
	 * @return
	 * The ups
	 */
	public int getUps() {
		return ups;
	}

	/**
	 *
	 * @param ups
	 * The ups
	 */
	public void setUps(int ups) {
		this.ups = ups;
	}

	/**
	 *
	 * @return
	 * The downs
	 */
	public int getDowns() {
		return downs;
	}

	/**
	 *
	 * @param downs
	 * The downs
	 */
	public void setDowns(int downs) {
		this.downs = downs;
	}

	/**
	 *
	 * @return
	 * The points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 *
	 * @param points
	 * The points
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 *
	 * @return
	 * The datetime
	 */
	public int getDatetime() {
		return datetime;
	}

	/**
	 *
	 * @param datetime
	 * The datetime
	 */
	public void setDatetime(int datetime) {
		this.datetime = datetime;
	}

	/**
	 *
	 * @return
	 * The parentId
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 *
	 * @param parentId
	 * The parent_id
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	/**
	 *
	 * @return
	 * The deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 *
	 * @param deleted
	 * The deleted
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 *
	 * @return
	 * The vote
	 */
	public Object getVote() {
		return vote;
	}

	/**
	 *
	 * @param vote
	 * The vote
	 */
	public void setVote(Object vote) {
		this.vote = vote;
	}

	/**
	 *
	 * @return
	 * The children
	 */
	public List<Comment> getChildren() {
		return children;
	}

	/**
	 *
	 * @param children
	 * The children
	 */
	public void setChildren(List<Comment> children) {
		this.children = children;
	}

	public String toString() {
		return Utils.toString( this );
	} // toString
}
