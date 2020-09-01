package com.example.demo.dataActionMngt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.janino.ExpressionEvaluator;
import org.springframework.context.ApplicationContext;

import com.example.demo.dataActionMngt.model.DataActionConfig;


public class DataActionWorker implements Runnable {
	//
	private DataActionManager dataActionManager;
	
	private BlockingQueue<String> queue;
	
	private ApplicationContext applicationContext;
	
	private final Long LEASE_TIME = 1000L;

	public DataActionWorker(DataActionManager dataActionManager, BlockingQueue<String> queue, ApplicationContext applicationContext) {
        this.dataActionManager = dataActionManager;
        this.queue = queue;
        this.applicationContext = applicationContext;
    }

    public void run() {
		//
	    String parsingDataObj = null;
        
        while(true) {
            //
            try{
                parsingDataObj = (String)queue.poll(LEASE_TIME, TimeUnit.MILLISECONDS);
                
                if (parsingDataObj != null) {
                    checkAndDoAction(parsingDataObj);
                }
            }
            catch (InterruptedException e) {
                //System.out.println("[DataActionWorker].run : " + e.toString());
                break;
            }
            catch (Exception e) {
                //System.out.println("[DataActionWorker].run : " + e.toString());
            }
        }
	}
    
    private void checkAndDoAction(String parsingDataObj) {
        //
//        for (DataActionConfig iter : dataActionManager.getDataActionConfigs()) {
//            if (isTriggered(iter.getTrigger(), parsingDataObj)) {
//                //
//                Object result = getResult(iter.getResult(), parsingDataObj);
//            }
//        }
    }

    private Boolean isTriggered(String trigger, String data) {
        return checkTrigger(trigger, data);
    }
    
    private Boolean checkTrigger(String trigger, String data) {
        //
        String replacedTrigger = new String(trigger);
        List<String> functions = Functions.getFunctions();
        
        for (String iter : functions) {
            //
            Pattern p = Pattern.compile(Functions.getFunctionRegExp(iter));
            Matcher m = p.matcher(trigger);
            
            while(m.find()) {
                String matchedFunction = m.group();
                replacedTrigger = Functions.replaceFunction(iter, replacedTrigger, matchedFunction, data);
            }
        }
        
        if(replacedTrigger != null)
            return evaluateTrigger(replacedTrigger);
        else
            return false;
    }
    
    private Boolean evaluateTrigger(String trigger) {
        //
        String regexp = "[!@#$%^&*(),?:{}|<=>]";
        
        try {
            Boolean result = null;
            
            String tmpTrigger = trigger.replace("\"", "");
            
            Pattern pattern = Pattern.compile(regexp);
            String[] operands = pattern.split(tmpTrigger);
            
            List<String> params = new ArrayList<>();
            for (String iter : operands) {
                if (iter != null && !iter.equals("")  && !iter.equals(" ")) {
                    params.add(iter);
                }
            }
            
            // Now here's where the story begins...
            ExpressionEvaluator ee = new ExpressionEvaluator();
            
            // The expression will have two "int" parameters: "a" and "b".
            Map<String, String> strMap = new HashMap<>();
            String[] arrParams = new String[params.size()];
            Class[] arrClasses = new Class[params.size()];
            Object[] arrObjects = new Object[params.size()];
            
            int count = 0;
            for (String iter : params) {
                String tmpParam = ("p" + count);
                arrParams[count] = tmpParam;
                arrClasses[count] = String.class;
                
                String value = iter.trim();
                if (!isNumeric(value)) {
                    String strValue = "\"" + value + "\"";
                    strMap.put(value, strValue);
                }
                
                arrObjects[count] = value;
                count++;
            }
            
            for (Entry<String, String> entry :strMap.entrySet()) {
                tmpTrigger = tmpTrigger.replace(entry.getKey(), entry.getValue());
            }
            // The expression will have two "int" parameters: "a" and "b".
            ee.setParameters(arrParams, arrClasses);
            
            // And the expression (i.e. "result") type is also "int".
            ee.setExpressionType(Boolean.class);
            
            // And now we "cook" (scan, parse, compile and load) the fabulous expression.
            //System.out.println("[DataActionWorker].evaluateTrigger :" + tmpTrigger);
            ee.cook(tmpTrigger);
            
            // Eventually we evaluate the expression - and that goes super-fast.
            result = (Boolean) ee.evaluate(arrObjects);
            return result;
        }
        catch (Exception e) {
            System.out.println("[DataActionWorker].evaluateTrigger : " + e.toString());
            return null;
        }
    }
    
    public static boolean isNumeric(String string) {
        //
        if(string.equals("")){
            return false;
        }
        return string.matches("-?\\d+(\\.\\d+)?");
    }
    
    private Object getResult(String function, String data) {
        //
        Object ret = null;
        List<String> functions = Functions.getFunctions();
        
        for (String iter : functions) {
            //
            Pattern p = Pattern.compile(Functions.getFunctionRegExp(iter));
            Matcher m = p.matcher(function);
            
            while(m.find()) {
                String matchedFunction = m.group();
                ret = Functions.doFunction(iter, matchedFunction, data);
            }
        }
        
        return ret;
    }
	
	public void setInterrupt() {
		// 
		Thread.currentThread().interrupt();
	}
}