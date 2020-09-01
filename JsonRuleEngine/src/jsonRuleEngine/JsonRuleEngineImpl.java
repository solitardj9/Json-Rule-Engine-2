package jsonRuleEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

import jsonRuleEngine.data.JsonRuleEngineConfig;
import jsonRuleEngine.data.JsonRuleEngineConfigs;
//import jsonRuleEngine.rule.RuleUtil;
//import jsonRuleEngine.rule.Rules;
//import jsonRuleEngine.rule.Rules.RULE;

public class JsonRuleEngineImpl implements JsonRuleEngine {
	//
	private static Logger logger = LoggerFactory.getLogger(JsonRuleEngineImpl.class);
	
	private JsonRuleEngineConfigs jsonRuleEngineConfigs;
	
	private static ObjectMapper om = new ObjectMapper();
	
	public JsonRuleEngineImpl() {
		//
	}
	
	@Override
	public void insertConfigs(String jsonRuleEngineConfigs) {
		//
		try {
			this.jsonRuleEngineConfigs = om.readValue(jsonRuleEngineConfigs, JsonRuleEngineConfigs.class);
		} catch (JsonProcessingException e) {
			logger.error(e.toString());
		}
	}

	@Override
	public void insertConfigs(JsonRuleEngineConfigs jsonRuleEngineConfigs) {
		//
		this.jsonRuleEngineConfigs = new JsonRuleEngineConfigs(jsonRuleEngineConfigs);
	}

	@Override
	public JsonRuleEngineConfigs getConfigs() {
		//
		JsonRuleEngineConfigs retConfigs = new JsonRuleEngineConfigs(jsonRuleEngineConfigs);
		return retConfigs;
	}

	@Override
	public List<String> execute(String jsonString) {
		//
		List<String> retList = new ArrayList<>();
		
		for (JsonRuleEngineConfig iter : this.jsonRuleEngineConfigs.getConfigs()) {
			//
        	Boolean ret = isTriggered(iter.getTrigger(), jsonString);
        	if (ret != null && ret.equals(true)) {
        		//
//        		String result = makeResult(iter.getResult(), jsonString);
//        		retList.add(result);
	        }
	    }
		
		return retList;
	}
	
