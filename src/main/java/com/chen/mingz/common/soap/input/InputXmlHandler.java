package com.chen.mingz.common.soap.input;


import com.chen.mingz.common.utils.CBeanUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputXmlHandler extends DefaultHandler {

    private String InSystem = "in:system";
    private String InBusiness = "in:business";
    private String para = "para";
    private String paraList = "paralist";
    private String row = "row";
    private boolean headFlag = false;
    private boolean bodyFlag = false;
    private InputXml inputXml = new InputXml();
    private Integer bodyListNum = 0;
    private Map<String, String> headMap = new HashMap<String, String>();
    private Map<String, String> bodyPara = new HashMap<String, String>();

    private List<InputPara> bodyList = new ArrayList<InputPara>();
    private List<String> indexList = new ArrayList<String>();
    private List<InputParaList> bodyParaList = new ArrayList<InputParaList>();
    private Map<String, InputParaList> listMap = new HashMap<String, InputParaList>();

    private void parseHead(Attributes attributes) {
        String name = attributes.getQName(0);
        String value = attributes.getValue(0);
        headMap.put(name, value);
    }

    private void transformHead() {
        InputHead header = (InputHead) CBeanUtils.map2bean(headMap, InputHead.class);
        //校验头部是funid还是fun_id
        if (header.getFunid() == null) { //则输入形式不是funid
            if (headMap != null && headMap.get("fun_id") != null) {
                header.setFunid(headMap.get("fun_id"));
            }
        }
        inputXml.setHeadMap(headMap);
        inputXml.setHeader(header);
    }

    private void parseBody(String qName, Attributes attributes) {
        if (qName.equals(para)) {
            String name = attributes.getQName(0);
            String value = attributes.getValue(0);
            InputPara para = new InputPara();
            para.setName(name);
            para.setValue(value);
            bodyList.add(para);
            bodyPara.put(name, value);
        }
        if (qName.equals(paraList)) {
            InputParaList inputParaList = new InputParaList();
            String value = attributes.getValue(0);
            indexList.add(value);  //记录下paraList的name属性
            inputParaList.setName(value);
            bodyParaList.add(inputParaList);
            listMap.put(value, inputParaList);
        }
        if (qName.equals(row)) {
            InputRow row = new InputRow();
            Map<String, String> values = new HashMap<String, String>();
            for (int i = 0, length = attributes.getLength(); i < length; ++i) {
                String rowkey = attributes.getQName(i);
                String rowValue = attributes.getValue(i);
                values.put(rowkey, rowValue);
            }
            row.setValues(values);
            bodyParaList.get(bodyListNum).getList().add(row);
        }
    }

    private void transformBody() {
        inputXml.getBody().setParaMap(bodyPara);
        inputXml.getBody().setPara(bodyList);
        inputXml.getBody().setParaList(bodyParaList);
        inputXml.getBody().setListMap(listMap);
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (headFlag == true) {    //开始头部XML的解析
            parseHead(attributes);
        }
        if (bodyFlag == true) {   //开始body部分XML的解析
            parseBody(qName, attributes);
        }
        if (qName.equals(InSystem)) {
            headFlag = true;
        }
        if (qName.equals(InBusiness)) {
            bodyFlag = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        super.endElement(uri, localName, qName);
        if (bodyFlag == true && qName.equals(paraList)) {  //body中的当前paraList解析完毕
            ++bodyListNum; //递增当前list数量
        }
        if (qName.equals(InSystem)) {
            headFlag = false;
            transformHead();
        }
        if (qName.equals(InBusiness)) {
            bodyFlag = false;
            transformBody();
        }
    }

    public InputXml getInputXml() {
        return inputXml;
    }

    public void setInputXml(InputXml inputXml) {
        this.inputXml = inputXml;
    }

}
