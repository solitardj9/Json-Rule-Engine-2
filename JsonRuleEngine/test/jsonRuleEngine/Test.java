package jsonRuleEngine;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jsonRuleEngine.data.JsonRuleEngineConfigs;

public class Test {
	
	private static Logger logger = LoggerFactory.getLogger(Test.class);
	
	public static void main(String[] args) {
		//
		ObjectMapper om = new ObjectMapper();
		
		RIMap riMap = new RIMap();
		
		String sampleJsonString = "{\r\n" + 
				"    \"store\": {\r\n" + 
				"        \"book\": [\r\n" + 
				"            {\r\n" + 
				"                \"category\": \"reference\",\r\n" + 
				"                \"author\": \"Nigel Rees\",\r\n" + 
				"                \"title\": \"Sayings of the Century\",\r\n" + 
				"                \"price\": 8.95\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"                \"category\": \"fiction\",\r\n" + 
				"                \"author\": \"Evelyn Waugh\",\r\n" + 
				"                \"title\": \"Sword of Honour\",\r\n" + 
				"                \"price\": 12.99\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"                \"category\": \"fiction\",\r\n" + 
				"                \"author\": \"Herman Melville\",\r\n" + 
				"                \"title\": \"Moby Dick\",\r\n" + 
				"                \"isbn\": \"0-553-21311-3\",\r\n" + 
				"                \"price\": 8.99\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"                \"category\": \"fiction\",\r\n" + 
				"                \"author\": \"J. R. R. Tolkien\",\r\n" + 
				"                \"title\": \"The Lord of the Rings\",\r\n" + 
				"                \"isbn\": \"0-395-19395-8\",\r\n" + 
				"                \"price\": 22.99\r\n" + 
				"            }\r\n" + 
				"        ],\r\n" + 
				"        \"bicycle\": {\r\n" + 
				"            \"color\": \"red\",\r\n" + 
				"            \"price\": 19.95\r\n" + 
				"        }\r\n" + 
				"    },\r\n" + 
				"    \"expensive\": 10\r\n" + 
				"}";
		
		String sampleJsonString1 = "{\r\n" + 
				"	\"firstName\" : \"John\",\r\n" + 
				"	\"lastName\" : \"Smith\",\r\n" + 
				"	\"age\" : 20,\r\n" + 
				"	\"address\" : {\r\n" + 
				"		\"streetAddress\" : \"21 2nd Street\",\r\n" + 
				"		\"city\" : \"New York\",\r\n" + 
				"		\"state\" : \"NY\",\r\n" + 
				"		\"postalCode\" : \"10021\"\r\n" + 
				"	},\r\n" + 
				"	\"phoneNumber\" : [\r\n" + 
				"		{\r\n" + 
				"			\"type\" : \"home\",\r\n" + 
				"			\"number\" : \"212 555-1234\"\r\n" + 
				"		},\r\n" + 
				"		{\r\n" + 
				"			\"type\" : \"fax\",\r\n" + 
				"			\"number\" : \"646 555-4567\"\r\n" + 
				"		}\r\n" + 
				"	]\r\n" + 
				"}";
		
		String sampleJsonString2 = "{\r\n" + 
				"	\"firstName\" : \"John\",\r\n" + 
				"	\"lastName\" : \"Smith\",\r\n" + 
				"	\"age\" : 30,\r\n" + 
				"	\"address\" : {\r\n" + 
				"		\"streetAddress\" : \"21 2nd Street\",\r\n" + 
				"		\"city\" : \"Illinois\",\r\n" + 
				"		\"state\" : \"NY\",\r\n" + 
				"		\"postalCode\" : \"10021\"\r\n" + 
				"	},\r\n" + 
				"	\"phoneNumber\" : [\r\n" + 
				"		{\r\n" + 
				"			\"type\" : \"home\",\r\n" + 
				"			\"number\" : \"212 555-1234\"\r\n" + 
				"		},\r\n" + 
				"		{\r\n" + 
				"			\"type\" : \"fax\",\r\n" + 
				"			\"number\" : \"646 555-4567\"\r\n" + 
				"		}\r\n" + 
				"	]\r\n" + 
				"}";
		
		String sampleJsonString3 = "[1,2,3,4]";
		
		String trigger1 = "{\r\n" + 
				"			\"input\" : {\r\n" + 
				"				\"input\":\"read($.address.city)\",\r\n" + 
				"				\"condition\":\"==\",\r\n" + 
				"				\"value\":\"New York\"\r\n" + 
				"			},\r\n" + 
				"			\"condition\" : \"&&\",\r\n" + 
				"			\"value\" : {\r\n" + 
				"				\"input\":\"read($.age)\",\r\n" + 
				"				\"condition\":\"==\",\r\n" + 
				"				\"value\":20\r\n" + 
				"			}\r\n" + 
				"		}";
		
		String trigger2 = "{\r\n" + 
				"			\"input\" : {\r\n" + 
				"				\"input\":\"read($.phoneNumber[0].type)\",\r\n" + 
				"				\"condition\":\"==\",\r\n" + 
				"				\"value\":\"home\"\r\n" + 
				"			},\r\n" + 
				"			\"condition\" : \"&&\",\r\n" + 
				"			\"value\" : {\r\n" + 
				"				\"input\":\"read($.address.city)\",\r\n" + 
				"				\"condition\":\"==\",\r\n" + 
				"				\"value\":\"Illinois\"\r\n" + 
				"			}\r\n" + 
				"		}";
		
		String trigger3 = "{\r\n" + 
				"			\"input\" : \"read($.[0])\",\r\n" + 
				"			\"condition\" : \"==\",\r\n" + 
				"			\"value\" : 1\r\n" + 
				"		}";
		
		String trigger4 = "{\r\n" + 
				"			\"input\" : {\r\n" + 
				"				\"input\":\"read($.address.city)\",\r\n" + 
				"				\"condition\":\"==\",\r\n" + 
				"				\"value\":\"Illinois\"\r\n" + 
				"			},\r\n" + 
				"			\"condition\" : \"&&\",\r\n" + 
				"			\"value\" : {\r\n" + 
				"				\"input\":\"read($.age)\",\r\n" + 
				"				\"condition\":\"==\",\r\n" + 
				"				\"value\":30\r\n" + 
				"			}\r\n" + 
				"		}";
		
		String strJsonRuleEngineConfigs = "{\r\n" + 
				"	\"configs\" : [\r\n" + 
				"		{\r\n" + 
				"			\"trigger\" : " + trigger1 + ",\r\n" + 
				"			\"result\" : \"read($..*)\"\r\n" + 
				"		},\r\n" +
				"		{\r\n" + 
				"			\"trigger\" : " + trigger2 + ",\r\n" +
				"			\"result\" : \"{'type':'alarm', 'info':read($.address.city)}\"\r\n" + 
				"		},\r\n" +
				"		{\r\n" + 
				"			\"trigger\" : " + trigger3 +",\r\n" +
				"			\"result\" : \"read($.[2])\"\r\n" + 
				"		},\r\n" +
				"		{\r\n" + 
				"			\"trigger\" : " + trigger4 + ",\r\n" + 
				"			\"result\" : \"read($.phoneNumber)\"\r\n" +
				"		}\r\n" + 
				"	]\r\n" + 
				"}";
		
		System.out.println("strJsonRuleEngineConfigs = " + strJsonRuleEngineConfigs);

		try {
			JsonRuleEngineConfigs jsonRuleEngineConfigs = om.readValue(strJsonRuleEngineConfigs, JsonRuleEngineConfigs.class);
			System.out.println("jsonRuleEngineConfigs = " + jsonRuleEngineConfigs.toString());
			
			JsonRuleEngine jsonRuleEngine = new JsonRuleEngineImpl();
			jsonRuleEngine.insertConfigs(jsonRuleEngineConfigs);
			System.out.println(jsonRuleEngine.getConfigs().toString());
			
		} catch (JsonProcessingException e) {
			logger.info(e.toString());
		}
		
		
		
		
//		try {
//			Map<String, Object> map = om.readValue(jsonRuleEngineConfigs, Map.class);
//			System.out.println(map.toString());
//			
//			riMap.setMap(map);
//			
//			System.out.println("//---------------------------------------------");
//			//riMap.iterate();
//			
//			System.out.println("//---------------------------------------------");
//			//System.out.println(riMap.makeExpression());
//			
//			System.out.println("//---------------------------------------------");
//			for (int i = 0 ; i < 10 ; i++) {
//				Long startTime = System.nanoTime();
//				
//				System.out.println(riMap.execute(sampleJsonString));
//				
//				Long endTime = System.nanoTime();
//				Long diffTime = endTime - startTime;
//				System.out.println("total : " + diffTime + "(ns)");
//				System.out.println("total : " + diffTime / 1000000 + "(ms)");
//			}
//			
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
		
		
//		{
//			"input" : {
//				"input":"payload(path)",
//				"condition":"==",
//				"value":"10"
//			},
//			"condition" : "==",
//			"value" : "10"
//		}
		
//		{
//			"input" : {
//				"input":"payload(path)",
//				"condition":"==",
//				"value":"10"
//			},
//			"condition" : "==",
//			"value" : {
//				"input":"payload(path)",
//				"condition":"==",
//				"value":"10"
//			}
//		}
		
		
		
		
		
		
		
		
		
		

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
			

//		
//		List<String> jsonRetStringList = null;
//		
//		Long startTime = System.nanoTime();
//		
//		jsonRetStringList = jsonRuleEngine.execute(sampleJsonString1);
//		System.out.println(jsonRetStringList.toString());
//		
//		jsonRetStringList = jsonRuleEngine.execute(sampleJsonString2);
//		System.out.println(jsonRetStringList.toString());
//		
//		jsonRetStringList = jsonRuleEngine.execute(sampleJsonString3);
//		System.out.println(jsonRetStringList.toString());
//		
//		Long endTime = System.nanoTime();
//		Long diffTime = endTime - startTime;
//		System.out.println(diffTime + "(ns)");
//		System.out.println(diffTime / 1000000 + "(ms)");
	}
}