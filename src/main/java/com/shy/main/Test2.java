package com.shy.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.shy.dao.StudentDao;
import com.shy.service.ProductService;
import com.shy.service.UserService;

public class Test2 {

	private static final Logger logger = LogManager.getLogger(Test2.class);
	
	public static void main(String[] args) {

		System.out.println(
				"-----------------------------------------Test2中打印的日志---------------------------------------------------------------------------------------------------");
		// Test2中打印的日志
		logger.fatal("--fatal");
		logger.error("--error");
		logger.warn("--warn");
		logger.info("--info");
		logger.debug("--debug");
		logger.trace("--trace");

		// 调用StudentDao的getOne()方法，方法中打了一些日志信息
		StudentDao dao = new StudentDao();
		dao.getOne();
		
		// 调用StudentDao的getAll()方法，方法中打了一些日志信息
		dao.getAll();

		// 调用UserService中的login()方法，方法中打了一些日志信息
		UserService userService = new UserService();
		userService.login();

		// 调用ProductService中的insert()方法，方法中打了一些日志信息
		ProductService productService = new ProductService();
		productService.addNewProduct();
	}
}
