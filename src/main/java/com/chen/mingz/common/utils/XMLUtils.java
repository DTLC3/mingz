package com.chen.mingz.common.utils;


import com.chen.mingz.common.soap.common.YlzXmlFilter;
import com.chen.mingz.common.soap.error.ErrorResultCause;
import com.chen.mingz.common.soap.error.ErrorResultFlag;
import com.chen.mingz.common.soap.error.ErrorXml;
import com.chen.mingz.common.soap.input.*;
import com.chen.mingz.common.soap.namespace.CustomName;
import com.chen.mingz.common.soap.namespace.RespondCName;
import com.chen.mingz.common.soap.output.*;
import com.chen.mingz.common.soap.transform.*;
import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLFilterImpl;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLUtils {

    private static String require = "1";

    private static String versions = "1.0";
    //转换XML的头部
    private static XmlHead inHead = new XmlHead();

    //转换XML的IN部分的Body
    private static List<MapPara> inMapPara = new ArrayList<MapPara>(); //辅助list
    private static XmlBody inBody = new XmlBody();
    private static List<MapList> inMapList = new ArrayList<MapList>(); //辅助List

    private static XmlHead outHead = new XmlHead();

    //转换XML的out部分的body
    private static List<MapPara> outMapPara = new ArrayList<MapPara>();//辅助list
    private static XmlBody outBody = new XmlBody();
    private static List<MapList> outMapList = new ArrayList<MapList>();//辅助List


    @SuppressWarnings("unchecked")
    private static void parseXml(org.dom4j.Document document) {
        if (document != null) {
            org.dom4j.Element element = document.getRootElement();
            if (element != null) {
                List<org.dom4j.Element> eleList = element.elements();
                for (org.dom4j.Element ele : eleList) {
                    String name = ele.getName();
                    switch (name) {
                        case "in-head":
                            parseHead(ele, inHead);
                            break;
                        case "in-body":
                            parseBody(ele.elements(), inMapPara,
                                    inMapList, inBody, null);
                            break;
                        case "out-head":
                            parseHead(ele, outHead);
                            break;
                        case "out-body":
                            parseBody(ele.elements(), outMapPara,
                                    outMapList, outBody, null);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    private static void mapPara(List<org.dom4j.Attribute> attrList,
                                List<MapPara> list,
                                Map<String, String> map, Map<String, String> requreMap) {
        String newKey = "", oldKey = "", name = "", require = "", def = "";
        MapPara para = new MapPara();
        for (org.dom4j.Attribute attr : attrList) {
            String attrName = attr.getName();
            String value = attr.getValue();
            switch (attrName) {
                case "new":
                    newKey = value;
                    break;
                case "old":
                    oldKey = value;
                    break;
                case "require":
                    require = value;
                    break;
                case "name":
                    name = value;
                    break;
                case "def":
                    def = value;
                    break;
                default:
                    break;
            }
        }
        para.setNow(newKey);
        para.setOld(oldKey);
        para.setRequire(require);
        para.setName(name);
        para.setDef(def);
        map.put(newKey, oldKey);
        list.add(para);
        if (require.equals("1")) {  //要求必填
            requreMap.put(newKey, oldKey);
        }
    }

    private static void parsePara(List<org.dom4j.Attribute> attrList,
                                  List<MapPara> list) {
        String newKey = "", oldKey = "", name = "", require = "", def = "";
        MapPara para = new MapPara();
        for (org.dom4j.Attribute attr : attrList) {
            String attrName = attr.getName();
            String value = attr.getValue();
            switch (attrName) {
                case "new":
                    newKey = value;
                    break;
                case "old":
                    oldKey = value;
                    break;
                case "require":
                    require = value;
                    break;
                case "name":
                    name = value;
                    break;
                case "def":
                    def = value;
                    break;
                default:
                    break;
            }
        }
        para.setNow(newKey);
        para.setOld(oldKey);
        para.setRequire(require);
        para.setName(name);
        para.setDef(def);
        list.add(para);
    }

    @SuppressWarnings("unchecked")
    private static void parseHead(org.dom4j.Element element, XmlHead head) {
        if (element != null) {
            List<org.dom4j.Element> eleList = (List<org.dom4j.Element>) element.elements();
            for (org.dom4j.Element ele : eleList) {
                List<org.dom4j.Attribute> attrList = ele.attributes();
                mapPara(attrList, head.getInHead(), head.getInHeadMap(), head.getInRequire());
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void parseBody(List<org.dom4j.Element> elements,
                                  List<MapPara> list,
                                  List<MapList> mapList,
                                  XmlBody body,
                                  MapList parent) {
        for (org.dom4j.Element element : elements) {
            String name = element.getName();
            if ("maplist".equals(name)) {
                List<org.dom4j.Attribute> mAttrs = element.attributes(); //maplist的属性
                MapList mList = new MapList();
                for (org.dom4j.Attribute mAttr : mAttrs) { //设置mapList的属性
                    String mName = mAttr.getName();
                    String mValue = mAttr.getValue();
                    switch (mName) {
                        case "name":
                            mList.setName(mValue);
                            break;
                        case "require":
                            mList.setRequire(mValue);
                            break;
                        default:
                            break;
                    }
                }
                List<MapPara> innerList = new ArrayList<MapPara>();
                parseBody(element.elements(), innerList, mapList, body, mList);
                mList.setMaplist(innerList);
                if (parent == null) {   //是否属于根节点
                    mapList.add(mList);
                } else {
                    parent.setChild(mList);
                }
            } else {
                parsePara(element.attributes(), list);
            }
        }
        body.setParaList(list);
        body.setMapList(mapList);
    }

    public static TransformXml parse(String xml) {
        try {
            org.dom4j.Document document = DocumentHelper.parseText(xml);
            return parse(document);
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void fillMapList(MapList map) {
        List<MapPara> maplist = map.getMaplist();
        Map<String, String> mapMap = map.getMapMap();
        Map<String, String> mapRequire = map.getMapRequire();
        MapList child = map.getChild();
        for (MapPara para : maplist) {
            mapMap.put(para.getOld(), para.getNow());
            if (para.getRequire() != null && para.getRequire().equals(require)) {
                mapRequire.put(para.getOld(), para.getNow());
            }
        }
        if (child != null) {
            fillMapList(child);
        }
    }

    private static void fillMapBody(XmlBody body) {
        List<MapPara> paraList = body.getParaList(); //普通的map数据填充
        List<MapList> mapList = body.getMapList();
        Map<String, MapList> mapMap = body.getMapMap();
        Map<String, String> paraMap = body.getParaMap();
        Map<String, String> paraRequire = body.getParaRequire();
        for (MapPara para : paraList) {
            paraMap.put(para.getOld(), para.getNow());
            if (para.getRequire() != null &&
                    para.getRequire().equals(require)) { //填充必填项的Map
                paraRequire.put(para.getOld(), para.getNow());
            }
        }
        for (MapList map : mapList) {
            String name = map.getName();
            mapMap.put(name, map);
            fillMapList(map);
        }
    }

    //将List中的数据填写到map中去(head不需要，已经在之前的解析过程中做了)
    private static void fillMapData(TransformXml transform) {
        fillMapBody(transform.getInBody());
        fillMapBody(transform.getOutBody());
    }

    private static void fillMapHead(XmlHead head) {
        List<MapPara> inHead = head.getInHead();
        Map<String, String> inHeadMap = head.getInHeadMap();
        Map<String, String> inRequire = head.getInRequire();
        for (MapPara para : inHead) {
            inHeadMap.put(para.getNow(), para.getOld());
            if (para.getRequire() != null && para.getRequire().equals(require)) {
                inRequire.put(para.getOld(), para.getNow());
            }
        }
    }

    public static TransformXml parse(org.dom4j.Document document) {
        parseXml(document);
        TransformXml transform = new TransformXml();
        transform.setInHead(inHead);
        transform.setInBody(inBody);
        transform.setOutHead(outHead);
        transform.setOutBody(outBody);
        fillMapData(transform);
        checkSame(transform);
        return transform;
    }

    private static boolean checkParaList(List<MapPara> paraList) {
        for (MapPara para : paraList) {
            String now = para.getNow();
            String old = para.getOld();
            if (!now.equals(old)) {
                return false;
            }
        }
        return true;
    }

    //检测转换文件的Body部分是否new与old的值一模一样
    private static boolean checkBodySame(XmlBody body) {
        if (body != null) {
            List<MapPara> paraList = body.getParaList();
            List<MapList> mapList = body.getMapList();
            boolean paraSame = checkParaList(paraList);
            if (!paraSame) {            //只要有1个para中的now和old不一致
                return false;                //则认为需要进行新旧属性的转换
            }
            for (MapList list : mapList) {
                boolean same = checkParaList(list.getMaplist());
                if (!same) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkRequires(XmlBody body) {
        Map<String, String> require = body.getParaRequire();
        if (require != null && require.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    //检测转换文件new和old的值是否一模一样和那些项是必填
    private static void checkSame(TransformXml target) {
        if (target != null) {
            //先检测是否存在require的值
            boolean inRequire = checkRequires(target.getInBody());
            boolean in = checkBodySame(target.getInBody());
            if (!inRequire || !in) { //存在转换和需要校验则认为应该进行转换
                target.setInSame(false);
            }
            boolean outRequire = checkRequires(target.getInBody());
            boolean out = checkBodySame(target.getOutBody());
            if (!outRequire || !out) {
                target.setOutSame(false);
            }
        }
    }

    private static void process(TransformXml target) {
        fillMapHead(target.getInHead());
        fillMapHead(target.getOutHead());
        fillMapBody(target.getInBody());
        fillMapBody(target.getOutBody());
        checkSame(target);   //判断新旧转换是否完全一样，如果都一样就不行转换操作了
    }

    //xml字符串转Java 对象
    @SuppressWarnings("unchecked")
    public static <T> T xml2bean(String xml, Class<T> c) {
        T target = null;
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            target = (T) unmarshaller.unmarshal(new StringReader(xml));
            if (target instanceof TransformXml) {
                process((TransformXml) target);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return target;
    }

    //默认encoding为"UTF-8"
    public static String bean2Xml(Object object, String encoding) {
        XMLStreamWriter writer = null;
        try {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            writer = xmlOutputFactory.createXMLStreamWriter(stream);
            writer.writeStartDocument((String) marshaller.getProperty(Marshaller.JAXB_ENCODING), versions);
            marshaller.marshal(object, writer);
            writer.writeEndDocument();
            writer.close();
            return stream.toString();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (XMLStreamException e1) {
                e1.printStackTrace();
            }
            return null;
        }
    }

    public static String bean2Xml(InputXml input) {
        Envelope envelope = InputToEnvelop(input);
        return bean2Xml(envelope, new CustomName(), new YlzXmlFilter());
    }

    public static String bean2XmlEnd(InputXml input) {
        return bean2XmlEnd(input, "para");
    }

    public static String bean2XmlEnd(InputXml input, String... replace) {
        Envelope envelope = InputToEnvelop(input);
        String xml = bean2Xml(envelope, new CustomName(), new YlzXmlFilter());
        for (int i = 0, length = replace.length; i < length; ++i) {
            String reg = "(<" + replace[i] + ")(\\s)?(.*?)(?:>)([\\s\\S]*?)(</" + replace[i] + ">)";
            xml = xml.replaceAll(reg, "$1" + "$2" + "$3" + "$4" + "/>");//
        }
        return xml;
    }

    private static String jaxbRun(Object object,
                                  NamespacePrefixMapper custom,
                                  XMLFilterImpl filter,
                                  String encode) {
        XMLWriter writer = null;
        StringWriter out = null;
        try {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encode);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.FALSE);
            marshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", custom);
            out = new StringWriter();
            OutputFormat format = new OutputFormat();
            format.setIndent(true);
            format.setNewlines(true);
            format.setNewLineAfterDeclaration(false);
            format.setEncoding(encode);
            writer = new XMLWriter(out, format);
            filter.setContentHandler(writer);
            marshaller.marshal(object, filter);
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Java对象转为固定XML格式，包括生成命名空间的处理，和格式的自定义
    //JavaMutXmlUtil.bean2Xml(envelope, new CustomName(), new YlzXmlFilter());
    public static String bean2Xml(Object object,
                                  NamespacePrefixMapper custom,
                                  XMLFilterImpl filter,
                                  String encode) {
        return jaxbRun(object, custom, filter, encode);
    }

    public static String bean2Xml(Object object,
                                  NamespacePrefixMapper custom,
                                  XMLFilterImpl filter) {
        return jaxbRun(object, custom, filter, "UTF-8");
    }

    public static String bean2Xml(Object object) {
        return bean2Xml(object, "UTF-8");
    }

    private static void parseSoap(String xml, DefaultHandler handler,
                                  String code, String message) {
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser saxParser = spf.newSAXParser();
            StringReader sr = new StringReader(xml);
            saxParser.parse(new org.xml.sax.InputSource(sr), handler);
        } catch (Exception e) {
            throw new RuntimeException(message);
        }
    }

    private static void parseInput(String xml, DefaultHandler handler) {
        String code = "11009";
        String message = "请求报文的xml格式有问题，建议格式化XML看是否正确(input xml format error)";
        parseSoap(xml, handler, code, message);
    }

    public static void parseOutput(String xml, DefaultHandler handler) {
        String code = "11019";
        String message = "要转化的输出XML格式有问题(output xml format error)";
        parseSoap(xml, handler, code, message);
    }

    public static InputXml parseInput(String xml) {
        InputXmlHandler handler = new InputXmlHandler();
        parseInput(xml, handler);
        return handler.getInputXml();
    }

    public static RespondXml parseOutput(String xml) {
        OutputXmlHandler handler = new OutputXmlHandler();
        parseOutput(xml, handler);
        return handler.getRespond();
    }

    private static void responseBody(StringBuffer body, Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            body.append("<result " + key + "=\"" + value + "\" />");
        }
    }

    private static void responseNestBody(StringBuffer body, Map<String, String> map) {
        if (map != null && map.size() > 0) {
            body.append("<result ");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                body.append(" " + key + "=\"" + value + "\" ");
            }
            body.append(" />");
        }
    }

    //生成返回值的XML格式数据(#######已经弃用#########)
    public static String bean2RsponseXml(RespondXml response) {
        String head = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" "
                + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">"
                + "<soap:Header>{}</soap:Header> <soap:Body>"
                + "<out:business xmlns:out=\"http://www.molss.gov.cn/\"> <out:business>";
        StringBuffer body = new StringBuffer("");
        if (response != null) {
            Map<String, String> result = response.getResult();
            Map<String, List<Map<String, String>>> resultSet = response.getResultSet();
            responseBody(body, result);
            for (Map.Entry<String, List<Map<String, String>>> entry : resultSet.entrySet()) {
                String key = entry.getKey();
                List<Map<String, String>> values = entry.getValue();
                body.append("<resultSet name=\"" + key + "\">");
                for (Map<String, String> nestResult : values) {
                    responseNestBody(body, nestResult);
                }
                body.append("</resultSet>");
            }
        }
        String tail = "</out:business>  </out:business>  </soap:Body></soap:Envelope>";
        return head + body + tail;
    }

    //转换输入参数的body部分
    private static List<InputParaList> changeBodyList(List<MapList> list,  //转换根据的List
                                                      Map<String, MapList> map,            //转换根据的Map
                                                      List<InputParaList> inputList) {     //需要转化的Input内容
        List<InputParaList> newInputParaList = new ArrayList<InputParaList>();
        for (InputParaList input : inputList) {
            InputParaList newInput = new InputParaList();
            String name = input.getName();
            MapList mapList = map.get(name);
            if (mapList == null) { //当前的name不存在对应转换关系
                newInput.setList(input.getList());
                newInput.setName(input.getName());
            } else {
                Map<String, String> old2new = mapList.getMapMap();
                if (old2new == null) { //不存在对应转换关系
                    newInput.setList(input.getList());
                    newInput.setName(input.getName());
                } else {
                    List<InputRow> rowList = input.getList();
                    List<InputRow> newRowList = new ArrayList<InputRow>();
                    for (InputRow row : rowList) {
                        Map<String, String> rowMap = row.getValues();
                        Map<String, String> newRowMap = new HashMap<String, String>();
                        InputRow newRow = new InputRow();
                        for (Map.Entry<String, String> rowEntity : rowMap.entrySet()) {
                            String old = rowEntity.getKey();
                            if (old2new.get(old) != null) {
                                newRowMap.put(old2new.get(old), rowEntity.getValue());
                            } else {
                                newRowMap.put(old, rowEntity.getValue());
                            }
                        }
                        newRow.setValues(newRowMap);
                        newRowList.add(newRow);
                    }
                    newInput.setList(newRowList);
                    newInput.setName(name);
                }
            }
            newInputParaList.add(newInput);
        }
        return newInputParaList;
    }

    //填充Map的映射关系
    private static Map<String, InputParaList> applyMap(List<InputParaList> list) {
        Map<String, InputParaList> map = new HashMap<String, InputParaList>();
        if (list != null) {
            for (InputParaList entity : list) {
                map.put(entity.getName(), entity);
            }
        }
        return map;
    }

    //验证必输项是否填写
    private static void checkAuth(Map<String, String> param,
                                  Map<String, String> require) {
        for (Map.Entry<String, String> entry : require.entrySet()) {
            String key = entry.getKey();
            String detection = param.get(key);
            if (detection == null || "".equals(detection)) {
                throw new RuntimeException("4001" + "出错啦，" + key + "为必填项，但却为空");
            }
        }
    }

    //替换输入参数input的header部分
    private static Map<String, String> changeInputHeader(Map<String, String> header, Map<String, String> criteria) {
        Map<String, String> map = new HashMap<String, String>();
        if (criteria != null) { //首先需要存在转换标准
            for (Map.Entry<String, String> entry : header.entrySet()) {
                String old = entry.getKey();
                String now = criteria.get(old);
                String value = entry.getValue();
                if (now != null) { //存在新旧转换关系
                    map.put(now, value);
                } else {
                    map.put(old, value);
                }
            }
        } else {
            map = header;
        }
        return map;
    }

    //根据转换文件将输入的input字段名转为目标字段名
    public static InputXml changeInput(InputXml input, TransformXml transform) {
        XmlBody body = transform.getInBody();
        InputXml result = new InputXml();
        InputBody newBody = new InputBody();
        result.setHeader(input.getHeader());
        result.setHeadMap(changeInputHeader(input.getHeadMap(), transform.getInHead().getInHeadMap()));//转换head的map数据
        if (body != null) {
            Map<String, String> bodyPara = input.getBody().getParaMap(); //需要转换的输入para键值对
            Map<String, String> newInputBodyMap = new HashMap<String, String>();  //生成的新的para键值对
            Map<String, String> paraMap = body.getParaMap();     // para对应新旧转换键值对
            List<InputPara> newParaList = new ArrayList<InputPara>();      //新的para链表
            checkAuth(bodyPara, body.getParaRequire());  //检测必填项是否已填
            for (Map.Entry<String, String> entry : bodyPara.entrySet()) {
                InputPara para = new InputPara();
                String old = entry.getKey();
                String value = entry.getValue();
                if (paraMap.get(old) != null) {
                    para.setName(paraMap.get(old));
                    newInputBodyMap.put(paraMap.get(old), value);
                } else { //不存在转换关系则不变
                    para.setName(old);
                    newInputBodyMap.put(old, value);
                }
                para.setValue(value);
                newParaList.add(para);
            }
            Map<String, MapList> mapMap = body.getMapMap();   //多层结构的嵌套
            List<MapList> mapList = body.getMapList();
            //转换body部分
            List<InputParaList> newInputParaList = changeBodyList(mapList, mapMap, input.getBody().getParaList());
            Map<String, InputParaList> listMap = applyMap(newInputParaList);        //将List数据填充到Map中去
            newBody.setPara(newParaList);
            newBody.setParaMap(newInputBodyMap);
            newBody.setListMap(listMap);
            newBody.setParaList(newInputParaList);
            result.setBody(newBody);
        } else { //不存在转换关系
            result.setBody(input.getBody());
        }
        return result;
    }

    private static Map<String, List<Map<String, String>>> changeNestBody(List<MapList> list,  //转换根据的List
                                                                         Map<String, MapList> map,            //转换根据的Map
                                                                         Map<String, List<Map<String, String>>> resultSet) {
        Map<String, List<Map<String, String>>> newResultSet = new HashMap<String, List<Map<String, String>>>();
        for (Map.Entry<String, List<Map<String, String>>> entry : resultSet.entrySet()) { //根据需要转换的map来决定走向
            String name = entry.getKey();  //old的name
            List<Map<String, String>> values = entry.getValue(); //old的values
            List<Map<String, String>> newValues = new ArrayList<Map<String, String>>();  //新的values
            MapList mapList = map.get(name); //转换依据
            Map<String, String> old2new = mapList != null ? mapList.getMapMap() : new HashMap<String, String>();
            for (Map<String, String> result : values) {
                Map<String, String> newResult = new HashMap<String, String>();
                for (Map.Entry<String, String> nestEntry : result.entrySet()) {
                    String old = nestEntry.getKey();
                    String value = nestEntry.getValue();
                    if (old2new.get(old) == null) { //不存在对应转换关系则不变
                        newResult.put(old, value);
                    } else {
                        newResult.put(old2new.get(old), value);
                    }
                }
                newValues.add(newResult);
            }
            newResultSet.put(name, newValues);
        }
        return newResultSet;
    }

    //根据转换文件将输入的ResponseXml中的相应属性转化为目标格式
    public static RespondXml changeOutput(RespondXml output, TransformXml transform) {
        RespondXml newRespond = new RespondXml();
        XmlBody body = transform.getOutBody();
        if (body != null) {
            Map<String, String> paraMap = body.getParaMap();     // para对应新旧转换键值对
            Map<String, MapList> mapMap = body.getMapMap();   //多层结构的嵌套
            List<MapList> mapList = body.getMapList();
            Map<String, String> result = output.getResult();
            Map<String, String> newResult = new HashMap<String, String>();
            for (Map.Entry<String, String> entry : result.entrySet()) { //简单新旧转换
                String key = entry.getKey();
                String value = entry.getValue();
                if (paraMap.get(key) != null) {
                    newResult.put(paraMap.get(key), value);  //将旧的Key替换为新的Key
                } else {
                    newResult.put(key, value); //不存在转换关系的话就原封不动
                }
            }
            Map<String, List<Map<String, String>>> newResultSet = changeNestBody(mapList, mapMap, output.getResultSet());
            newRespond.setResult(newResult);
            newRespond.setResultSet(newResultSet);
        }
        return newRespond;
    }

    private static Envelope InputToEnvelop(InputXml input) {
        Envelope envelope = new Envelope();
        if (input != null) {
            Map<String, String> headMap = input.getHeadMap();
            SoapHeader soapHeader = new SoapHeader();
            InSystem system = new InSystem();
            List<InPara> inParaList = new ArrayList<InPara>();
            for (Map.Entry<String, String> entry : headMap.entrySet()) {
                InPara para = new InPara();
                para.setOne(entry.getKey());
                para.setTwo(entry.getValue());
                inParaList.add(para);
            }
            system.setList(inParaList);
            soapHeader.setSystem(system);
            //开始转换Envelope中的body部分数据
            SoapBody soapBody = new SoapBody();
            InBusiness business = new InBusiness();
            List<InPara> bodyParaList = new ArrayList<InPara>();
            List<InParaList> paraListList = new ArrayList<InParaList>();
            //读取input中的body部分数据
            InputBody inputBody = input.getBody() != null ? input.getBody() : new InputBody();
//			List<InputPara> inputParaList = inputBody.getPara();
            Map<String, String> paraMap = inputBody.getParaMap();
            for (Map.Entry<String, String> paraEntry : paraMap.entrySet()) {
                InPara para = new InPara();
                para.setOne(paraEntry.getKey());
                para.setTwo(paraEntry.getValue());
                bodyParaList.add(para);
            }
            //开始转换input body中的 paralist 标签的内容
            Map<String, InputParaList> listMap = inputBody.getListMap();
            for (Map.Entry<String, InputParaList> entry : listMap.entrySet()) {
                InParaList nestPara = new InParaList();
                nestPara.setName(entry.getKey());
                InputParaList nestParaList = entry.getValue();
                List<InputRow> inputRows = nestParaList.getList();
                List<ListRows> listRows = new ArrayList<ListRows>();
                for (InputRow row : inputRows) {
                    String keys = "", values = "";
                    ListRows nestlistRows = new ListRows();
                    Map<String, String> rowValues = row.getValues();
                    for (Map.Entry<String, String> nestEntry : rowValues.entrySet()) {
                        String key = nestEntry.getKey();
                        String value = nestEntry.getValue();
                        if ("".equals(value)) {
                            value = "<br>";
                        }
                        keys += key + "#";
                        values += value + "#";
                    }
                    nestlistRows.setKeys(keys);
                    nestlistRows.setValues(values);
                    listRows.add(nestlistRows);
                }
                nestPara.setList(listRows);
                paraListList.add(nestPara);
            }
            business.setList(bodyParaList);
            business.setParaList(paraListList);
            soapBody.setBusiness(business);

            envelope.setSoapHeader(soapHeader);
            envelope.setSoapBody(soapBody);
        }
        return envelope;
    }

    public static String getErrorXml(String cause, String flag) {
        ErrorResultCause errorCause = new ErrorResultCause(cause);
        ErrorResultFlag errorFlag = new ErrorResultFlag(flag);
        YRNestBusiness nestBusiness = new YRNestBusiness(errorCause, errorFlag);
        YRBusiness business = new YRBusiness(nestBusiness);
        YRHeader header = new YRHeader();
        YRBody body = new YRBody(business);
        ErrorXml error = new ErrorXml(header, body);
        return XMLUtils.bean2Xml(error, new RespondCName(), new RespondXmlFilter());
    }

    //将RespondXml对象转化为YRespond
    private static YRespond RespondXml2YRespond(RespondXml xml) {
        YRespond respond = new YRespond();
        Map<String, String> map = xml.getResult();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            YNResult result = new YNResult(entry.getKey(), entry.getValue());
            respond.getBody()
                    .getBusiness()
                    .getNestBusiness().
                    getListResult().add(result);
        }
        Map<String, List<Map<String, String>>> mapList = xml.getResultSet();
        for (Map.Entry<String, List<Map<String, String>>> entry : mapList.entrySet()) {
            String name = entry.getKey();
            List<Map<String, String>> list = entry.getValue();
            List<ListRows> listRows = new ArrayList<ListRows>();
            for (Map<String, String> row : list) {
                StringBuilder keys = new StringBuilder("");
                StringBuilder values = new StringBuilder("");
                for (Map.Entry<String, String> rowResult : row.entrySet()) {
                    String key = rowResult.getKey();
                    String value = rowResult.getValue();
                    if ("".equals(value)) {
                        value = "<br>";
                    }
                    keys.append(key).append("#");
                    values.append(value).append("#");
                }
                listRows.add(new ListRows(keys.toString(), values.toString()));
            }
            respond.getBody().
                    getBusiness()
                    .getNestBusiness()
                    .getListResultSet()
                    .add(new YNResultSet(name, listRows));
        }
        return respond;
    }

    public static String getRespondXml(RespondXml xml,
                                       NamespacePrefixMapper custom,
                                       XMLFilterImpl filter) {
        YRespond respond = RespondXml2YRespond(xml);
        return XMLUtils.bean2Xml(respond, custom, filter);
    }

    public static String getRespondXml(RespondXml xml, String encode) {
        YRespond respond = RespondXml2YRespond(xml);
        return XMLUtils.bean2Xml(respond, new RespondCName(), new YRXmlFilter(), encode);
    }

    public static String getRespondXml(RespondXml xml) {
        YRespond respond = RespondXml2YRespond(xml);
        return XMLUtils.bean2Xml(respond, new RespondCName(), new YRXmlFilter(), "UTF-8");
    }


}
