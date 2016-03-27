/**
 * imgur account 
 */
package com.github.kskelm.imgurapi.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * @author kskelm
 *
 */

public class AccountSettings {


	/**
	 * The account username
	 */
	private String accountUrl;
	/**
	 * The user's email address
	 */
	private String email;
	/**
	 * True if the user us abke to upload higher quality images with less compression
	 */
	@SerializedName("high_quality")
	private boolean highQuality;
	/**
	 * True if user is automatically allowing all images to be publically accessible
	 */
	@SerializedName("public_images")
	private boolean publicImages;
	/**
	 * Set the album privacy to this privacy setting on creation
	 */
	@SerializedName("album_privacy")
	private String albumPrivacy;
	/**
	 * Expiration date if pro, null if not
	 */
	@SerializedName("pro_expiration")
	private Integer proExpiration;
	/**
	 * True if the user has accepted the terms of uploading to the Imgur gallery
	 */
	@SerializedName("accepted_gallery_terms")
	private boolean acceptedGalleryTerms;
	/**
	 * The email addresses that have been activated to allow uploading
	 */
	@SerializedName("active_emails")
	private ArrayList<String> activeEmails;
	/**
	 * True if the user is accepting incoming messages
	 */
	@SerializedName("messaging_enabled")
	private boolean messagingEnabled;
	/**
	 * Array of users that have been blocked from messaging this user
	 */
	@SerializedName("blocked_users")
	private ArrayList<BlockedUser> blockedUsers;
	/**
	 * True if the user has opted to have mature images displayed in gallery lists
	 */
	@SerializedName("show_mature")
	private boolean showMature;


	@Override
	public String toString() {
		return String.format(
				"AccountSettings[accountUrl=%s, email=%s, highQuality=%b, publicImages=%b, albumPrivacy=%s, proExpiration=%d, acceptedGalleryTerms=%b, activeEmails=%s, messagingEnabled=%b, blockedUsers=%s, showMature=%b]",
				accountUrl, email, highQuality, publicImages, albumPrivacy, proExpiration, acceptedGalleryTerms, activeEmails, messagingEnabled, blockedUsers, showMature);
	}

} // class AccountSettings
