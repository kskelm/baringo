/**
 * an account's gallery profile 
 */
package com.github.kskelm.imgurapi.model;

import java.util.ArrayList;

import com.github.kskelm.imgurapi.util.Utils;
import com.google.gson.annotations.SerializedName;

/**
 * @author kskelm
 *
 */

public class GalleryProfile {

	
	/**
	 * Total number of comments this user has made in the gallery
	 * @return the commentCount
	 */
	public int getCommentCount() {
		return commentCount;
	}

	/**
	 * Total number of items favorited by this user in the gallery
	 * @return the favoriteCount
	 */
	public int getFavoriteCount() {
		return favoriteCount;
	}

	/**
	 * Total number of images submitted by this user
	 * @return the submissionCount
	 */
	public int getSubmissionCount() {
		return submissionCount;
	}

	/**
	 * A list of the trophies the user has earned
	 * @return the trophies
	 */
	public ArrayList<Trophy> getTrophies() {
		return trophies;
	}

	// ===================================================
	@SerializedName("total_gallery_comments")
	private int commentCount;
	@SerializedName("total_gallery_favorites")
	private int favoriteCount;
	@SerializedName("total_gallery_submissions")
	private int submissionCount;
	private ArrayList<Trophy> trophies;
	
	@Override
	public String toString() {
		return Utils.toString( this );
	}

} // class AccountSettings
