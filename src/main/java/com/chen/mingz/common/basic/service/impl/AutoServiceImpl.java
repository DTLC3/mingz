package com.chen.mingz.common.basic.service.impl;


import com.alibaba.fastjson.JSON;
import com.chen.mingz.common.auto.CmzBootQuery;
import com.chen.mingz.common.basic.dao.impl.CmzSearchRepository;
import com.chen.mingz.common.basic.model.CmzSearch;
import com.chen.mingz.common.basic.service.AutoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Service("AutoSearchService")
public class AutoServiceImpl implements AutoService, EnvironmentAware {

    private Environment environment;

    @Resource
    private CmzBootQuery autoCurd;

    @Resource
    private CommonService service;

    @Resource(name = "cmzsearch_repository")
    private CmzSearchRepository repository;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    @Override
    public String register(String json) {
        CmzSearch search = JSON.parseObject(json, CmzSearch.class);
        String query = search.getInquire();
        search.setCreate_time(new Date());
        search.setEnable(1);
        String sql = autoCurd.queryPage(query);
        service.nativeQuery(sql);
        try {          //如果已经存在则先删除已有服务,及覆盖
            autoCurd.eject(search.getService());
        } catch (Exception e) {
            log.info("<<<<<<<<<<<<<" + e.getMessage() + ">>>>>>>>>>>>");
        }
        try {
            autoCurd.injectSearch(json);
            repository.save(search);
        } catch (Exception e) {
            log.warn("<<<<<<<<<<<<<" + e.getMessage() + ">>>>>>>>>>>>");
            return e.getMessage();
        }
        return "添加成功:" + search.getService();
    }

    @Override
    public String html() {
        return "";
    }


    @Override
    public void refresh() {
        autoCurd.refresh();
    }


}
