package jsonRuleEngine.data;

public class Node {
	//
	private Object input;
	
	private String condtion;
	
	private Object value;

	public Node() {
		
	}
	
	public Node(Object input, String condtion, Object value) {
		this.input = input;
		this.condtion = condtion;
		this.value = value;
	}

	public Object getInput() {
		return input;
	}

	public void setInput(Object input) {
		this.input = input;
	}

	public String getCondtion() {
		return condtion;
	}

	public void setCondtion(String condtion) {
		this.condtion = condtion;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Node [input=" + input + ", condtion=" + condtion + ", value=" + value + "]";
	}
}