package com.chen.mingz.office.basic;

import com.chen.mingz.common.spi.DServiceLoader;

/**
 * @author chenmingzhi
 * @date 2019-08-15
 */
public abstract class AbstractExporter implements Exporter {

    static public DServiceLoader<Exporter> spiLoader = DServiceLoader.getServiceLoader(Exporter.class);

}
