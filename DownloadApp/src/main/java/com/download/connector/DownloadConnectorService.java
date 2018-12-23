package com.download.connector;

import java.io.IOException;

import org.springframework.core.env.Environment;

import com.download.Repositiory.DownloadRepository;
import com.download.exception.StandardException;
import com.download.model.DownloadModel;

public interface DownloadConnectorService {
	public DownloadModel connectHost(String hostType, DownloadRepository downloadRepository, Environment environment) throws StandardException, IOException;
}
