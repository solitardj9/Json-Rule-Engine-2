package jsonRuleEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.codehaus.janino.ExpressionEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

public class RIMap {
	//
	private static Logger logger = LoggerFactory.getLogger(RIMap.class);
	
	private Map<String, Object> map = new HashMap<>();
	
	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public void iterate() {
		iterate(this.map);
	}
	
	@SuppressWarnings("rawtypes")
	private void iterate(Map<String, Object> map)  {
		//
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			//
			System.out.println(entry.getClass().toString());
			
			if (entry.getValue() instanceof Map) {
				iterate((Map)entry.getValue());
	        }
			else {
				System.out.println(entry.getValue().toString());
	        }
	    }
	}
	
	public String makeExpression() {
		String ret = (String)makeExpression(this.map);
		return ret;
	}
	
	private Object makeExpression(Map<String, Object> map)  {
		//
		String ret = "";
		
		ret += "(";
		
		Object object = null;
		
		object = map.get("input");
		if (object instanceof Map) {
			ret += makeExpression((Map)object).toString();
		}
		else {
			ret += object.toString();
		}
		
		object = map.get("condition");
		ret += object.toString();
		
		object = map.get("value");
		if (object instanceof Map) {
			ret += makeExpression((Map)object).toString();
		}
		else {
			ret += object.toString();
		}
		
		return ret += ")";
	}
	
	public Boolean execute(String jsonString) {
		//
		String exp = (String)executeExpression(this.map, jsonString);
		
		logger.info("exp = " + exp);
		
		//return false;
		return evaluateExpression(exp);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object executeExpression(Map<String, Object> map, String jsonString)  {
		//
		String ret = "";
		
		ret += "(";
		
		Object object = null;
		Object tmpObject = null;
		
		object = map.get("input");
		if (object instanceof Map) {
			ret += executeExpression((Map)object, jsonString).toString();
		}
		else {
			if (((String)object).startsWith("read")) {
				tmpObject = readDataFromJson(jsonString, (String)object).toString();
				if (tmpObject instanceof String) {
					tmpObject = "\"" + tmpObject + "\"";
				}
				ret += tmpObject;
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
			ret += executeExpression((Map)object, jsonString).toString();
		}
		else {
			if (((String)object).startsWith("read")) {
				tmpObject = readDataFromJson(jsonString, (String)object).toString();
				if (tmpObject instanceof String) {
					tmpObject = "\"" + tmpObject + "\"";
				}
				ret += tmpObject; 
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
		Long startTime = System.nanoTime();
		
		String keyPath = function.replace("read", "").replace("(", "").replace(")", "");
		
		DocumentContext context = JsonPath.parse(jsonString);
		Object object = context.read(keyPath);
		
		Long endTime = System.nanoTime();
		Long diffTime = endTime - startTime;
		System.out.println("readDataFromJson : " + diffTime / 1000000 + "(ms)");
		
		return object;
	}
	
	
	private Boolean evaluateExpression(String exp) {
		//
		String regexp = "[!@#$%^&*(),?:{}|<=>]";
		
		try {
			Boolean result = null;
	
			Pattern pattern = Pattern.compile(regexp);
			String[] operands = pattern.split(exp);
			
			List<String> params = new ArrayList<>();
			for (String iter : operands) {
				if (iter != null && !iter.equals("")  && !iter.equals(" ")) {
					params.add(iter);
				}
			}
			
			// Now here's where the story begins...
			ExpressionEvaluator ee = new ExpressionEvaluator();
			
			// The expression will have two "int" parameters: "a" and "b".
			String[] arrParams = new String[params.size()];
			Class[] arrClasses = new Class[params.size()];
			Object[] arrObjects = new Object[params.size()];
			
			int count = 0;
			for (String iter : params) {
				String tmpParam = ("p" + count);
				arrParams[count] = tmpParam;
				arrClasses[count] = String.class;
				
				String value = iter.trim();
				arrObjects[count] = value;
				count++;
	        }
			
			// The expression will have two "int" parameters: "a" and "b".
			ee.setParameters(arrParams, arrClasses);
			
			// And the expression (i.e. "result") type is also "int".
			ee.setExpressionType(Boolean.class);
			
			// And now we "cook" (scan, parse, compile and load) the fabulous expression.
			//System.out.println("[DataActionWorker].evaluateTrigger :" + tmpTrigger);
			ee.cook(exp);
			
			// Eventually we evaluate the expression - and that goes super-fast.
			result = (Boolean) ee.evaluate(arrObjects);
			logger.info("[JsonRuleEngine].evaluateTrigger : result = " + result.toString());
			
			return result;
		}
		catch (Exception e) {
			logger.info("[JsonRuleEngine].evaluateTrigger : error = " + e.toString());
			return false;
		}
	}
}
