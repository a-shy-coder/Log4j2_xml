package com.shy.main;

import com.shy.service.CourseService;

public class Test3 {

	public static void main(String[] args) throws InterruptedException {

		// 调用CourseService中的selectCourse()方法，方法中打了一些日志信息
		CourseService courseService = new CourseService();
		for (int i = 0; i < 150; i++) {
			Thread.sleep(200);
			courseService.selectCourse();
		}

	}
}
