package jsonRuleEngine.data;

public class JsonRuleEngineConfig {
	//
	private String trigger;
	
	private String result;
	
	public JsonRuleEngineConfig() {
		
	}
	
	public JsonRuleEngineConfig(JsonRuleEngineConfig object) {
		setTrigger(new String(object.getTrigger()));
		setResult(new String(object.getResult()));
	}

	public JsonRuleEngineConfig(String trigger, String result) {
		this.trigger = trigger;
		this.result = result;
	}

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

	@Override
	public String toString() {
		return "JsonRuleEngineConfig [trigger=" + trigger + ", result=" + result + "]";
	}
}
