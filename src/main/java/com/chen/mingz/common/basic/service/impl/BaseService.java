package com.chen.mingz.common.basic.service.impl;


import com.chen.mingz.common.basic.dao.Dao;
import com.chen.mingz.common.basic.model.BaseParameter;
import com.chen.mingz.common.basic.model.QueryResult;
import com.chen.mingz.common.basic.service.CService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * Created by chenmingzhi on 18/2/28.
 */
@Transactional
public class BaseService implements CService {

    protected Dao dao;


    @Override
    public List query(BaseParameter parameter) {
        return dao.query(parameter);
    }

    @Override
    public List queryMap(BaseParameter parameter) {
        return dao.queryMap(parameter);
    }

    @Override
    public List queryMap(String sql) {
        return dao.queryMap(sql);
    }

    @Override
    public QueryResult queryPage(BaseParameter parameter) {
        return dao.queryPage(parameter);
    }

    @Override
    public QueryResult queryReplace(BaseParameter parameter) {
        return dao.queryReplace(parameter);
    }

    @Override
    public QueryResult pagination(BaseParameter parameter) {
        return dao.pagination(parameter);
    }

    @Override
    public Long queryTotal(BaseParameter parameter) {
        return dao.queryTotal(parameter);
    }


    @Override
    public int nativeQuery(String sql) {
        return dao.nativeQuery(sql);
    }

    @Override
    public <T> List<T> queryBean(BaseParameter parameter) {
        return dao.queryBean(parameter);
    }

    @Override
    public <T> T queryModel(BaseParameter parameter) {
        return dao.queryModel(parameter);
    }


    @Override
    public void procedure(String procedure) {
        dao.procedure(procedure);
    }

    @Override
    public List<Object[]> doProcedure(String procedure) {
        return dao.doProcedure(procedure);
    }

    @Override
    public Map<String, Object> doProcedure(String procedure, String[] inName, Class[] inType, Object[] inValue, Map<String, Class> out) {
        return dao.doProcedure(procedure, inName, inType, inValue, out);
    }
}
