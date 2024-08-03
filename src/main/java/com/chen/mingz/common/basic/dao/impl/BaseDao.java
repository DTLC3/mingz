package com.chen.mingz.common.basic.dao.impl;

import com.chen.mingz.common.basic.dao.Dao;
import com.chen.mingz.common.basic.model.*;
import com.chen.mingz.common.utils.Pagination;
import org.apache.http.util.Asserts;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenmingzhi on 18/2/27.
 */

@Repository("baseDao")
@Transactional
public class BaseDao implements Dao {


    @PersistenceContext
    protected EntityManager entityManager;


    private void appendWhere(StringBuilder sb, QueryType type, List list) {
        if (list != null && list.size() > 0) {
            switch (type) {
                case OR:
                    list.forEach(entity -> {
                        Maybe maybe = (Maybe) entity;
                        List<String> keys = maybe.getKeys();
                        String value = maybe.getValue();
                        String query = maybe.getQuery();
                        if (value != null && !"".equals(value.trim())) {
                            StringBuilder tmpOr = new StringBuilder("");
                            for (String key : keys) {
                                key = key.replaceAll(" ", "");
                                if ("query".equals(query)) {
                                    tmpOr.append(key + "=:or" + key + " or ");
                                } else if ("like".equals(query)) {
                                    tmpOr.append(key + " like :or" + key + " or ");
                                }
                            }
                            String tmp = tmpOr.toString();
                            if (tmpOr.length() > 0) {
                                int index = tmp.lastIndexOf("or");
                                tmp = tmp.substring(0, index - 1);
                            }
                            sb.append(" and (" + tmp + ") ");
                        }
                    });
                    break;
                case NULL:
                    list.forEach(entity -> {
                        //去除所有相关空格，防注入问题
                        String field = entity.toString();
                        field = field.replaceAll(" ", "");
                        if (!"".equals(field.trim())) {
                            sb.append(" and " + field + " is null ");
                        }
                    });
                    break;
                case NOTNULL:
                    list.forEach(entity -> {
                        //去除所有相关空格，防注入问题
                        String field = entity.toString();
                        field = field.replaceAll(" ", "");
                        if (!"".equals(field.trim())) {
                            sb.append(" and " + field + " is not null ");
                        }
                    });
                    break;
                case ORMAYBE:
                    list.forEach(entity -> {
                        OrMaybe orMaybe = (OrMaybe) entity;
                        String key = orMaybe.getKey();
                        List<String> values = orMaybe.getValues();
                        key = key.replaceAll(" ", "");
                        String query = orMaybe.getQuery();
                        if (values != null && values.size() > 0) {
                            StringBuilder tmpOr = new StringBuilder("");
                            tmpOr.append(" (");
                            for (Integer index = 0; index < values.size(); ++index) {
                                String value = values.get(index);
                                if (!"".equals(value.trim())) {
                                    if ("query".equals(query)) {
                                        tmpOr.append(key + " = :" + key + index + " or ");
                                    } else if ("like".equals(query)) {
                                        tmpOr.append(key + " like :" + key + index + " or ");
                                    }
                                }
                            }
                            String tmp = tmpOr.toString();
                            int indexOf = tmp.lastIndexOf("or");
                            tmp = tmp.substring(0, indexOf - 1);
                            tmp += " ) ";
                            sb.append(" and " + tmp);
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    }

    private void appendWhere(StringBuilder sb, QueryType type, Map<String, Object> condition) {
        if (condition != null && condition.size() > 0) {
            for (Map.Entry<String, Object> entry : condition.entrySet()) {
                String name = entry.getKey();
                Object value = entry.getValue();
                name = name.replaceAll(" ", "");
                if (value != null && !"".equals(value.toString().trim())) {
                    switch (type) {
                        case QUERY:
                            sb.append(" and o." + name + "=:" + name);
                            break;
                        case GREATER:
                            sb.append(" and o." + name + ">:" + name);
                            break;
                        case GREATERE:
                            sb.append(" and o." + name + ">=:" + name);
                            break;
                        case LOWER:
                            sb.append(" and o." + name + "<:" + name);
                            break;
                        case LOWERE:
                            sb.append(" and o." + name + "<=:" + name);
                            break;
                        case BEGINLLIKE:
                        case LIKE:
                            sb.append(" and o." + name + " like :" + name);
                            break;
                        case BETWEEN:
                            sb.append(" and o." + name + " between :" + name + "first and :" + name + "second");
                            break;
                        case IN:
                            List L = (List) value;
                            if (L != null && L.size() > 0) {
                                sb.append(" and o." + name + " in(:" + name + ")");
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }


    private void appendOrder(StringBuilder sb, Map<String, String> sortedCondition, String orderNull) {
        if (sortedCondition != null && sortedCondition.size() > 0) {
            sb.append(" order by ");
            for (Map.Entry<String, String> entry : sortedCondition.entrySet()) {
                sb.append(entry.getKey() + " " + entry.getValue() + ",");
            }
            sb.deleteCharAt(sb.length() - 1);
            if (orderNull != null && !"".equals(orderNull)) {
                sb.append(orderNull);
            }
        }
    }

    private void setParameter(javax.persistence.Query query,
                              QueryType type, List list, List<String> fieldList, String include) {
        if (list != null && list.size() > 0) {
            switch (type) {
                case OR:
                    for (Object entity : list) {
                        Maybe maybe = (Maybe) entity;
                        List<String> keys = maybe.getKeys();
                        String value = maybe.getValue();
                        String inquire = maybe.getQuery();
                        if (value != null && !"".equals(value.trim())) {
                            for (String key : keys) {
                                if (include != null && "exclude".equals(include) && fieldList != null && fieldList.size() > 0) {
                                    if (fieldList.contains(key)) {
                                        continue;
                                    }
                                }
                                if (include != null && "include".equals(include) && fieldList != null && fieldList.size() > 0) {
                                    if (!fieldList.contains(key)) {
                                        continue;
                                    }
                                }
                                if ("query".equals(inquire)) {
                                    query.setParameter("or" + key, value);
                                } else if ("like".equals(inquire)) {
                                    query.setParameter("or" + key, "%" + value + "%");
                                }
                            }
                        }
                    }
                    break;
                case NULL:
                    break;
                case NOTNULL:
                    break;
                case ORMAYBE:
                    for (Object entity : list) {
                        OrMaybe orMaybe = (OrMaybe) entity;
                        String key = orMaybe.getKey();
                        List<String> values = orMaybe.getValues();
                        key = key.replaceAll(" ", "");
                        if (include != null && "exclude".equals(include) && fieldList != null && fieldList.size() > 0) {
                            if (fieldList.contains(key)) {
                                continue;
                            }
                        }
                        if (include != null && "include".equals(include) && fieldList != null && fieldList.size() > 0) {
                            if (!fieldList.contains(key)) {
                                continue;
                            }
                        }
                        String inquery = orMaybe.getQuery();
                        if (values != null && values.size() > 0) {
                            for (Integer index = 0; index < values.size(); ++index) {
                                String value = values.get(index);
                                if (!"".equals(value.trim())) {
                                    if ("query".equals(inquery)) {
                                        query.setParameter(key + index, value);
                                    } else if ("like".equals(inquery)) {
                                        query.setParameter(key + index, "%" + value + "%");
                                    }
                                }
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void setParameter(javax.persistence.Query query, QueryType type,
                              Map<String, Object> condition,
                              List<String> list,
                              String include,
                              boolean placeholder) {
        if (condition != null && condition.size() > 0) {
            for (Map.Entry<String, Object> entry : condition.entrySet()) {
                String name = entry.getKey();
                Object value = entry.getValue();
                name = name.replaceAll(" ", "");
                if (include != null && "include".equals(include) && list != null && list.size() > 0) {
                    if (!list.contains(name)) {
                        continue;
                    }
                }
                if (include != null && "exclude".equals(include) && list != null && list.size() > 0) {
                    if (list.contains(name)) {
                        continue;
                    }
                }
                if (value != null && !"".equals(value.toString().trim())) {
                    switch (type) {
                        case BEGINLLIKE:
                            if (placeholder) {
                                query.setParameter("beginLike" + name, value + "%");
                            } else {
                                query.setParameter(name, value + "%");
                            }
                            break;
                        case LIKE:
                            if (placeholder) {
                                query.setParameter("like" + name, "%" + value + "%");
                            } else {
                                query.setParameter(name, "%" + value + "%");
                            }
                            break;
                        case BETWEEN:
                            Map<String, Object> temp = (Map<String, Object>) value;
                            Between between = new Between(temp.get("first").toString(), temp.get("second").toString());
                            if (placeholder) {
                                query.setParameter("between" + name + "first", between.getFirst());
                                query.setParameter("between" + name + "second", between.getSecond());
                            } else {
                                query.setParameter(name + "first", between.getFirst());
                                query.setParameter(name + "second", between.getSecond());
                            }
                            break;
                        case IN:
                            List L = (List) value;
                            if (L != null && L.size() > 0) {
                                if (placeholder) {
                                    query.setParameter("in" + name, value);
                                } else {
                                    query.setParameter(name, value);
                                }
                            }
                            break;
                        case QUERY:
                            if (placeholder) {
                                query.setParameter("query" + name, value);
                            } else {
                                query.setParameter(name, value);
                            }
                            break;
                        case GREATER:
                            if (placeholder) {
                                query.setParameter("greater" + name, value);
                            } else {
                                query.setParameter(name, value);
                            }
                            break;
                        case GREATERE:
                            if (placeholder) {
                                query.setParameter("greatere" + name, value);
                            } else {
                                query.setParameter(name, value);
                            }
                            break;
                        case LOWER:
                            if (placeholder) {
                                query.setParameter("lower" + name, value);
                            } else {
                                query.setParameter(name, value);
                            }
                            break;
                        case LOWERE:
                            if (placeholder) {
                                query.setParameter("lowere" + name, value);
                            } else {
                                query.setParameter(name, value);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }


    private void appendWhere(StringBuilder sb, BaseParameter parameter) {
        appendWhere(sb, QueryType.QUERY, parameter.getQuery());
        appendWhere(sb, QueryType.GREATER, parameter.getGreater());
        appendWhere(sb, QueryType.GREATERE, parameter.getGreaterE());
        appendWhere(sb, QueryType.LOWER, parameter.getLower());
        appendWhere(sb, QueryType.LOWERE, parameter.getLowerE());
        appendWhere(sb, QueryType.BEGINLLIKE, parameter.getBeginLike());
        appendWhere(sb, QueryType.LIKE, parameter.getLike());
        appendWhere(sb, QueryType.BETWEEN, parameter.getBetween());
        appendWhere(sb, QueryType.IN, parameter.getIn());
        appendWhere(sb, QueryType.OR, parameter.getOr());
        appendWhere(sb, QueryType.NULL, parameter.getIsNull());
        appendWhere(sb, QueryType.NOTNULL, parameter.getIsNotNull());
        appendWhere(sb, QueryType.ORMAYBE, parameter.getOrMaybes());
    }

    private void setParameter(Query query, BaseParameter parameter) {
        boolean placeholder = parameter.isPlaceholder();
        setParameter(query, QueryType.QUERY, parameter.getQuery(), parameter.getFieldList(), parameter.getInclude(), placeholder);
        setParameter(query, QueryType.GREATER, parameter.getGreater(), parameter.getFieldList(), parameter.getInclude(), placeholder);
        setParameter(query, QueryType.GREATERE, parameter.getGreaterE(), parameter.getFieldList(), parameter.getInclude(), placeholder);
        setParameter(query, QueryType.LOWER, parameter.getLower(), parameter.getFieldList(), parameter.getInclude(), placeholder);
        setParameter(query, QueryType.LOWERE, parameter.getLowerE(), parameter.getFieldList(), parameter.getInclude(), placeholder);
        setParameter(query, QueryType.BEGINLLIKE, parameter.getBeginLike(), parameter.getFieldList(), parameter.getInclude(), placeholder);
        setParameter(query, QueryType.LIKE, parameter.getLike(), parameter.getFieldList(), parameter.getInclude(), placeholder);
        setParameter(query, QueryType.BETWEEN, parameter.getBetween(), parameter.getFieldList(), parameter.getInclude(), placeholder);
        setParameter(query, QueryType.IN, parameter.getIn(), parameter.getFieldList(), parameter.getInclude(), placeholder);
        setParameter(query, QueryType.OR, parameter.getOr(), parameter.getFieldList(), parameter.getInclude());
        setParameter(query, QueryType.NULL, parameter.getIsNull(), parameter.getFieldList(), parameter.getInclude());
        setParameter(query, QueryType.NOTNULL, parameter.getIsNotNull(), parameter.getFieldList(), parameter.getInclude());
        setParameter(query, QueryType.ORMAYBE, parameter.getOrMaybes(), parameter.getFieldList(), parameter.getInclude());
    }

    private void setParameter(Query query, BaseParameter parameter, List<String> list, String include) {
        boolean placeholder = parameter.isPlaceholder();
        setParameter(query, QueryType.QUERY, parameter.getQuery(), list, include, placeholder);
        setParameter(query, QueryType.GREATER, parameter.getGreater(), list, include, placeholder);
        setParameter(query, QueryType.GREATERE, parameter.getGreaterE(), list, include, placeholder);
        setParameter(query, QueryType.LOWER, parameter.getLower(), list, include, placeholder);
        setParameter(query, QueryType.LOWERE, parameter.getLowerE(), list, include, placeholder);
        setParameter(query, QueryType.BEGINLLIKE, parameter.getBeginLike(), list, include, placeholder);
        setParameter(query, QueryType.LIKE, parameter.getLike(), list, include, placeholder);
        setParameter(query, QueryType.BETWEEN, parameter.getBetween(), list, include, placeholder);
        setParameter(query, QueryType.IN, parameter.getIn(), list, include, placeholder);
        setParameter(query, QueryType.OR, parameter.getOr(), list, include);
        setParameter(query, QueryType.NULL, parameter.getIsNull(), list, include);
        setParameter(query, QueryType.NOTNULL, parameter.getIsNotNull(), list, include);
        setParameter(query, QueryType.ORMAYBE, parameter.getOrMaybes(), list, include);
    }

    private void orderBy(BaseParameter parameter) {
        if (parameter.getSortedConditions() != null && parameter.getSortedConditions().size() > 0) {
            StringBuilder orderBy = new StringBuilder(" order by ");
            parameter.getSortedConditions().forEach((key, value) -> {
                orderBy.append(key + " " + value + ",");
            });
            orderBy.deleteCharAt(orderBy.length() - 1);
            parameter.setSql(parameter.getSql().replace("order_by_description", orderBy.toString()));
        } else {
            parameter.setSql(parameter.getSql().replace("order_by_description", ""));
        }
    }

    @Override
    public QueryResult queryReplace(BaseParameter parameter) {
        Asserts.notNull(parameter, "传入参数parameter不能为null");
        Asserts.notBlank(parameter.getSql(), "BaseParameter的SQL属性值不能为空");
        Assert.isTrue(parameter.isPlaceholder(), "placeholder 必须为true");
        QueryResult queryResult = new QueryResult();
        orderBy(parameter);
        StringBuilder sb = new StringBuilder();
        sb.append(parameter.getSql());
        Class clazz = parameter.getTypes();
        String sql = parameter.getSql();
        Query querySql;
        if (clazz == null) {
            querySql = entityManager.createNativeQuery(sb.toString());
            querySql.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        } else {
            querySql = entityManager.createNativeQuery(sb.toString(), clazz);
        }
        List<List<String>> replaceList = parameter.getReplaceList();
        if (replaceList != null && replaceList.size() > 0) {
            for (List<String> list : replaceList) {
                setParameter(querySql, parameter, list, parameter.getInclude());
            }
        }
        Pagination pagination = parameter.getPagination();
        if (pagination != null) {
            StringBuilder totalSB = new StringBuilder();
            sql = sql.replace("order_by_description", "");
            totalSB.append("select count(*) from (" + sql + ") o where 1=1 ");
            Query queryTotalSql = entityManager.createNativeQuery(totalSB.toString());
            setParameter(queryTotalSql, parameter);
            List totalList = queryTotalSql.getResultList();
            if (totalList != null && totalList.size() > 0 && Long.valueOf(totalList.get(0).toString()) > 0) {
                pagination.setStartLength(); //为了兼容已有接口
                querySql.setFirstResult(pagination.getStart());
                querySql.setMaxResults(pagination.getLength());
                queryResult.setTotal(Long.valueOf(totalList.get(0).toString()));
                queryResult.setRows(querySql.getResultList());
            }
            return queryResult;
        }
        queryResult.setRows(querySql.getResultList());
        return queryResult;
    }

    private QueryResult queryPage(BaseParameter parameter, boolean isPage) {
        Asserts.notNull(parameter, "传入参数parameter不能为null");
        Asserts.notBlank(parameter.getSql(), "BaseParameter的SQL属性值不能为空");
        QueryResult queryResult = new QueryResult();
        boolean placeholder = parameter.isPlaceholder();
        StringBuilder sb = new StringBuilder();
        String sql = parameter.getSql();
        if (placeholder) {
            orderBy(parameter);
            sb.append(parameter.getSql());
        } else {
            sb.append("select o.* from (" + sql + ") o where 1=1 ");
            appendWhere(sb, parameter);
            appendOrder(sb, parameter.getSortedConditions(), parameter.getOrderNull());
        }
        Query querySql;
        Class clazz = parameter.getTypes();
        if (clazz == null) {
            querySql = entityManager.createNativeQuery(sb.toString());
            querySql.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        } else {
            querySql = entityManager.createNativeQuery(sb.toString(), clazz);
        }
        setParameter(querySql, parameter);
        if (isPage) {
            StringBuilder totalSB = new StringBuilder();
            if (placeholder) {
                sql = sql.replace("order_by_description", "");
                totalSB.append("select count(*) from (" + sql + ") o where 1=1 ");
            } else {
                totalSB.append("select count(*) from (" + sql + ") o where 1=1 ");
                appendWhere(totalSB, parameter);
            }
            Query queryTotalSql = entityManager.createNativeQuery(totalSB.toString());
            setParameter(queryTotalSql, parameter);
            List totalList = queryTotalSql.getResultList();
            Pagination pagination = parameter.getPagination();
            if (totalList != null && totalList.size() > 0 && Long.valueOf(totalList.get(0).toString()) > 0) {
                pagination.setStartLength();
                querySql.setFirstResult(pagination.getStart());
                querySql.setMaxResults(pagination.getLength());
                queryResult.setTotal(Long.valueOf(totalList.get(0).toString()));
                queryResult.setRows(querySql.getResultList());
            }
            return queryResult;
        }
        Pagination pagination = parameter.getPagination();
        if (pagination != null) {
            pagination.setStartLength();
            querySql.setFirstResult(pagination.getStart());
            querySql.setMaxResults(pagination.getLength());
        }
        queryResult.setRows(querySql.getResultList());
        return queryResult;
    }

    @Override
    public QueryResult queryPage(BaseParameter parameter) {
        if (parameter.getPagination() == null) {
            parameter.setPagination(new Pagination());
        }
        return this.queryPage(parameter, true);
    }

    private void setCriteria(BaseParameter parameter, List<Predicate> predicates,
                             CriteriaBuilder builder,
                             javax.persistence.criteria.Root root) {
        Map<String, Object> query = parameter.getQuery();
        Map<String, Object> greater = parameter.getGreater();
        Map<String, Object> lower = parameter.getLower();
        Map<String, Object> like = parameter.getLike();
        Map<String, Object> between = parameter.getBetween();
        Map<String, Object> in = parameter.getIn();
        List<Maybe> or = parameter.getOr();
        if (query != null && query.size() > 0) {
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                if (entry.getValue() != null && !"".equals(entry.getValue().toString().trim())) {
                    predicates.add(builder.equal(root.get(entry.getKey()), entry.getValue()));
                }
            }
        }
        if (greater != null && greater.size() > 0) {
            for (Map.Entry<String, Object> entry : greater.entrySet()) {
                if (entry.getValue() != null && !"".equals(entry.getValue().toString().trim())) {
                    predicates.add(builder.ge(root.get(entry.getKey()), (Number) entry.getValue()));
                }
            }
        }
        if (lower != null && lower.size() > 0) {
            for (Map.Entry<String, Object> entry : lower.entrySet()) {
                if (entry.getValue() != null) {
                    if (entry.getValue() != null && !"".equals(entry.getValue().toString().trim())) {
                        predicates.add(builder.le(root.get(entry.getKey()), (Number) entry.getValue()));
                    }
                }
            }
        }
        if (like != null && like.size() > 0) {
            for (Map.Entry<String, Object> entry : like.entrySet()) {
                String name = entry.getKey();
                Object value = entry.getValue();
                if (value != null && value.getClass() == String.class) {
                    predicates.add(builder.like(root.get(name), "%" + value.toString() + "%"));
                } else if (value != null) {
                    predicates.add(builder.like(root.get(name).as(String.class), "%" + value.toString() + "%"));
                }
            }
        }
        if (between != null && between.size() > 0) {
            for (Map.Entry<String, Object> entry : between.entrySet()) {
                String name = entry.getKey();
                Map<String, Object> temp = (Map<String, Object>) entry.getValue();
                Object first = temp.get("first");
                Object second = temp.get("second");
                Object type = temp.get("type");
                if (type != null && !"".equals(type.toString().trim())) {
                    if ("date".equals(type.toString().trim())) {
                        predicates.add(builder.between(root.get(name), (java.util.Date) first, (java.util.Date) second));
                    }
                } else {
                    predicates.add(builder.between(root.get(name), first.toString(), second.toString()));
                }
            }
        }
        if (in != null && in.size() > 0) {
            for (Map.Entry<String, Object> entry : in.entrySet()) {
                CriteriaBuilder.In inBuilder = builder.in(root.get(entry.getKey()));
                inBuilder.value(entry.getValue());
                predicates.add(inBuilder);
            }
        }
        if (or != null && or.size() > 0) {
            for (Maybe maybe : or) {
                List<String> keys = maybe.getKeys();
                String value = maybe.getValue();
                List<Predicate> orPredicate = new ArrayList<>();
                for (String key : keys) {
                    orPredicate.add(builder.equal(root.get(key), value));
                }
                if (orPredicate.size() > 0) {
                    Predicate[] predicateArray = new Predicate[orPredicate.size()];
                    orPredicate.toArray(predicateArray);
                    predicates.add(builder.or(predicateArray));
                }
            }
        }

    }

    @Override
    public QueryResult pagination(BaseParameter parameter) {
        QueryResult queryResult = new QueryResult();
        if (parameter == null) {
            return queryResult;
        }
        Class entityClass = parameter.getTypes();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = builder.createQuery(entityClass);
        javax.persistence.criteria.Root root = criteriaQuery.from(entityClass);
        CriteriaQuery<Long> totalCriteria = builder.createQuery(Long.class);
        Root totalRoot = totalCriteria.from(entityClass);
        List<Predicate> totalPredicates = new ArrayList<>();
        List<Predicate> predicates = new ArrayList<>();
        try {
            setCriteria(parameter, totalPredicates, builder, totalRoot);
            setCriteria(parameter, predicates, builder, root);
            Map<String, String> sortedConditions = parameter.getSortedConditions();
            if (sortedConditions != null && sortedConditions.size() > 0) {
                List<Order> orderList = new ArrayList<>();
                for (Map.Entry<String, String> entry : sortedConditions.entrySet()) {
                    String name = entry.getKey();
                    String value = entry.getValue();
                    if (BaseParameter.SORTED_DESC.equalsIgnoreCase(value)) {
                        orderList.add(builder.desc(root.get(name)));
                    } else if (BaseParameter.SORTED_ASC.equalsIgnoreCase(value)) {
                        orderList.add(builder.asc(root.get(name)));
                    }
                }
                criteriaQuery.orderBy(orderList);
            }
            totalCriteria.select(builder.count(totalRoot));
            if (predicates.size() > 0) {
                Predicate[] array = new Predicate[predicates.size()];
                Predicate[] totalArray = new Predicate[totalPredicates.size()];
                for (int i = 0, length = predicates.size(); i < length; ++i) {
                    array[i] = predicates.get(i);
                    totalArray[i] = totalPredicates.get(i);
                }
                criteriaQuery.where(array);
                totalCriteria.where(totalArray);
            }
            Query totalQuery = entityManager.createQuery(totalCriteria);
            List totalList = totalQuery.getResultList();
            if (totalList != null && totalList.size() > 0 && (!totalList.get(0).toString().equals("0"))) {
                Pagination pagination = parameter.getPagination();
                pagination.setStartLength();
                if (totalList != null && totalList.size() > 0 && Long.valueOf(totalList.get(0).toString()) > 0) {
                    Query resultQuery = entityManager.createQuery(criteriaQuery);
                    resultQuery.setFirstResult(pagination.getStart());
                    resultQuery.setMaxResults(pagination.getLength());
                    queryResult.setRows(resultQuery.getResultList());
                    queryResult.setTotal(Long.valueOf(totalList.get(0).toString()));
                }
            } else {
                queryResult.setTotal(0L);
                queryResult.setRows(new ArrayList<>());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return queryResult;
    }


    @Override
    public Long queryTotal(BaseParameter parameter) {
        String sql = parameter.getSql();
        StringBuilder totalSB = new StringBuilder();
        if (parameter.isPlaceholder()) {
            sql = sql.replace("order_by_description", "");
            totalSB.append("select count(*) from (" + sql + ") o where 1=1 ");
        } else {
            totalSB.append("select count(*) from (" + sql + ") o where 1=1 ");
            appendWhere(totalSB, parameter);
        }
        Query queryTotalSql = entityManager.createNativeQuery(totalSB.toString());
        setParameter(queryTotalSql, parameter);
        List totalList = queryTotalSql.getResultList();
        if (totalList != null && totalList.size() > 0 && Long.valueOf(totalList.get(0).toString()) > 0) {
            return Long.valueOf(totalList.get(0).toString());
        }
        return 0L;
    }

    @Override
    public int nativeQuery(String sql) {
        Query query = entityManager.createNativeQuery(sql);
        return query.executeUpdate();
    }

    @Override
    public List query(BaseParameter parameter) {
        return this.queryMap(parameter);
    }

    @Override
    public List queryMap(BaseParameter parameter) {
        QueryResult queryResult = queryPage(parameter, false);
        return queryResult.getRows();
    }

    @Override
    public List queryMap(String sql) {
        BaseParameter parameter = new BaseParameter();
        parameter.setSql(sql);
        return this.queryMap(parameter);
    }

    @Override
    public <T> List<T> queryBean(BaseParameter parameter) {
        QueryResult queryResult = queryPage(parameter, false);
        return queryResult.getRows();
    }

    @Override
    public <T> T queryModel(BaseParameter parameter) {
        List<T> list = queryBean(parameter);
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void procedure(String procedure) {
        Query query = entityManager.createNativeQuery(procedure);
        query.executeUpdate();
    }

    @Override
    public List doProcedure(String procedure) {
        StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery(procedure);
        procedureQuery.registerStoredProcedureParameter(1, Class.class, ParameterMode.REF_CURSOR);
        procedureQuery.execute();
        return procedureQuery.getResultList();
    }

    /**
     * 例如: p_set_qzrq00(v_qsrq00 in char,v_jzrq00 in char)
     *
     * @param procedure 存储过程名字: p_set_qzrq00
     * @param inName    入参名: new String[]{"v_qsrq00","v_jzrq00"}
     * @param inType    入参类型: new Class[]{String.class, String.class}
     * @param inValue   入参值: new Object[]{"20190101","20190201"}
     * @param out       返回值： Map -> key为返回值名， value 为返回值类型
     * @return
     */
    @Override
    public Map<String, Object> doProcedure(String procedure, String[] inName, Class[] inType, Object[] inValue, Map<String, Class> out) {
        Map<String, Object> result = new HashMap<>();
        StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery(procedure);
        Integer length = inName.length;
        for (int i = 0; i < length; ++i) {
            procedureQuery.registerStoredProcedureParameter(inName[i], inType[i], javax.persistence.ParameterMode.IN);
            procedureQuery.setParameter(inName[i], inValue[i]);
        }

        for (Map.Entry<String, Class> entry : out.entrySet()) {
            procedureQuery.registerStoredProcedureParameter(entry.getKey(), entry.getValue(), ParameterMode.OUT);
        }
        procedureQuery.execute();
        for (String key : out.keySet()) {
            result.put(key, procedureQuery.getOutputParameterValue(key));
        }
        return result;
    }

}