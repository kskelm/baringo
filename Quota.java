/**
 * Provides insight into quota status for a given
 * client account.
 * 
 * @link http://api.imgur.com/#limits
 */
package com.github.kskelm.baringo;

import java.util.Date;

import com.github.kskelm.baringo.util.Utils;
public class Quota {

	
	/**
	 * The number of credits allocated to this IP address per hour
	 * @return the userCreditsAllocated
	 */
	public int getUserCreditsAllocated() {
		return userCreditsAllocated;
	}
	/**
	 * The number of credits left to this IP address this hour
	 * @return the userCreditsAvailable
	 */
	public int getUserCreditsAvailable() {
		return userCreditsAvailable;
	}
	/**
	 * The date/time that the this IP address's credits will be reset
	 * @return the userCreditResetDate
	 */
	public Date getUserCreditResetDate() {
		return userCreditResetDate;
	}
	/**
	 * The number of credits this registered client is allowed per day
	 * @return the applicationCreditsAllocated
	 */
	public int getApplicationCreditsAllocated() {
		return applicationCreditsAllocated;
	}
	/**
	 * The number of remaining credits this registered client has left today
	 * @return the applicationCreditsAvailable
	 */
	public int getApplicationCreditsAvailable() {
		return applicationCreditsAvailable;
	}
	/**
	 * Across all usage of this registered client, the number of post credits
	 * allocated to this client per hour.  Pro clients get more.
	 * This value is set only after a POST call is returned
	 * @return the postCreditsAllocated
	 */
	public int getPostCreditsAllocated() {
		return postCreditsAllocated;
	}
	/**
	 * Across all usage of this registered client, the number of post credits
	 * left this hour.
	 * This value is set only after a POST call is returned.
	 * @return the postCreditsAvailable
	 */
	public int getPostCreditsAvailable() {
		return postCreditsAvailable;
	}
	/**
	 * The date/time that the post credit limit will be reset.
	 * This value is set only after a POST call is returned.
	 * @return the postCreditReset
	 */
	public Date getPostCreditResetDate() {
		return postCreditResetDate;
	}
	
	@Override
	public String toString() {
		return Utils.toString( this );
	} // toString
	
	// ================================================
	/**
	 * @param userCreditsAllocated the userCreditsAllocated to set
	 */
	protected void setUserCreditsAllocated(int userCreditsAllocated) {
		this.userCreditsAllocated = userCreditsAllocated;
	}
	/**
	 * @param userCreditsAvailable the userCreditsAvailable to set
	 */
	protected void setUserCreditsAvailable(int userCreditsAvailable) {
		this.userCreditsAvailable = userCreditsAvailable;
	}
	/**
	 * @param userCreditResetDate the userCreditResetDate to set
	 */
	protected void setUserCreditResetDate(Date userCreditResetDate) {
		this.userCreditResetDate = userCreditResetDate;
	}
	/**
	 * @param applicationCreditsAllocated the applicationCreditsAllocated to set
	 */
	protected void setApplicationCreditsAllocated(int applicationCreditsAllocated) {
		this.applicationCreditsAllocated = applicationCreditsAllocated;
	}
	/**
	 * @param applicationCreditsAvailable the applicationCreditsAvailable to set
	 */
	protected void setApplicationCreditsAvailable(int applicationCreditsAvailable) {
		this.applicationCreditsAvailable = applicationCreditsAvailable;
	}
	/**
	 * @param postCreditsAllocated the postCreditsAllocated to set
	 */
	protected void setPostCreditsAllocated(int postCreditsAllocated) {
		this.postCreditsAllocated = postCreditsAllocated;
	}
	/**
	 * @param postCreditsAvailable the postCreditsAvailable to set
	 */
	protected void setPostCreditsAvailable(int postCreditsAvailable) {
		this.postCreditsAvailable = postCreditsAvailable;
	}
	/**
	 * @param postCreditResetDate the postCreditResetDate to set
	 */
	protected void setPostCreditResetDate(Date postCreditResetDate) {
		this.postCreditResetDate = postCreditResetDate;
	}
	
	private int userCreditsAllocated;
	private int userCreditsAvailable;
	private Date userCreditResetDate;
	private int applicationCreditsAllocated;
	private int applicationCreditsAvailable;
	
	private int postCreditsAllocated;
	private int postCreditsAvailable;
	private Date postCreditResetDate;
} // Quota
