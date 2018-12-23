package com.download.validation;

import org.springframework.core.env.Environment;

import com.download.exception.StandardException;

public interface DownloadValidationService {
	
	boolean urlValidator(String str) throws StandardException;
	boolean isTargetDirectoryExist(Environment environment) throws StandardException;

}
