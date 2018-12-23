package com.download.delegate;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.download.Repositiory.DownloadRepository;
import com.download.connector.FTPClient;
import com.download.connector.HTTPClient;
import com.download.exception.StandardException;
import com.download.model.DownloadConstants;
import com.download.model.DownloadModel;

@Service
public class DownloadDelegateImpl implements DownloadDelegate {

	final static Logger logger = LoggerFactory.getLogger(DownloadDelegateImpl.class);
	
	@Override
	public DownloadModel delegateConnectorRequest(String url, DownloadRepository downloadRepository,Environment environment) throws StandardException, IOException {

		try
		{
			if(url.startsWith(DownloadConstants.HTTP) || url.startsWith(DownloadConstants.HTTPS))
			{
				return	new HTTPClient().connectHost(url,downloadRepository,environment);
			}
			else if(url.startsWith(DownloadConstants.FTP) || url.startsWith(DownloadConstants.SFTP))
			{
				return	new FTPClient().connectHost(url,downloadRepository,environment);
			}
			else
			{
				throw new StandardException(url+ "doesn't support. Contact to administrator.");
			}
		}
		catch(StandardException exception)
		{
			logger.info(exception.getMessage());
			throw new RuntimeException("Error received during downloading resource");
		}
	}
}
