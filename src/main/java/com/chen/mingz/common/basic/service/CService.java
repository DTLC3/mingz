package com.chen.mingz.common.basic.service;


import com.chen.mingz.common.basic.model.BaseParameter;
import com.chen.mingz.common.basic.model.QueryResult;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * Created by chenmingzhi on 18/2/28.
 */
@Transactional
public interface CService {

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

}
