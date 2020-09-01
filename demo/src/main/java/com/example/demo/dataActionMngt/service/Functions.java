package com.example.demo.dataActionMngt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;


/**
 * @Project : evppPlatForm
 * @Package : org.evpp.platformManager.dataActionMngtSet.dataActionMngt.service.impl
 * @FileName : Functions.java
 * @CreateDt : 2020. 7. 29.
 * @CreateUs : bk.cha@lge.com
 * @Progm Desc : Action 설정에 사용된 Json Document Parsing Class
 */
public class Functions {
	//
	private static ObjectMapper om = new ObjectMapper();
	
	public enum FUNCTION {
		//
		FUNCTION_PAYLOAD_KEY("PAYLOAD_KEY", "PAYLOAD_KEY\\(([a-z-A-Z-\\s0-9.\\\"]*)\\)") {
			@Override
			public String replaceFunction(String function, String string, String matchedFunction, String payload) {
				//
				String keyPath = getKeyPath(matchedFunction, function);
				if (keyPath != null) {
					String replacedFunction = payloadKey(payload, keyPath);
					if (replacedFunction != null) {
						String replacedString = string.replace(matchedFunction, replacedFunction);
						return replacedString;
					}
					else
						return string;
				}
				else {
					return string;
				}
			}

            @Override
            public Object doFunction(String function, String matchedFunction, String payload) {
                //
                String keyPath = getKeyPath(matchedFunction, function);
                if (keyPath != null) {
                    String key = payloadKey(payload, keyPath);
                    return key;
                }
                return null;
            }
		},
		FUNCTION_PAYLOAD_KEY_PATH("PAYLOAD_KEY_PATH", "PAYLOAD_KEY_PATH\\(([a-z-A-Z-\\s0-9.\\\"]*)\\)") {
            @SuppressWarnings("unused")
            @Override
            public String replaceFunction(String function, String string, String matchedFunction, String payload) {
                //
                String keyPath = getKeyPath(matchedFunction, function);
                if (keyPath != null) {
                    String replacedFunction = keyPath;
                    if (replacedFunction != null) {
                        String replacedString = string.replace(matchedFunction, replacedFunction);
                        return replacedString;
                    }
                }
                else {
                    return string;
                }
                return string;
            }

            @Override
            public Object doFunction(String function, String matchedFunction, String payload) {
                //
                String keyPath = getKeyPath(matchedFunction, function);
                if (keyPath != null) {
                    return keyPath;
                }
                return null;
            }
        },
		FUNCTION_PAYLOAD_VALUE("PAYLOAD_VALUE", "PAYLOAD_VALUE\\(([a-z-A-Z-\\s0-9.\\\"]*)\\)") {
			@Override
			public String replaceFunction(String function, String string, String matchedFunction, String payload) {
				//
				String keyPath = getKeyPath(matchedFunction, function);
				if (keyPath != null) {
					String replacedFunction = payloadValueAsString(payload, keyPath);
					if (replacedFunction != null) {
						return replaceString(string, matchedFunction, replacedFunction);
					}
					else {
						return string;
					}
				}
				else {
					return string;
				}
			}

            @Override
            public Object doFunction(String function, String matchedFunction, String payload) {
                //
                String keyPath = getKeyPath(matchedFunction, function);
                if (keyPath != null) {
                    Object object = payloadValueAsString(payload, keyPath);
                    if (object != null) {
                        return object;
                    }
                    return null;
                }
                return payload;
            }
		},
		FUNCTION_PAYLOAD_ARRAY("PAYLOAD_ARRAY", "PAYLOAD_ARRAY\\(([a-z-A-Z-\\s0-9.\\\"]*)\\,( [0-9]*)\\)") {
			@Override
			public String replaceFunction(String function, String string, String matchedFunction, String payload) {
				//
				KeyPathIndex keyPathIndex = getKeyPathIndex(matchedFunction, function);
				
				if (keyPathIndex != null) {
					//
					if (keyPathIndex.getKeyPath() != null) {
						//
						String replacedFunction = payloadValueAsString(payload, keyPathIndex.getKeyPath());
						if (replacedFunction != null) {
							return replaceString(string, matchedFunction, replacedFunction);
						}
						else {
							return string;
						}
					}
					else {
						// payload 자체가 json object가 아닌 json array라고 봐야 함
						String replacedFunction = payloadArrayValueAsString(payload, keyPathIndex.getIndex());
						if (replacedFunction != null) {
							return replaceString(string, matchedFunction, replacedFunction);
						}
						else {
							return string;
						}
					}
				}
				else {
					return string;
				}
			}

            @Override
            public Object doFunction(String function, String matchedFunction, String payload) {
                //
                KeyPathIndex keyPathIndex = getKeyPathIndex(matchedFunction, function);
                
                if (keyPathIndex != null) {
                    //
                    if (keyPathIndex.getKeyPath() != null) {
                        //
                        Object object = payloadValueAsString(payload, keyPathIndex.getKeyPath());
                        if (object != null)
                            return object;
                        else
                            return payload;
                    }
                    else {
                        // payload 자체가 json object가 아닌 json array라고 봐야 함
                        Object object = payloadArrayValueAsString(payload, keyPathIndex.getIndex());
                        if (object != null)
                            return object;
                        else
                            return payload;
                    }
                }
                else {
                    return payload;
                }
            }
		}
		;
		
