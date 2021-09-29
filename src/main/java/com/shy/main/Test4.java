package com.shy.main;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Test4 {

	private static final Logger logger=LogManager.getLogger();
	
	public static void main(String[] args) {
		
		Scanner sc=new Scanner(System.in);
		System.out.print("输入被除数：");
		int a=sc.nextInt();
		System.out.print("输入除数：");
		int b=sc.nextInt();

		try {
			System.out.println("计算结果："+a/b);
		}catch(Exception e) {
			
		}

	}
}
