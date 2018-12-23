package com.download.model;

public class DownloadConstants {
	
	
	public static final String URL_REGEX =
			"^((((https?|sftp?|gopher|ftp|telnet|nntp)://)|(mailto:|news:))" +
			"(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)([).!';/?:,][[:blank:]])?$";
	
	public static final String HTTP = "http";
	public static final String HTTPS = "https";
	public static final String FTP = "ftp";
	public static final String SFTP = "sftp";
	public final static int MAX_BUFFER_SIZE_SPEED= 4096;
	public final static int MIN_BUFFER_SIZE_SPEED= 1024;
	public final static Long BIG_DATA_SIZE=2147483648L;
	public static final String FAST = "FAST";
	public static final String SLOW = "SLOW";

}
