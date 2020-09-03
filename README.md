# Json-Rule-Engine-2

## Generates a message and an event when trigger is activated
1) Input is Json document.
2) Output Message & Event are Objcts or Json Documents.

## 1. Configuration
> 1) described by Json format.
> 2) parameters.

> > configs : Json array - can register more than one config pair (trigger, result, event).

> > trigger : spepcific condition set using json document.

> > > input : function to read value from json document.

> > > > key path of json (reference : https://github.com/json-path/JsonPath).

> > > operator : available logical operators with java style (>, >=, <, <=, ==, !=, &&, ||).

> > > value : target value or function to read value from json document.

> > result : Object or Json Object - can define a message which is output when trigger is activated.

> > event : Object or Json Object - can define a event which is output when trigger is activated.

> example)
<pre>
<code>
// this is a sample to trigger result and evnet when AC has a error code which is not equals to 0.
// trigger is to compare equipment's type and error code.
// result is json object.
// event is simple string.
    {
        "configs" : [
            {
                "trigger" : {
                    "input" : {
                        "input" : "read($.eqpType)", 
                        "operator" : "==", 
                        "value" : "AC"
                    }, 
                    "operator" : "&&", 
                    "value" : {
                        "input" : "read($.data.errorCode)", 
                        "operator" : "!=", 
                        "value" : 0
                    }
                }, 
                "result" : {
                    "eqpId" : "read($.eqpId)", 
                    "eqpType" : "read($.eqpType)", 
                    "errorCode" : "read($.data.errorCode)"
                }, 
                "event" : "error"
            }
        ]
    }
</code>
</pre>

## 2. Examples
###     2.1 Insert configuration
<pre>
<code>
String configs = "{...}";

JsonRuleEngine jsonRuleEngine = new JsonRuleEngineImpl();
jsonRuleEngine.insertConfigs(configs);
</code>
</pre>

###     2.2 Input Data Sample
<pre>
<code>
    {
        "data" : {
            "onoff" : "on",
            "setTemp" : 30,
            "roomTemp" : 24,
            "errorCode" : 10
        },
        "eqpId":"{eqpId}",
        "eqpType":"AC"
    }
</code>
</pre>

###     2.3 Execute Rule Engine
<pre>
<code>
String inputData = "{...}"

List<JsonRuleEngineResultSet> results = jsonRuleEngine.execute(inputData);
</code>
</pre>

###     2.4 Result Set Class
<pre>
<code>
    public class JsonRuleEngineResultSet {
		private Object result;
		private Object event;
        ...
	}
</code>
</pre>

###     2.5 Result Data Sampale
<pre>
<code>
    JsonRuleEngineResultSet [result={"errorCode":10,"eqpId":"{eqpId}","eqpType":"AC"}, event=error]
</code>
</pre>

	


