package com.example.demo.dataActionMngtSet.dataActionMngt.service.impl;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.example.demo.dataActionMngtSet.dataActionMngt.service.DataActionManager;
import com.example.demo.dataActionMngtSet.dataActionMngt.service.util.jsonRuleEngine.JsonRuleEngine;
import com.example.demo.dataActionMngtSet.dataActionMngt.service.util.jsonRuleEngine.data.JsonRuleEngineResultSet;
import com.example.demo.dataActionMngtSet.dataActionPluginMngt.model.DataActionPluginTypeEnum;
import com.example.demo.dataActionMngtSet.dataActionPluginMngt.service.DataActionPluginManager;


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
    		
    		Long startTime = System.nanoTime();
    		
    		List<JsonRuleEngineResultSet> results = jsonRuleEngine.execute(parsingDataObj);
    		
    		for (JsonRuleEngineResultSet iter : results) {
    			//
    			logger.info(iter.toString());
    			
    			getDataActionPluginManager((String)iter.getEvent()).doActon(iter.getResult());
    		}
    		
    		Long endTime = System.nanoTime();
			Long diffTime = endTime - startTime;
			System.out.println("total time = " + diffTime + "(ns)");
			System.out.println("total time = " + diffTime / 1000000 + "(ms)");
    	}
    }
    
    private DataActionPluginManager getDataActionPluginManager(String actionPluginType) {
        //
        return (DataActionPluginManager)applicationContext.getBean(DataActionPluginTypeEnum.getServiceName(actionPluginType));
    }

	public void setInterrupt() {
		// 
		Thread.currentThread().interrupt();
	}
}