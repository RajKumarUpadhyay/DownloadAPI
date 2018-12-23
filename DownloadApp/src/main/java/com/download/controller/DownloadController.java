package com.download.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.download.Repositiory.DownloadRepository;
import com.download.delegate.DownloadDelegate;
import com.download.exception.StandardException;
import com.download.model.DownloadModel;
import com.download.validation.DownloadValidationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/download")
@Api(value="Download Controller Api")
public class DownloadController {

	final static Logger logger = LoggerFactory.getLogger(DownloadController.class);

	@Autowired
	DownloadValidationService downloadValidationService;
	@Autowired
	DownloadDelegate downloadDelegate;
	@Autowired
	DownloadRepository downloadRepository;
	@Autowired
	Environment environment;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String redirectToLoginPage(){
		
		return "redirect:/downloadResource";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public ModelAndView displayLogin(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView model = new ModelAndView("ResourceDownloder");
		return model;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value="Accept source as argument",notes="Please provide correct source for download data and use application properties for change configuration.")
	public ResponseEntity<DownloadModel> downloadDataFromSource(@RequestParam("Source") String url) throws StandardException, IOException {

		logger.info("DownloadDataFromSource Is Invoked");

		DownloadModel downloadModel = null ;
		try
		{
			if(downloadValidationService.isTargetDirectoryExist(environment) && downloadValidationService.urlValidator(url))
			{
				downloadModel =	downloadDelegate.delegateConnectorRequest(url,downloadRepository,environment);
			}
			else
			{
				logger.info("Taget directory or source is invalid. Please check before trigger service.");
				return new ResponseEntity<DownloadModel>(HttpStatus.EXPECTATION_FAILED);
			}
		}
		catch(Exception exception)
		{
			return new ResponseEntity<DownloadModel>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<DownloadModel>(downloadModel, HttpStatus.OK);
	}
}