		private String value;
		private String regExp;
		
		private FUNCTION(String value, String regExp) {
			this.value = value;
			this.regExp = regExp;
		}
		
		@Override
		public String toString() {
			return value;
		}
		
		public String getValue() {
			return value;
		}

		public String getRegExp() {
			return regExp;
		}

		public void setRegExp(String regExp) {
			this.regExp = regExp;
		}
		
		public abstract String replaceFunction(String function, String string, String matchedFunction, String payload);
		
		public abstract Object doFunction(String function, String matchedFunction, String payload);
	}
	
	/**
	 * Method Name : getFunction
	 * Method Desc : Function Type을 이용하여 FUNCTION enum 조회
	 * @param function
	 * @return
	 */
	public static String getFunction(String function) {
		//
		for (FUNCTION iter : FUNCTION.values()) {
			if (iter.toString().equals(function)) {
				return iter.getValue();
			}
		}
		return null;
	}
	
	/**
	 * Method Name : getFunctionRegExp
	 * Method Desc : Function Type을 이용하여 FUNCTION Regular Expression 조회
	 * @param function
	 * @return
	 */
	public static String getFunctionRegExp(String function) {
		//
		for (FUNCTION iter : FUNCTION.values()) {
			if (iter.toString().equals(function)) {
				return iter.getRegExp();
			}
		}
		return null;
	}
	
	/**
	 * Method Name : getFunctions
	 * Method Desc : 전체 FUNCTION enum 조회
	 * @return
	 */
	public static List<String> getFunctions() {
		//
		List<String> list = new ArrayList<>();
		for (FUNCTION iter : FUNCTION.values()) {
			list.add(iter.toString());
		}
		return list;
	}
	
	/**
	 * Method Name : replaceFunction
	 * Method Desc : Function 문자열을 이용하여 실제값을 포함한 문자열로 변환
	 * @param function
	 * @param string
	 * @param matchedFunction
	 * @param payload
	 * @return
	 */
	public static String replaceFunction(String function, String string, String matchedFunction, String payload) {
		//
		for (FUNCTION iter : FUNCTION.values()) {
			if (iter.toString().equals(function)) {
				return iter.replaceFunction(function, string, matchedFunction, payload);
			}
		}
		return null;
	}
	
