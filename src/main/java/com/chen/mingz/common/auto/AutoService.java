package com.chen.mingz.common.auto;


import com.chen.mingz.common.basic.model.BaseParameter;
import com.chen.mingz.common.basic.service.CService;

public interface AutoService {

    Object doService(CService service, BaseParameter parameter);

}
