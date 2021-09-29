package com.shy.main;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Test1 {

	private static final Logger logger = LogManager.getLogger();

	public static void main(String[] args) {

		System.out.println("-----------------------------------------Test1类的main方法打印的日志---------------------------------------------------------------------------------------------------");
		logger.log(Level.ALL, "main-all level log");
		logger.trace("main-trace level log");
		logger.debug("main-debug level log");
		logger.info("main-info level log");
		logger.warn("main-warn level log");
		logger.error("main-error level log");
		logger.fatal("main-fatal level log");
		logger.log(Level.OFF, "main-off level log");
		
	}
}
