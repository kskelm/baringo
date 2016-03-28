/**
 * Represents changes to the account settings.  This is a different
 * class from AccountSettings because the Imgur API 3 getter/setter
 * endpoints are assymetrical:
 *   - bio is set here but returned in account, not account settings
 *   - username here is url in account and account_url in account settings
 */
package com.github.kskelm.imgurapi.model;

import com.github.kskelm.imgurapi.util.Utils;
import com.google.gson.annotations.SerializedName;

/**
 * @author kskelm
 *
 */

public class ChangedAccountSettings {


	/**
	 * Get the biography of the user that's displayed in the gallery profile page
	 * @return the bio
	 */
	public String getBio() {
		return bio;
	}

	/**
	 * Set the biography of the user to be displayed in the gallery profile page
	 * @param bio the bio to set
	 */
	public void setBio(String bio) {
		this.bio = bio;
	}

	/**
	 * Get whether or not the user's new images default to public instead of private
	 * @return the publicImages
	 */
	public boolean isPublicImages() {
		return publicImages;
	}

	/**
	 * Set whether or not the user's new images default to public instead of private
	 * @param publicImages the publicImages to set
	 */
	public void setPublicImages(boolean publicImages) {
		this.publicImages = publicImages;
	}

	/**
	 * Get whether or not the user has opted to receive private messages
	 * @return the messagingEnabled
	 */
	public boolean isMessagingEnabled() {
		return messagingEnabled;
	}

	/**
	 * Set whether or not the user has opted to receive private messages
	 * @param messagingEnabled the messagingEnabled to set
	 */
	public void setMessagingEnabled(boolean messagingEnabled) {
		this.messagingEnabled = messagingEnabled;
	}

// TODO: Put in Album, Public/Hidden/Secret
//	/**
//	 * Get the default privacy level of new albums the user creates
//	 * @return the albumPrivacy
//	 */
//	public AlbumPrivacy getAlbumPrivacy() {
//		return albumPrivacy;
//	}
//
//	/**
//	 * Set the default privacy level of new albums the user creates
//	 * @param albumPrivacy the albumPrivacy to set
//	 */
//	public void setAlbumPrivacy(AlbumPrivacy albumPrivacy) {
//		this.albumPrivacy = albumPrivacy;
//	}

	/**
	 * Get whether or not the user has accepted the Imgur Gallery terms
	 * @return the acceptedGalleryTerms
	 */
	public boolean isAcceptedGalleryTerms() {
		return acceptedGalleryTerms;
	}

	/**
	 * Set whether or not the user has accepted the Imgur Gallery terms
	 * @param acceptedGalleryTerms the acceptedGalleryTerms to set
	 */
	public void setAcceptedGalleryTerms(boolean acceptedGalleryTerms) {
		this.acceptedGalleryTerms = acceptedGalleryTerms;
	}

	/**
	 * Get the user's username.
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Set the user's username, 4-63 characters, must be unique.
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Get whether or not the user has opted to see mature content in the galleries
	 * @return the showMature
	 */
	public boolean getShowMature() {
		return showMature;
	}

	/**
	 * Set whether or not the user has opted to see mature content in the galleries
	 * @param showMature the showMature to set
	 */
	public void setShowMature(boolean showMature) {
		this.showMature = showMature;
	}

	/**
	 * Get whether or not the user is subscribed to receive the email newsletters
	 * @return the newsletterSubscribed
	 */
	public boolean isNewsletterSubscribed() {
		return newsletterSubscribed;
	}

	/**
	 * Set whether or not the user is subscribed to receive the email newsletters
	 * @param newsletterSubscribed the newsletterSubscribed to set
	 */
	public void setNewsletterSubscribed(boolean newsletterSubscribed) {
		this.newsletterSubscribed = newsletterSubscribed;
	}

	// ============================================================
	private String bio;
	@SerializedName("public_images")
	private boolean publicImages;
	@SerializedName("messaging_enabled")
	private boolean messagingEnabled;
// TODO: reference Album object
//		@SerializedName("album_privacy")
//		private Album.Privacy albumPrivacy;
	@SerializedName("accepted_gallery_terms")
	private boolean acceptedGalleryTerms;
	@SerializedName("username")
	private String userName;
	@SerializedName("show_mature")
	private boolean showMature;
	@SerializedName("newsletter_subscribed")
	private boolean newsletterSubscribed;

	public ChangedAccountSettings() {
		System.err.println("TODO: Add Album.Privacy as albumPrivacy ");
	}

	@Override
	public String toString() {
		return Utils.toString( this );
	}

} // class AccountSettings
