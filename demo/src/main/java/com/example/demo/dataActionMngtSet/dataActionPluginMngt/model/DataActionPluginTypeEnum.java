package com.example.demo.dataActionMngtSet.dataActionPluginMngt.model;

public class DataActionPluginTypeEnum {
    //
    public enum DataActionPluginType {
        //
        EVNET("event", "eventActionPluginManager"),
        ERROR("error", "errorActionPluginManager")
        ;
        
        private String type;
        private String serviceName;
        
        private DataActionPluginType(String type, String serviceName) {
            this.type = type;
            this.serviceName = serviceName;
        }
        
        public String getType() {
            return type;
        }
    
        public String getServiceName() {
            return serviceName;
        }
    
        @Override
        public String toString() {
            return type;
        }
    }
    
    public static String getServiceName(String actionPluginType) {
        //
        for (DataActionPluginType iter : DataActionPluginType.values()) {
            if (iter.getType().equals(actionPluginType)) {
                return iter.getServiceName(); 
            }
        }
        return null;
    }
}