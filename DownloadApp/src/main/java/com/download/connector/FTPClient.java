package com.download.connector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
public class FTPClient implements DownloadConnectorService
{
	final static Logger logger = LoggerFactory.getLogger(FTPClient.class);
	DownloadModel downloadModel;

	/**
	 * Downloads a file from a URL
	 * @param hostType ftp/sftp URL of the file to be downloaded
	 * @return DownloadModel
	 * @throws IOException
	 */
	@Qualifier("ftpClient") 
	@Override
	public DownloadModel connectHost(String hostType, DownloadRepository downloadRepository,
			Environment environment) throws StandardException, IOException {

		downloadModel = new DownloadModel();
		downloadModel.setFileSource(hostType);

		String server = hostType;
		int port = Integer.parseInt(environment.getProperty("FTP_PORT").trim());
		String user = environment.getProperty("FTP_USER").trim();
		String pass = environment.getProperty("FTP_PASS").trim();
		int BUFFER_SIZE = Integer.parseInt(environment.getProperty("BUFFER_SIZE").trim());

		if(hostType.startsWith(DownloadConstants.FTP))
		{
			downloadModel.setProtocol(DownloadConstants.FTP);
		}
		else
		{
			downloadModel.setProtocol(DownloadConstants.SFTP);
		}

		org.apache.commons.net.ftp.FTPClient ftpClient = new org.apache.commons.net.ftp.FTPClient();
		try 
		{
			String saveDir = environment.getProperty("download.folder");
			downloadModel.setFileDestination(saveDir);
			ftpClient.connect(server, port);
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(org.apache.commons.net.ftp.FTPClient.BINARY_FILE_TYPE);

			String fileName =  server.substring(server.lastIndexOf("/") + 1,server.length());
			String saveFilePath = saveDir + File.separator + fileName;

			FileOutputStream outputStream = new FileOutputStream(saveFilePath);

			InputStream inputStream = ftpClient.retrieveFileStream(fileName);
			if(DownloadConstants.MAX_BUFFER_SIZE_SPEED >= BUFFER_SIZE)
			{
				downloadModel.setDownloadSpeed(DownloadConstants.FAST);
			}
			else
			{
				downloadModel.setDownloadSpeed(DownloadConstants.SLOW);
			}

			byte[] bytesArray = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			downloadModel.setDownloadStartTime(new java.util.Date());

			while ((bytesRead = inputStream.read(bytesArray)) != -1) 
			{
				outputStream.write(bytesArray, 0, bytesRead);
			}

			ftpClient.completePendingCommand();
			downloadModel.setDownloadEndTime(new java.util.Date());
			logger.info("File downloaded");
			outputStream.close();
			inputStream.close();
			downloadRepository.save(downloadModel);
		} 
		catch (IOException exception) 
		{
			throw new StandardException(exception.getMessage()+"::"+exception.getCause());
		} 
		finally 
		{
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} 
			catch (IOException ex)
			{
				throw new StandardException(ex.getMessage()+"::"+ex.getCause());
			}
		}
		return downloadModel;
	}
}
