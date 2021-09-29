package com.shy.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StudentDao {
	
	private static final Logger logger=LogManager.getLogger();
	
	public void getOne() {
		System.out.println("-----------------------------------------StudentDao的getOne方法中打印的日志------------------------------------------------------------------");
		logger.fatal("--fatal");
		logger.error("--error");
		logger.warn("--warn");
		logger.info("--info");
		logger.debug("--debug");
		logger.trace("--trace");
	}

	public void getAll() {
		System.out.println("-----------------------------------------StudentDao的getAll方法中打印的日志------------------------------------------------------------------");
		logger.fatal("--fatal");
		logger.error("--error");
		logger.warn("--warn");
		logger.info("--info");
		logger.debug("--debug");
		logger.trace("--trace");
	}
}
