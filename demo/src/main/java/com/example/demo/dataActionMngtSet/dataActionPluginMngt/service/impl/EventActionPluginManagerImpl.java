package com.example.demo.dataActionMngtSet.dataActionPluginMngt.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.dataActionMngtSet.dataActionPluginMngt.service.DataActionPluginManager;


@Service("eventActionPluginManager")
public class EventActionPluginManagerImpl implements DataActionPluginManager {

    private static final Logger logger = LoggerFactory.getLogger(EventActionPluginManagerImpl.class);
    
    @Override
    public Object doActon(Object data) {
        //
        if (data != null) {
            logger.info("[EventActionPluginManager].doActon : data=" + data.toString());
        }
        else {
            logger.error("[EventActionPluginManager].doActon : data is null");
        }
        return null;
    }
}