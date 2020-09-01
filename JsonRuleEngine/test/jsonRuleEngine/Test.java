package jsonRuleEngine;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jsonRuleEngine.data.JsonRuleEngineConfigs;

/**
 * reference :
 * 		1) https://github.com/json-path/JsonPath
 * 		2) https://advenoh.tistory.com/28
 * @author HoYoung.Rhee
 *
 */
public class Test {
	
	private static Logger logger = LoggerFactory.getLogger(Test.class);
	
	public static void main(String[] args) {
		//
		ObjectMapper om = new ObjectMapper();
		
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
				"			\"input\" : \"read($[0])\",\r\n" + 
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
				"			\"result\" : \"read($)\"\r\n" + 
				"		},\r\n" +
				"		{\r\n" + 
				"			\"trigger\" : " + trigger1 + ",\r\n" + 
				"			\"result\" : \"read($.lastName)\"\r\n" + 
				"		},\r\n" +
				"		{\r\n" + 
				"			\"trigger\" : " + trigger1 + ",\r\n" + 
				"			\"result\" : \"ok\"\r\n" + 
				"		},\r\n" +
				"		{\r\n" + 
				"			\"trigger\" : " + trigger2 + ",\r\n" +
				"			\"result\" : {\"type\":\"alarm\", \"info1\":\"read($.address.city)\", \"info2\":\"read($.address.postalCode)\", \"info3\":\"read($.age)\"}\r\n" +
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
			
			List<Object> jsonRetStringList = null;
			
			for (int i = 0 ; i < 10 ; i++) {
				Long startTime = System.nanoTime();
				
				jsonRetStringList = jsonRuleEngine.execute(sampleJsonString1);
				System.out.println(jsonRetStringList.toString());
				
				jsonRetStringList = jsonRuleEngine.execute(sampleJsonString2);
				System.out.println(jsonRetStringList.toString());
				
				jsonRetStringList = jsonRuleEngine.execute(sampleJsonString3);
				System.out.println(jsonRetStringList.toString());
				
				Long endTime = System.nanoTime();
				Long diffTime = endTime - startTime;
				System.out.println("total time = " + diffTime + "(ns)");
				System.out.println("total time = " + diffTime / 1000000 + "(ms)");
				System.out.println("total time(avg) = " + diffTime / 1000000 / 3 + "(ms)");
			}
			
		} catch (JsonProcessingException e) {
			logger.info(e.toString());
		}

// Configuration Sample #1
//		{
//			"input" : {
//				"input":"read(path)",
//				"condition":"==",
//				"value":"10"
//			},
//			"condition" : "==",
//			"value" : "10"
//		}
// Configuration Sample #2
//		{
//			"input" : {
//				"input":"read(path)",
//				"condition":"==",
//				"value":"10"
//			},
//			"condition" : "==",
//			"value" : {
//				"input":"read(path)",
//				"condition":"==",
//				"value":"10"
//			}
//		}
	}
}