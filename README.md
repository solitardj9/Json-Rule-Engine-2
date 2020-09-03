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



