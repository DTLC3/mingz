package com.chen.mingz.common.soap.input;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)  
@XmlType
public class InSystem {

	
//	@XmlJavaTypeAdapter(ParaAdapter.class)
//	@XmlAttribute(name = "para")
	@XmlElement(name = "para")
	private List<InPara> list= new ArrayList<InPara>();

	public List<InPara> getList() {
		return list;
	}

	public void setList(List<InPara> list) {
		this.list = list;
	}
	
}
