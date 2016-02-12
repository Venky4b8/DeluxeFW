package com.java.Objects;

public class TestExecutionDetails {
	int totalTestCases;
	int totalExecuted;
	int passed;
	int failed;
	int skipped;
	
	String htmlReportPath;
	String htmlSharedPath;
	String excelReportPath;
	String URL;
	String startTime="-";
	String endTime="-";
	String logFile;
	
	/**
	 * @param totalTestCases The totalTestCases to set.
	 */
	public void setTotalTestCases(int totalTestCases){
		this.totalTestCases=totalTestCases;		
	}
	/**
	 * @return Returns the totalTestCases.
	 */
	public int getTotalTestCases(){
		return totalTestCases;
	}
	/**
	 * @param totalExecuted The totalExecuted to set.
	 */
	public void setTotalExecuted(int totalExecuted){
		this.totalExecuted=totalExecuted;		
	}
	/**
	 * @return Returns the totalExecuted.
	 */
	public int getTotalExecuted(){
		return totalExecuted;
	}	
	/**
	 * @param passed The passed to set.
	 */
	public void setPassed(int passed){
		this.passed=passed;		
	}
	/**
	 * @return Returns the passed.
	 */
	public int getPassed(){
		return passed;
	}
	/**
	 * @param failed The failed to set.
	 */
	public void setFailed(int failed){
		this.failed=failed;		
	}
	/**
	 * @return Returns the failed.
	 */
	public int getFailed(){
		return failed;
	}
	/**
	 * @param skipped The skipped to set.
	 */	
	public void setSkipped(int skipped){
		this.skipped=skipped;
	}
	/**
	 * @return Returns the skipped.
	 */
	public int getSkipped(){
		return skipped;
	}
	/**
	 * @param htmlReportPath The htmlReportPath to set.
	 */	
	public void setHTMLReportPath(String htmlReportPath){
		this.htmlReportPath=htmlReportPath;
	}
	/**
	 * @return Returns the htmlReportPath.
	 */
	public String getHTMLReportPath(){
		return htmlReportPath;
	}
	/**
	 * @param htmlSharedPath The htmlSharedPath to set.
	 */	
	public void setHTMLSharedPath(String htmlSharedPath){
		this.htmlSharedPath=htmlSharedPath;
	}
	/**
	 * @return Returns the htmlSharedPath.
	 */
	public String getHTMLSharedPath(){
		return htmlSharedPath;
	}
	/**
	 * @param excelReportPath The excelReportPath to set.
	 */	
	public void setExcelReportPath(String excelReportPath){
		this.excelReportPath=excelReportPath;
	}
	/**
	 * @return Returns the excelReportPath.
	 */
	public String getExcelReportPath(){
		return excelReportPath;
	}
	/**
	 * @param setURL The setURL to set.
	 */	
	public void setURL(String URL){
		this.URL=URL;
	}
	/**
	 * @return Returns the URL.
	 */
	public String getURL(){
		return URL;
	}
	/**
	 * @param startTime The startTime to set.
	 */	
	public void setStartTime(String startTime){
		this.startTime=startTime;
	}
	/**
	 * @return Returns the startTime.
	 */
	public String getStartTime(){
		return startTime;
	}	
	/**
	 * @param endTime The endTime to set.
	 */	
	public void setEndTime(String endTime){
		this.endTime=endTime;
	}
	/**
	 * @return Returns the endTime.
	 */
	public String getEndTime(){
		return endTime;
	}
	/**
	 * @param logFile The logFile to set.
	 */	
	public void setLogFile(String logFile){
		this.logFile=logFile;
	}
	/**
	 * @return Returns the logFile.
	 */	
	public String getLogFile(){
		 return logFile;
	}
}
