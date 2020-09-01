package com.example.demo.dataActionMngt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.example.demo.dataActionMngt.model.DataActionConfig;
import com.example.demo.dataActionMngt.model.DataActionConfigs;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service("dataActionManager")
public class DataActionManagerImpl implements DataActionManager {
    //
    private static final Logger logger = LoggerFactory.getLogger(DataActionManagerImpl.class);
    
    @Value("${test.configs}") 
    private String strConfigs;
    
    private Integer workerPool = 1;
    
    @Autowired
    ApplicationContext applicationContext;
    
    private DataActionConfigs configs;
    
    private BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
    private ExecutorService executorService = null;
    
    private ObjectMapper om = new ObjectMapper();
    
    private boolean isInitialized = false;
    
    @PostConstruct
    public void init() {
        //
        try {
            if (strConfigs != null && !strConfigs.equals("{}")) { 
                configs = om.readValue(strConfigs, DataActionConfigs.class);
            }
            else {
                configs = new DataActionConfigs(new ArrayList<DataActionConfig>());
            }
            
            logger.info("[DataActionManager] configs : " + configs.toString());
        } catch (Exception e) {
            logger.error("[DataActionManager].init : " + e.toString());
        }
        
        executorService = Executors.newFixedThreadPool(workerPool);
        for(Integer i = 0 ; i < workerPool ; i++) {
            DataActionWorker dataActionWorker = new DataActionWorker(this, queue, applicationContext);
            executorService.submit(dataActionWorker);
        }
        
        isInitialized = true;
    }
    
    @Override
    public Boolean isInitialized() {
        return isInitialized;       
    }
    
    @Override
    public void checkAndDoAction(String parsingDataObj) {
        //
        queue.add(parsingDataObj);
    }
    
//    @Override
//    public List<DataActionConfig> getDataActionConfigs() {
//        return new ArrayList<DataActionConfig>(configs.getConfigs());
//    }
}