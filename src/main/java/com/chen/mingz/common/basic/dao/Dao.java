package com.chen.mingz.common.basic.dao;


import com.chen.mingz.common.basic.model.BaseParameter;
import com.chen.mingz.common.basic.model.QueryResult;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * Created by chenmingzhi on 18/2/27.
 */
@Transactional
public interface Dao {

    List query(BaseParameter parameter);

    List queryMap(BaseParameter parameter);

    List queryMap(String sql);

    QueryResult queryPage(BaseParameter parameter);

    QueryResult queryReplace(BaseParameter parameter);

    QueryResult pagination(BaseParameter parameter);

    Long queryTotal(BaseParameter parameter);

    int nativeQuery(String sql);

    <T> List<T> queryBean(BaseParameter parameter);

    <T> T queryModel(BaseParameter parameter);

    void procedure(String procedure);

    List<Object[]> doProcedure(String procedure);

    Map<String, Object> doProcedure(String procedure, String[] inName, Class[] inType, Object[] inValue, Map<String, Class> out);


    enum QueryType {
        QUERY,
        GREATER,
        GREATERE,
        LOWER,
        LOWERE,
        BEGINLLIKE,
        LIKE,
        BETWEEN,
        IN,
        OR,
        NULL,
        NOTNULL,
        ORMAYBE
    }
}
