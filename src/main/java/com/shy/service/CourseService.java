package com.shy.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CourseService {
	
	private static final Logger logger=LogManager.getLogger();
	
	public void selectCourse() {
		System.out.println("----------------------------------------------调用CourseService的selectCourse()----------------------------------------------");
		logger.fatal("-- fatal level info");
		logger.error("-- error level info");
		logger.warn("-- warn level info");
		logger.info("-- info level info");
		logger.debug("-- debug level info");
		logger.trace("-- trace level info");
	}
}
