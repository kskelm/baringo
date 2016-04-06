package com.github.kskelm.baringo.model;

import com.google.gson.annotations.SerializedName;

/**
 * When voting, these are the two values you can use.
 * Removing a vote means sending the same value again.
 * @author kskelm
 * 
 */
public enum Vote {
	/**
	 * This represents an upvote
	 */
	@SerializedName("up") Up,
	/**
	 * This represents a downvote
	 */
	@SerializedName("down") Down
}
