/**
 * imgur account 
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
		newest,  // default
		oldest
	}

	/**
	 * @return  Account ID for the username requested
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return Account username, will be the same as requested in the URL
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return Basic description the user has filled out
	 */
	public String getBio() {
		return bio;
	}

	/**
	 * @return The reputation for the account, in its numerical format
	 */
	public double getReputation() {
		return reputation;
	}

	/**
	 * @return Date of account creation.
	 */
	public Date getCreated() {
		return created;
	}

	private int id;
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
