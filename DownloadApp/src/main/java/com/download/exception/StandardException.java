package com.download.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StandardException extends RuntimeException
{
	final static Logger logger = LoggerFactory.getLogger(StandardException.class);
	private static final long serialVersionUID = 1L;
	private String myMessage = "";
	private long myErrorCode;
	
	public String getMyMessage() {
		return myMessage;
	}

	public void setMyMessage(String myMessage) {
		this.myMessage = myMessage;
	}

	public long getMyErrorCode() {
		return myErrorCode;
	}

	public void setMyErrorCode(long myErrorCode) {
		this.myErrorCode = myErrorCode;
	}
	
	public StandardException()
	{
		myErrorCode = 0;
	}
	
	public StandardException(String theReason)
	{
		super(theReason);
	}
	
	public StandardException(String theReason, long theErrorCode)
	{
		myMessage = theReason;
		myErrorCode = theErrorCode;
		if(logger.isDebugEnabled())
		{
			logger.info(myMessage, myErrorCode);
		}
	}
	
	public StandardException(String theReason, long theErrorCode, Throwable cause)
	{
		myMessage = theReason;
		myErrorCode = theErrorCode;
		if(logger.isDebugEnabled())
		{
			logger.info(myMessage, myErrorCode, cause);
		}
	}

}
