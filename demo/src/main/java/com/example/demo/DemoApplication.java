package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.demo.dataActionMngtSet.dataActionMngt.service.DataActionManager;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		//
		ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
		
		String status = "{\r\n" + 
				"			\"siteId\": \"1234\",\r\n" + 
				"			\"eqpId\": \"5678\",\r\n" + 
				"			\"eqpType\": \"ESS\",\r\n" + 
				"			\"data\": {\r\n" + 
				"				\"onoff\": \"on\",\r\n" + 
				"				\"setTemp\": 30,\r\n" + 
				"				\"roomTemp\": 24,\r\n" + 
				"				\"errorCode\": 10\r\n" + 
				"			}\r\n" + 
				"		}";
		
		DataActionManager dataActionManager = ((DataActionManager)context.getBean("dataActionManager"));
		
		for (int i = 0 ; i < 3 ; i++)
			dataActionManager.checkAndDoAction(status);
	}
}