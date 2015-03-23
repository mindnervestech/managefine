package com.mnt.time.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class scheduler1 {
	
	
	public static void main(String[] args) throws InterruptedException {
		
		        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("job-report.xml");
	
		         
	/*
		        Thread.sleep(30000);
	
		        context.close();*/
	
		    }

	
	
	
	
	/*public static void main(String[] args) {

		String springConfig = "../src/main/resources/job-report.xml";

		ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);

	}*/
}
