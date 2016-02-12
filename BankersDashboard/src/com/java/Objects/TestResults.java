package com.java.Objects;

import java.util.Date;

/**
 * This is to store each row details form Test Data Details Excel Sheet
 */
public class TestResults {
	
	private int TCID;
	private String TCTitle;
	private String result;
	private String errorMSG;
	private Date timeStamp;

	/**
	 * @param TCID The TCID to set.
	 */
	public void setTCID(int tcid) {
		this.TCID = tcid;
	}
	/**
	 * @return Returns the TCID.
	 */
	public int getTCID() {
		return	TCID;
	}
	/**
	 * @return Returns the TCTitle.
	 */
	public String getTCTitle() {
		return TCTitle;
	}
	/**
	 * @param tCTitle The tCTitle to set.
	 */
	public void setTCTitle(String tCTitle) {
		this.TCTitle = tCTitle;
	}
	/**
	 * @return Returns the result.
	 */
	public String getResult() {
		return result;
	}
	/**
	 * @param result The result to set.
	 */
	public void setResult(String result) {
		this.result = result;
	}
	/**
	 * @return Returns the errorMSG.
	 */
	public String getErrorMsg() {
		return errorMSG;
	}
	/**
	 * @param errorMSG The errorMSG to set.
	 */
	public void setErrorMsg(String errorMSG) {
		if(errorMSG == null){
			errorMSG = "";
		}
		this.errorMSG = errorMSG;
	}
	/**
	 * @return Returns the timeStamp.
	 */
	public Date getTime_Stamp() {
		return timeStamp;
	}
	/**
	 * @param timeStamp The timeStamp to set.
	 */	
	public void setTime_Stamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	

}
