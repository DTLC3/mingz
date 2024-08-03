package com.chen.mingz.common.soap.namespace;


import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;

public class RespondCName extends NamespacePrefixMapper {

    @Override
    public String getPreferredPrefix(String url,
                                     String suggestion, boolean requirePrefix) {
        String namespace = suggestion; //先赋值默认前缀
        switch (url) {
            case "http://schemas.xmlsoap.org/soap/envelope/":
                namespace = "soap";
                break;
            case "http://www.w3.org/2001/XMLSchema":
                namespace = "xsd";
                break;
            case "http://www.w3.org/2001/XMLSchema-instance":
                namespace = "xsi";
                break;
            case "http://www.molss.gov.cn/":
                namespace = "out";
                break;
            default:
                break;
        }
        return namespace;
    }

}
