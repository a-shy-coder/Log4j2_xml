package com.shy.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProductService {
	
	private static final Logger logger=LogManager.getLogger();

	public void addNewProduct() {
		System.out.println("-----------------------------------------ProductService的addNewProduct方法中打印的日志------------------------------------------------------------------");
		logger.fatal("--fatal");
		logger.error("--error");
		logger.warn("--warn");
		logger.info("--info");
		logger.debug("--debug");
		logger.trace("--trace");
	}
	
}