	/**
	 * Method Name : getKeyPath
	 * Method Desc : Function 내부에 있는 Key Path 추출
	 * @param payloadFunction
	 * @param function
	 * @return
	 */
	private static String getKeyPath(String payloadFunction, String function) {
		//
		String result = payloadFunction.replace(getFunction(function).toString(), "").replace("(", "").replace(")", "");
		
		if (result != null && !result.isEmpty() )
			return result;
		else
			return null;
	}
	
	/**
	 * Method Name : getKeyPathIndex
	 * Method Desc : Function 내부에 있는 Key Path 및 Index 추출
	 * @param payloadFunction
	 * @param function
	 * @return
	 */
	private static KeyPathIndex getKeyPathIndex(String payloadFunction, String function) {
		//
		String params = payloadFunction.replace(getFunction(function).toString(), "").replace("(", "").replace(")", "").replace(" ", "");
		
		if (params != null && !params.isEmpty() ) {
			//
			List<String> paramList = seperateStrings(params, ",");
			
			if (paramList != null && !paramList.isEmpty()) {
				//
				String keyPath = null;
				Integer index = null;
				
				if (params.startsWith(",")) {
					index = getIndex(paramList.get(0));
				}
				else {
					if (paramList.size() == 2) {
						keyPath = paramList.get(0);
						index = getIndex(paramList.get(1)); 
					}
				}
				
				KeyPathIndex result = new KeyPathIndex(keyPath, index);			
				return result;
			}
			else {
				return null;
			}
		}
		else
			return null;
	}
	
	/**
	 * Method Name : getIndex
	 * Method Desc :  String to Integer
	 * @param index
	 * @return
	 */
	private static Integer getIndex(String index) {
		try {
			return Integer.valueOf(index);
		}
		catch (NumberFormatException e) {
			//
			return null;
		}
	}
	
	/**
	 * Method Name : payloadKey
	 * Method Desc : payload 내 Key Path에 있는 값(String)을 추출 
	 * @param payload
	 * @param keyPath
	 * @return
	 */
	private static String payloadKey(String payload, String keyPath) {
		//
		if (payloadValueAsString(payload, keyPath) != null) {
			return seperateStrings(keyPath, null, ".");
		}
		else
			return null;
	}
	
	/**
	 * 
	 * Method Name : payloadValueAsString
	 * Method Desc : payload 내 Key Path에 있는 값(Object)을 String으로 변환하여 추출
	 * @param payload
	 * @param keyPath
	 * @return
	 */
	private static String payloadValueAsString(String payload, String keyPath) {
        //
	    try {
    	    Object object = payloadValue(payload, keyPath);
    	    if (object != null) {
    	        String ret = om.writeValueAsString(object);
    	        return ret;
    	    }
    	    else {
    	        return null;
    	    }
	    }
	    catch (Exception e) {
            System.out.println("[Functions].payloadValueAsString : " + e.toString());
            return null;
        }
    }
	
	/**
	 * 
	 * Method Name : payloadValue
	 * Method Desc : payload 내 Key Path에 있는 값(Object)을 추출
	 * @param payload
	 * @param keyPath
	 * @return
	 */
	private static Object payloadValue(String payload, String keyPath) {
		//
		try {
			DocumentContext context = JsonPath.parse(payload);
			
			if (context.json() instanceof JSONArray) {	// 적어도 문서 전체가 Json Array 구조로 되어 있다면....처리 불가
				return null;
			}
			else {
				Object object = context.read("$." + keyPath);
				return object;
			}
		}
		catch (Exception e) {
		    System.out.println("[Functions].payloadValue : " + e.toString());
			return null;
		}
	}
	
	/**
	 * Method Name : payloadArrayValueAsString
	 * Method Desc : payload 내 Key Path에 있는 값(Json Array의 특정 index에 있는 Object)을 String으로 변환하여 추출
	 * @param payload
	 * @param index
	 * @return
	 */
	private static String payloadArrayValueAsString(String payload, Integer index) {
        //
        try {
            Object object = payloadArrayValue(payload, index);
            if (object != null) {
                String ret = om.writeValueAsString(object);
                return ret;
            }
            else {
                return null;
            }
        }
        catch (Exception e) {
            System.out.println("[Functions].payloadArrayValueAsString : " + e.toString());
            return null;
        }
    }
	
