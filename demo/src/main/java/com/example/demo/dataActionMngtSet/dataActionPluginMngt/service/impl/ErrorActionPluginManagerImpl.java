package com.example.demo.dataActionMngtSet.dataActionPluginMngt.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.dataActionMngtSet.dataActionPluginMngt.service.DataActionPluginManager;


@Service("errorActionPluginManager")
public class ErrorActionPluginManagerImpl implements DataActionPluginManager {

    private static final Logger logger = LoggerFactory.getLogger(ErrorActionPluginManagerImpl.class);
    
    @Override
    public Object doActon(Object data) {
        //
        if (data != null) {
            logger.info("[ErrorActionPluginManager].doActon : data=" + data.toString());
        }
        else {
            logger.error("[ErrorActionPluginManager].doActon : data is null");
        }
        return null;
    }
}