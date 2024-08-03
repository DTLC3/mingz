package com.chen.mingz.common.soap.namespace;

import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;

public class CustomName extends NamespacePrefixMapper {

    @Override //suggestion就是默认的前缀
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        if (namespaceUri.equals("http://schemas.xmlsoap.org/soap/envelope/")) {
            return "soap";
        } else if (namespaceUri.equals("http://www.molss.gov.cn/")) {
            return "in";
        }
        return "business";
    }

}
