package com.chen.mingz.office.basic;

import java.io.IOException;

/**
 * @author chenmingzhi
 * @date 2019-07-17
 */
public interface Exporter {

    void make(Object data) throws IOException;

}

