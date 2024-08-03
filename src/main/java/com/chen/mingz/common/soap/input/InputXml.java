package com.chen.mingz.common.soap.input;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class InputXml implements Serializable{


	private static final long serialVersionUID = 6032356094853553393L;

	private InputHead header = new InputHead();
	
	private Map<String, String> headMap = new HashMap<String, String>();
	
	private InputBody body = new InputBody();
	
	public InputHead getHeader() {
		return header;
	}
	public void setHeader(InputHead header) {
		this.header = header;
	}
	public InputBody getBody() {
		return body;
	}
	public void setBody(InputBody body) {
		this.body = body;
	}
	public Map<String, String> getHeadMap() {
		return headMap;
	}
	public void setHeadMap(Map<String, String> headMap) {
		this.headMap = headMap;
	}
	@Override
	public String toString() {
		return "InputXml [header=" + header + ", headMap=" + headMap + ", body=" + body + "]";
	}
	
	
}
