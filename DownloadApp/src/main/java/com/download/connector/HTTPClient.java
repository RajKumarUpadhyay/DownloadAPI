package com.download.connector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.download.Repositiory.DownloadRepository;
import com.download.exception.StandardException;
import com.download.model.DownloadConstants;
import com.download.model.DownloadModel;

@Service
public class HTTPClient implements DownloadConnectorService
{
	final static Logger logger = LoggerFactory.getLogger(HTTPClient.class);
	DownloadModel downloadModel;

	/**
	 * Downloads a file from a URL
	 * @param fileURL HTTP URL of the file to be downloaded
	 * @return DownloadModel
	 * @throws IOException
	 */
	@Qualifier("httpClient") 
	public DownloadModel connectHost(String fileURL, DownloadRepository downloadRepository,Environment  environment) throws IOException 
	{
		String saveDir = environment.getProperty("download.folder");
		downloadModel = new DownloadModel();
		downloadModel.setFileSource(fileURL);
		downloadModel.setFileDestination(saveDir);
		int totalBytesDownloaded=0;
		int failurePercentage=0;
		
		if(fileURL.startsWith(DownloadConstants.HTTP))
		{
			downloadModel.setProtocol(DownloadConstants.HTTP);
		}
		else
		{
			downloadModel.setProtocol(DownloadConstants.HTTPS);
		}
		
		try
		{
			URL url = new URL(fileURL);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			int responseCode = httpConn.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				String fileName = "";
				String disposition = httpConn.getHeaderField("Content-Disposition");
				String contentType = httpConn.getContentType();
				int contentLength = httpConn.getContentLength();

				if (disposition != null) 
				{

					int index = disposition.indexOf("filename=");
					if (index > 0) 
					{
						fileName = disposition.substring(index + 10,disposition.length() - 1);
					}
				} 
				else 
				{
					fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,fileURL.length());
				}

				logger.info("Content-Type = " + contentType);
				logger.info("Content-Disposition = " + disposition);
				logger.info("Content-Length = " + contentLength);
				logger.info("fileName = " + fileName);
				downloadModel.setLargeData(contentLength);
				
				if(DownloadConstants.BIG_DATA_SIZE <= contentLength)
				{
					logger.info("Data is too big");
				}
				
				InputStream inputStream = httpConn.getInputStream();
				String saveFilePath = saveDir + File.separator + fileName;

				FileOutputStream outputStream = new FileOutputStream(saveFilePath);
				
				int bytesRead = -1;
				int BUFFER_SIZE = Integer.parseInt(environment.getProperty("BUFFER_SIZE").trim());

				if(DownloadConstants.MAX_BUFFER_SIZE_SPEED >= BUFFER_SIZE)
				{
					downloadModel.setDownloadSpeed(DownloadConstants.FAST);
				}
				else
				{
					downloadModel.setDownloadSpeed(DownloadConstants.SLOW);
				}

				byte[] buffer = new byte[BUFFER_SIZE];

				downloadModel.setDownloadStartTime(new java.util.Date());
                
				while ((bytesRead = inputStream.read(buffer)) != -1) 
				{
					outputStream.write(buffer, 0, bytesRead);
					totalBytesDownloaded +=bytesRead;
				}
                
				outputStream.close();
				inputStream.close();
				
				if(totalBytesDownloaded != contentLength)
				{
					failurePercentage = totalBytesDownloaded *100/contentLength;
					downloadModel.setPercentageOfFailure(failurePercentage);
					throw new StandardException("Download Failed. Please check log for more detail.");
				}
				else
				{
					failurePercentage =0;
				}
				
				downloadModel.setDownloadEndTime(new java.util.Date());
				logger.info("File downloaded");

				downloadRepository.save(downloadModel);
			} 
			else 
			{
				logger.info("No file to download. Server replied HTTP code: " + responseCode);
				throw new StandardException("Response Code Is:"+responseCode);
			}
			httpConn.disconnect();
		}
		catch(StandardException exception)
		{
			throw new StandardException(exception.getMessage()+"::"+exception.getCause());
		}
		return downloadModel;
	}
}
