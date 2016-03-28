/**
 * Imgur API 3 Account POJO
 */
package com.github.kskelm.imgurapi.model;

import java.util.Date;

import com.github.kskelm.imgurapi.util.Utils;
import com.google.gson.annotations.SerializedName;

/**
 * @author kskelm
 *
 */

public class Account {

	/**
	 * When returning gallery items, sort by this
	 */
	public enum GallerySort {
		/**
		 * Order results newest first (this is the default)
		 */
		newest,  // default
		/**
		 * Order results oldest first
		 */
		oldest
	}

	/**
	 * @return  Account ID for the username requested
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the userName associated with this account
	 * @return Account userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Returns the basic description the user filled out to appear in their gallery page.
	 * This is set via setAccountSettings()
	 * @return description
	 */
	public String getBio() {
		return bio;
	}

	/**
	 * Returns The reputation for the account, in its numerical format
	 * @return reputation
	 */
	public double getReputation() {
		return reputation;
	}

	/**
	 * Returns the date/time the account was created.
	 * @return Date of account creation.
	 */
	public Date getCreated() {
		return created;
	}

	// ================================================
	private int id;
	@SerializedName("url")
	private String userName;
	private String bio;
	private double reputation;
	private Date created;
	@SerializedName("pro_expiration")
	private Date proExpiration;

	public String toString() {
		return Utils.toString( this );
	} // toString
} // class Account
