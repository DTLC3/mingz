package com.chen.mingz.common.basic.service.impl;


import com.chen.mingz.common.basic.dao.impl.BaseDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommonService extends BaseService {

    @Resource
    public void setDao(BaseDao dao) {
        this.dao = dao;
    }

}