	@SuppressWarnings("unchecked")
	private Boolean isTriggered(Object trigger, String jsonString) {
		
		if (jsonString == null || jsonString.isEmpty()) {
			logger.info("[JsonRuleEngine].isTriggered : jsonString is null");
			return false;
		}
		
		logger.info("[JsonRuleEngine].isTriggered : tmpTrigger = " + trigger);
		try {
			Map<String, Object> triggerExpMap = om.readValue(om.writeValueAsString(trigger), Map.class);
			String triggerExp = (String) makeExpression(triggerExpMap, jsonString);
			
			logger.info("[JsonRuleEngine].isTriggered : triggerExp = " + triggerExp);
			
		} catch (JsonProcessingException e) {
			logger.info("[JsonRuleEngine].isTriggered : error = " + e.toString());
		}
		
		// 2) get Rules
//		List<String> ruleTypes = Rules.getRuleTypes();
//		
//		logger.info("[JsonRuleEngine].isTriggered : ruleTypes = " + ruleTypes.toString());
//		
//		for (String ruleType : ruleTypes) {
//			//
//			RULE rule = Rules.getRuleByType(ruleType);
//			
//			tmpTrigger = rule.replaceRuleExpressionWithData(tmpTrigger, data);
//		}
//		
//		if (tmpTrigger != null)
//			return evaluateTrigger(tmpTrigger);
//		else
			return false;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object makeExpression(Map<String, Object> map, String jsonString)  {
		//
		String ret = "";
		
		ret += "(";
		
		Object object = null;
		Object tmpObject = null;
		
		object = map.get("input");
		if (object instanceof Map) {
			ret += makeExpression((Map)object, jsonString).toString();
		}
		else {
			if ((object instanceof String) && ((String)object).startsWith("read")) {
				tmpObject = readDataFromJson(jsonString, (String)object);
				if (tmpObject != null) {
					if (tmpObject instanceof String) {
						tmpObject = "\"" + tmpObject + "\"";
					}
					ret += tmpObject;
				}
			}
			else {
				tmpObject = object;
				if (tmpObject instanceof String) {
					tmpObject = "\"" + tmpObject + "\"";
				}
				ret += tmpObject;
			}
		}
		
		object = map.get("condition");
		ret += object.toString();
		
		object = map.get("value");
		if (object instanceof Map) {
			ret += makeExpression((Map)object, jsonString).toString();
		}
		else {
			if ((object instanceof String) && ((String)object).startsWith("read")) {
				tmpObject = readDataFromJson(jsonString, (String)object);
				if (tmpObject != null) {
					if (tmpObject instanceof String) {
						tmpObject = "\"" + tmpObject + "\"";
					}
					ret += tmpObject;
				} 
			}
			else {
				tmpObject = object;
				if (tmpObject instanceof String) {
					tmpObject = "\"" + tmpObject + "\"";
				}
				ret += tmpObject;
			}
		}
		
		return ret += ")";
	}
	
	private Object readDataFromJson(String jsonString, String function) {
		//
		try {
			String keyPath = function.replace("read", "").replace("(", "").replace(")", "");
			
			DocumentContext context = JsonPath.parse(jsonString);
			Object object = context.read(keyPath);
			
			return object;
		} catch (PathNotFoundException e) {
			return null;
		}
	}
	
	
	
	
	
	
//    private Boolean evaluateTrigger(String trigger) {
//    	//
//    	String regexp = "[!@#$%^&*(),?:{}|<=>]";
//    	
//    	try {
//    		Boolean result = null;
//
//    		Pattern pattern = Pattern.compile(regexp);
//    		String[] operands = pattern.split(trigger);
//    		
//    		List<String> params = new ArrayList<>();
//    		for (String iter : operands) {
//    			if (iter != null && !iter.equals("")  && !iter.equals(" ")) {
//    				params.add(iter);
//    			}
//    		}
//    		
//    		// Now here's where the story begins...
//    		ExpressionEvaluator ee = new ExpressionEvaluator();
//    		
//    		// The expression will have two "int" parameters: "a" and "b".
//    		String[] arrParams = new String[params.size()];
//    		Class[] arrClasses = new Class[params.size()];
//    		Object[] arrObjects = new Object[params.size()];
//    		
//    		int count = 0;
//    		for (String iter : params) {
//    			String tmpParam = ("p" + count);
//    			arrParams[count] = tmpParam;
//    			arrClasses[count] = String.class;
//    			
//    			String value = iter.trim();
//    			arrObjects[count] = value;
//    			count++;
//            }
//    		
//    		// The expression will have two "int" parameters: "a" and "b".
//    		ee.setParameters(arrParams, arrClasses);
//    		
//    		// And the expression (i.e. "result") type is also "int".
//    		ee.setExpressionType(Boolean.class);
//    		
//    		// And now we "cook" (scan, parse, compile and load) the fabulous expression.
//    		//System.out.println("[DataActionWorker].evaluateTrigger :" + tmpTrigger);
//    		ee.cook(trigger);
//    		
//    		// Eventually we evaluate the expression - and that goes super-fast.
//    		result = (Boolean) ee.evaluate(arrObjects);
//    		logger.info("[JsonRuleEngine].evaluateTrigger : result = " + result.toString());
//    		
//    		return result;
//    	}
//    	catch (Exception e) {
//    		logger.info("[JsonRuleEngine].evaluateTrigger : error = " + e.toString());
//    		return false;
//    	}
//    }
//	
//	private String makeResult(String result, String data) {
//		//
//		String tmpResult = RuleUtil.replaceSingleQuotesToDoubleQuotes(new String(result));
//		
//		logger.info("[JsonRuleEngine].makeResult : tmpResult = " + tmpResult);
//		
//		// 2) get Rules
//		List<String> ruleTypes = Rules.getRuleTypes();
//		
//		logger.info("[JsonRuleEngine].makeResult : ruleTypes = " + ruleTypes.toString());
//		
//		for (String ruleType : ruleTypes) {
//			//
//			RULE rule = Rules.getRuleByType(ruleType);
//			
//			tmpResult = rule.replaceRuleExpressionWithData(tmpResult, data);
//		}
//		
//		return tmpResult;
//	}
}