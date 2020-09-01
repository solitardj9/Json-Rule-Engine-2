package com.example.demo.dataActionMngt.model;

import java.util.List;

public class DataActionConfigs {
    //
    private List<DataActionConfig> configs;
    
    public List<DataActionConfig> getConfigs() {
		return configs;
	}

	public void setConfigs(List<DataActionConfig> configs) {
		this.configs = configs;
	}

	public DataActionConfigs() {
    }

    public DataActionConfigs(List<DataActionConfig> configs) {
        this.configs = configs;
    }

    @Override
    public String toString() {
        return "DataActionConfigs [configs=" + configs.toString() + "]";
    }
}