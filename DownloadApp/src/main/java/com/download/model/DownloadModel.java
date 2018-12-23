package com.download.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DownloadModel {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long downloadID;
	@Column(name="File_SOURCE")
	private String fileSource;
	@Column(name="FILE_DESTINATION")
	private String fileDestination;
	@Column(name="DOWNLOAD_START_TIME")
	private java.util.Date downloadStartTime;
	@Column(name="DOWNLOAD_END_TIME")
	private java.util.Date downloadEndTime;
	@Column(name="PROTOCOL")
	private String protocol;
	@Column(name="LARGE_DATA_SIZE")
	private long largeData;
	@Column(name="DOWNLOAD_SPEED")
	private String downloadSpeed;
	@Column(name="PERCENTAGE_OF_FAILURE")
	private int percentageOfFailure;
	
	public DownloadModel()
	{
		
	}
	public String getDownloadSpeed() {
		return downloadSpeed;
	}

	public void setDownloadSpeed(String downloadSpeed) {
		this.downloadSpeed = downloadSpeed;
	}
	
	public Long getDownloadID() {
		return downloadID;
	}
	public void setDownloadID(Long downloadID) {
		this.downloadID = downloadID;
	}

	public String getFileSource() {
		return fileSource;
	}

	public void setFileSource(String fileSource) {
		this.fileSource = fileSource;
	}

	public String getFileDestination() {
		return fileDestination;
	}

	public void setFileDestination(String fileDestination) {
		this.fileDestination = fileDestination;
	}

	public java.util.Date getDownloadStartTime() {
		return downloadStartTime;
	}

	public void setDownloadStartTime(java.util.Date downloadStartTime) {
		this.downloadStartTime = downloadStartTime;
	}

	public java.util.Date getDownloadEndTime() {
		return downloadEndTime;
	}

	public void setDownloadEndTime(java.util.Date downloadEndTime) {
		this.downloadEndTime = downloadEndTime;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public long getLargeData() {
		return largeData;
	}

	public void setLargeData(long largeData) {
		this.largeData = largeData;
	}


	public int getPercentageOfFailure() {
		return percentageOfFailure;
	}

	public void setPercentageOfFailure(int percentageOfFailure) {
		this.percentageOfFailure = percentageOfFailure;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(" File Source : ").append(this.fileSource);
		sb.append(" File Destination : ").append(this.fileDestination);
		sb.append(" File Download Start Time : ").append(this.downloadStartTime);
		sb.append(" File Download End Time : ").append(this.downloadEndTime);
		sb.append(" Protocol : ").append(this.protocol);
		sb.append(" Large Data : ").append(this.largeData);
		sb.append(" Slow Download : ").append(this.downloadSpeed);
		sb.append(" Percentage Of Failure : ").append(this.percentageOfFailure);
		return sb.toString();
	}
}
