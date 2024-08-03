package com.chen.mingz.common.soap.input;

import java.io.Serializable;
import java.util.Map;

public class InputRow implements Serializable {

	private static final long serialVersionUID = -6500167525832740244L;

	private Map<String, String> values;

	public Map<String, String> getValues() {
		return values;
	}

	public void setValues(Map<String, String> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "InputRow [values=" + values + "]";
	}
	
	
}
