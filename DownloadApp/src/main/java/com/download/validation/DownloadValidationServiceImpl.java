package com.download.validation;

import java.io.File;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.download.exception.StandardException;
import com.download.model.DownloadConstants;

@Service
public class DownloadValidationServiceImpl implements DownloadValidationService{

	@Autowired
	Environment environment;

	private static final Pattern URL_PATTERN = Pattern.compile(DownloadConstants.URL_REGEX);

	@Override
	public boolean isTargetDirectoryExist(Environment environment) throws StandardException {
		String tagetDirectory = environment.getProperty("download.folder");
		boolean flag = false;
		try {
			if(Objects.nonNull(tagetDirectory))
			{
				File tagetDir = new File(tagetDirectory);

				if(tagetDir.exists())
				{
					flag = true;
				}
				else
				{
					flag = false;
					throw new StandardException("Target directory doesn't exist: "+tagetDirectory, 0);
				}
			}
		} 
		catch (StandardException ex)
		{
			flag = false;
		}
		return flag;
	}


	public boolean urlValidator(String url) {

		if (url == null) {
			return false;
		}
		
		Matcher matcher = URL_PATTERN.matcher(url);
		return matcher.matches();
	}
}