	/**
	 * Method Name : payloadArrayValue
	 * Method Desc : payload 내 Key Path에 있는 값(Json Array의 특정 index에 있는 Object)을 추출
	 * @param payload
	 * @param index
	 * @return
	 */
	private static Object payloadArrayValue(String payload, Integer index) {
		//
		try {
			DocumentContext context = JsonPath.parse(payload);
			if (context.json() instanceof JSONArray) {
				Object object = context.read("$[" + index + "]");
				return object;
			}
			else {
				return null;
			}
		}
		catch (Exception e) {
		    System.out.println("[Functions].payloadArrayValue : " + e.toString());
			return null;
		}
	}
	
	/**
	 * Method Name : seperateStrings
	 * Method Desc : String Tokenize
	 * @param string
	 * @param index
	 * @param seperator
	 * @return
	 */
	private static String seperateStrings(String string, Integer index, String seperator) {
		//
		StringTokenizer st = new StringTokenizer(string, seperator);
		List<String> listTopicUnits = new ArrayList<>();
		
		//System.out.println(st.countTokens());
		
		while(st.hasMoreTokens()) {
			//System.out.println(st.nextToken());
			listTopicUnits.add(st.nextToken());
		}
		//System.out.println(st.countTokens());
		
		if (index != null)
			return listTopicUnits.get(index);
		else
			return listTopicUnits.get(listTopicUnits.size()-1);
	}
	
	/**
	 * Method Name : seperateStrings
	 * Method Desc : String List Tokenize
	 * @param string
	 * @param seperator
	 * @return
	 */
	private static List<String> seperateStrings(String string, String seperator) {
		//
		StringTokenizer st = new StringTokenizer(string, seperator);
		List<String> listTopicUnits = new ArrayList<>();
		
		while(st.hasMoreTokens()) {
			listTopicUnits.add(st.nextToken());
		}

		if (!listTopicUnits.isEmpty())
			return listTopicUnits;
		else
			return null;
	}
	
	private static String replaceString(String string, String matchedFunction, String replacedFunction) {
		//
		//System.out.println("replacedFunction :" +replacedFunction);
		//System.out.println("replacedFunction :" +replacedFunction);
		String replacedString = string.replace(matchedFunction, replacedFunction);
		
		//System.out.println("matchedFunction :" + matchedFunction);
		//System.out.println("replacedString :" + replacedString);
		replacedString = replacedString.replace("\"\"", "\"").replace("\"{", "{").replace("}\"", "}");
		//System.out.println("replacedString :" + replacedString);
		
		return replacedString;
	}

    public static Object doFunction(String function, String matchedFunction, String payload) {
        //
        for (FUNCTION iter : FUNCTION.values()) {
            if (iter.toString().equals(function)) {
                return iter.doFunction(function, matchedFunction, payload);
            }
        }
        return null;
    }
}

/**
 * @Project : evppPlatForm
 * @Package : org.evpp.platformManager.dataActionMngtSet.dataActionMngt.service.impl
 * @FileName : Functions.java
 * @CreateDt : 2020. 7. 29.
 * @CreateUs : bk.cha
 * @Progm Desc : Key Paht 및 Index
 */
class KeyPathIndex {
	//
	private String keyPath;
	private Integer index;
	
	public KeyPathIndex(String keyPath, Integer index) {
		super();
		this.keyPath = keyPath;
		this.index = index;
	}

	public String getKeyPath() {
		return keyPath;
	}
	
	public void setKeyPath(String keyPath) {
		this.keyPath = keyPath;
	}
	
	public Integer getIndex() {
		return index;
	}
	
	public void setIndex(Integer index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "KeyPathIndex [keyPath=" + keyPath + ", index=" + index + "]";
	}
}