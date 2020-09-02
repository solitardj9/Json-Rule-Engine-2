package com.example.demo.dataActionMngtSet.dataActionMngt.service;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.example.demo.dataActionMngtSet.dataActionMngt.service.util.jsonRuleEngine.JsonRuleEngine;


public class DataActionWorker implements Runnable {
	//
	private static final Logger logger = LoggerFactory.getLogger(DataActionWorker.class);
	
	private DataActionManager dataActionManager;
	
	private BlockingQueue<String> queue;
	
	private ApplicationContext applicationContext;
	
	private final Long LEASE_TIME = 1000L;
	
	private JsonRuleEngine jsonRuleEngine;

	public DataActionWorker(DataActionManager dataActionManager, BlockingQueue<String> queue, JsonRuleEngine jsonRuleEngine, ApplicationContext applicationContext) {
		//
        this.dataActionManager = dataActionManager;
        this.queue = queue;
        this.jsonRuleEngine = jsonRuleEngine;
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
                logger.error("[DataActionWorker].run : " + e.toString());
                break;
            }
            catch (Exception e) {
            	logger.error("[DataActionWorker].run : " + e.toString());
            }
        }
	}
    
    private void checkAndDoAction(String parsingDataObj) {
        //
    	if (jsonRuleEngine != null) {
    		List<Object> results = jsonRuleEngine.execute(parsingDataObj);
    		
//    		for (Object iter : results)
//    			logger.info(iter.toString());
    	}
    }

	public void setInterrupt() {
		// 
		Thread.currentThread().interrupt();
	}
}