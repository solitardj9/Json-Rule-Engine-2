package com.example.demo.dataActionMngtSet.dataActionPluginMngt.service.impl;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.example.demo.dataActionMngtSet.dataActionPluginMngt.service.DataActionPluginManager;
import com.example.demo.dataActionMngtSet.dataActionPluginMngt.service.data.ErrorAction;
import com.example.demo.util.HttpProxyAdaptor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service("errorActionPluginManager")
public class ErrorActionPluginManagerImpl implements DataActionPluginManager {

    private static final Logger logger = LoggerFactory.getLogger(ErrorActionPluginManagerImpl.class);
    
    @Value("${dataActionPluginMngt.errorActionPlugin.httpClient.scheme}")
    private String scheme;
    
    @Value("${dataActionPluginMngt.errorActionPlugin.httpClient.url}")
    private String url;
    
    @Value("${dataActionPluginMngt.errorActionPlugin.httpClient.path}")
    private String path;
    
    @Value("${dataActionPluginMngt.errorActionPlugin.httpClient.httpMethodType}")
    private String httpMethodType;
    private HttpMethod httpMethod; 
    
    @Value("${dataActionPluginMngt.errorActionPlugin.httpClient.queryParamConfigs}")
    private String queryParamConfigs;
    Map<String, Object> queryParams = null;
    
    @Value("${dataActionPluginMngt.errorActionPlugin.httpClient.headerParamConfigs}")
    private String headerParamConfigs;
    Map<String, Object> headerParams = null;
    HttpHeaders headers = null;
    
    @Autowired
    HttpProxyAdaptor httpProxyAdaptor;
    
    private ObjectMapper om = new ObjectMapper();
    
    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        //
        httpMethod = makeHttpMethod(httpMethodType);
        
        try {
            queryParams = om.readValue(queryParamConfigs, Map.class);
        } catch (IOException e) {
            logger.error("[ErrorActionPluginManager].init : " + e.toString());
        }
        
        try {
            headers = new HttpHeaders();
            headerParams = om.readValue(headerParamConfigs, Map.class);
            for (Entry<String, Object> iter : headerParams.entrySet()) {
                headers.add(iter.getKey(), iter.getValue().toString());
            }
        } catch (IOException e) {
            logger.error("[ErrorActionPluginManager].init : " + e.toString());
        }
        
        url = scheme + "://" + url;
        
        logger.info("[ErrorActionPluginManager].init : scheme = " + scheme);
        logger.info("[ErrorActionPluginManager].init : url = " + url);
        logger.info("[ErrorActionPluginManager].init : path = " + path);
        logger.info("[ErrorActionPluginManager].init : httpMethod = " + httpMethod.toString());
        logger.info("[ErrorActionPluginManager].init : queryParams = " + queryParams.toString());
        logger.info("[ErrorActionPluginManager].init : headers = " + headers.toString());
    }
    
    private HttpMethod makeHttpMethod(String httpMethodType) {
        //
        if (httpMethodType != null) {
            return HttpMethod.valueOf(StringUtils.upperCase(httpMethodType));
        }
        else {
            return null;
        }
    }
    
    @Override
    public Object doActon(Object data) {
        //
        if (data != null && (data instanceof String) && !((String)data).isEmpty()) {
            //logger.info("[ErrorActionPluginManager].doActon : data=" + data.toString());
            
            ErrorAction errorAction = null;
			try {
				errorAction = om.readValue((String)data, ErrorAction.class);	// TODO : necessary ? or not ?
				
				String requestBody = (String)data;
				
				//logger.info("[ErrorActionPluginManager].doActon : errorAction=" + errorAction.toString());
				
				return httpProxyAdaptor.executeHttpProxy(scheme, url, path, queryParams, httpMethod, headers, requestBody);
			} catch (JsonProcessingException e) {
				logger.error("[ErrorActionPluginManager].doActon : error = " + e.toString());
				return null;
			}
        }
        else {
            logger.error("[ErrorActionPluginManager].doActon : data is null or wrong (" + data.toString() + ")");
        }
        
        return null;
    }
}