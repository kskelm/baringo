/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo.model;


/**
 * 
 * This class is not meant to be seen by human eyes. It's
 * the outer wrapper returned by all Imgur API calls.  It
 * conveys status and wraps the actual result.  It's
 * used by the Retrofit2 code internally only.
 * @author Kevin Kelm (triggur@gmail.com)
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
