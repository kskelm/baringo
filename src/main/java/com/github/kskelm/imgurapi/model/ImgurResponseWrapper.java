/**
 * hidden wrapper for all imgur responses
 */
package com.github.kskelm.imgurapi.model;


/**
 * @author kskelm
 * 
 * This class isn't meant to be seen by human eyes.
 * It just exists internally to represent the status wrapper that
 * comes back in all Imgur API 3 responses.
 *
 */

public class ImgurResponseWrapper<T> {

	/**
	 * Actual payload
	 */
	private T data;
	/**
	 * Success status
	 */
	private boolean success;
	/**
	 * HTTP status
	 */
	private int status;
	
	
	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}


	/**
	 * @param data the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}


	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}


	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}


	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return String.format(
				"ImgurResponseWrapper[success=%b, status=%d, data=%s]",
				success, status, data );
	} // toString

} // class ImgurResponseWrapper
