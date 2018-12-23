package com.download.delegate;

import java.io.IOException;

import org.springframework.core.env.Environment;

import com.download.Repositiory.DownloadRepository;
import com.download.exception.StandardException;
import com.download.model.DownloadModel;

public interface DownloadDelegate {
	
	public DownloadModel delegateConnectorRequest(String url, DownloadRepository downloadRepository,Environment environment) throws StandardException, IOException;

}
