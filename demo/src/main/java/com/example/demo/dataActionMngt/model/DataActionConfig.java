package com.example.demo.dataActionMngt.model;

public class DataActionConfig {
    //
    private String trigger;     // trigger 설정(json format)
    
    private String result;      // trigger 통과 시 수행 process(의 결과)
    
    private String action;      // trigger 통과 후 result를 가지고 수행해야 하는 action
    
    public String getTrigger() {
		return trigger;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
    public String toString() {
        return "DataActionConfig [trigger=" + trigger + ", result=" + result + ", action=" + action + "]";
    }
}