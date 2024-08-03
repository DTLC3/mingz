package com.chen.mingz.common.basic.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.chen.mingz.common.basic.dao.Dao;
import com.chen.mingz.common.tree.annotation.TreeLeaf;
import com.chen.mingz.common.utils.Pagination;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.http.util.Asserts;

import java.io.Serializable;
import java.util.*;

/**
 * Created by chenmingzhi on 18/2/27.
 */
@Data
@NoArgsConstructor
@ToString
@ApiModel(value = "BaseParameter", description = "查询条件Model")
public class BaseParameter implements Serializable {

    public static final String SORTED_ASC = "ASC";

    public static final String SORTED_DESC = "DESC";

    private static final long serialVersionUID = 3454734869L;

    @JSONField(serialize = false)
    @TreeLeaf(serialize = false)
    @ApiModelProperty(name = "pagination", value = "分页查询参数", dataType = "Pagination")
    private Pagination pagination;

    @JSONField(serialize = false)
    @TreeLeaf(serialize = false)
    @ApiModelProperty(name = "rows", value = "每一页长度(弃用)", dataType = "Integer", hidden = true)
    private Integer rows;

    @JSONField(serialize = false)
    @TreeLeaf(serialize = false)
    @ApiModelProperty(name = "page", value = "第几页(弃用)", dataType = "Integer", hidden = true)
    private Integer page;

    @JSONField(serialize = false)
    @TreeLeaf(serialize = false)
    @ApiModelProperty(name = "query", value = "相等字段查询", dataType = "Map")
    private Map<String, Object> query = new HashMap<>();

    @JSONField(serialize = false)
    @TreeLeaf(serialize = false)
    @ApiModelProperty(name = "greater", value = "大于字段查询", dataType = "Map")
    private Map<String, Object> greater = new HashMap<>();

    @JSONField(serialize = false)
    @TreeLeaf(serialize = false)
    @ApiModelProperty(name = "greaterE", value = "大于等于查询", dataType = "Map")
    private Map<String, Object> greaterE = new HashMap<>();

    @JSONField(serialize = false)
    @TreeLeaf(serialize = false)
    @ApiModelProperty(name = "lower", value = "小于字段查询", dataType = "Map")
    private Map<String, Object> lower = new HashMap<>();

    @JSONField(serialize = false)
    @TreeLeaf(serialize = false)
    @ApiModelProperty(name = "lowerE", value = "小于等于字段查询", dataType = "Map")
    private Map<String, Object> lowerE = new HashMap<>();

    @JSONField(serialize = false)
    @TreeLeaf(serialize = false)
    @ApiModelProperty(name = "like", value = "LIKE模糊查询", dataType = "Map")
    private Map<String, Object> like = new HashMap<>();

    @JSONField(serialize = false)
    @TreeLeaf(serialize = false)
    @ApiModelProperty(name = "beginLike", value = "字段模糊查询起始", dataType = "Map")
    private Map<String, Object> beginLike = new HashMap<>();

    @JSONField(serialize = false)
    @TreeLeaf(serialize = false)
    @ApiModelProperty(name = "between", value = "BETWEEN区间查询", dataType = "Map")
    private Map<String, Object> between = new HashMap<>();

    @JSONField(serialize = false)
    @TreeLeaf(serialize = false)
    @ApiModelProperty(name = "in", value = "IN字段查询", dataType = "Map")
    private Map<String, Object> in = new HashMap<>();

    @JSONField(serialize = false)
    @TreeLeaf(serialize = false)
    @ApiModelProperty(name = "sortedConditions", value = "排序", dataType = "Map", hidden = true)
    private Map<String, String> sortedConditions;

    @JSONField(serialize = false)
    @TreeLeaf(serialize = false)
    @ApiModelProperty(name = "sql", hidden = true)
    private String sql;

    @JSONField(serialize = false)
    @TreeLeaf(serialize = false)
    @ApiModelProperty(name = "service", value = "查询服务名", dataType = "String", hidden = true)
    private String service;

    @JSONField(serialize = false)
    @TreeLeaf(serialize = false)
    @ApiModelProperty(name = "placeholder", hidden = true)
    private boolean placeholder = false;

    @JSONField(serialize = false)
    @TreeLeaf(serialize = false)
    private Class<?> types;

    @JSONField(serialize = false)
    @TreeLeaf(serialize = false)
    @ApiModelProperty(name = "or", value = "OR或查询", dataType = "List", hidden = true)
    private List<Maybe> or;

    @JSONField(serialize = false)
    @TreeLeaf(serialize = false)
    @ApiModelProperty(name = "orMaybes", hidden = true)
    private List<OrMaybe> orMaybes;

    @JSONField(serialize = false)
    @TreeLeaf(serialize = false)
    @ApiModelProperty(name = "isNotNull", value = "不为空查询", dataType = "List", hidden = true)
    private List<String> isNotNull;

    @JSONField(serialize = false)
    @TreeLeaf(serialize = false)
    @ApiModelProperty(name = "isNull", value = "为空查询", dataType = "Map", hidden = true)
    private List<String> isNull;

    @JSONField(serialize = false)
    @TreeLeaf(serialize = false)
    @ApiModelProperty(name = "include", hidden = true)
    private String include;

    @JSONField(serialize = false)
    @TreeLeaf(serialize = false)
    @ApiModelProperty(name = "remark", hidden = true)
    private String remark;

    @JSONField(serialize = false)
    @TreeLeaf(serialize = false)
    @ApiModelProperty(name = "orderNull", hidden = true)
    private String orderNull;

    @JSONField(serialize = false)
    @TreeLeaf(serialize = false)
    @ApiModelProperty(name = "fieldList", hidden = true)
    private List<String> fieldList;

    @JSONField(serialize = false)
    @TreeLeaf(serialize = false)
    @ApiModelProperty(name = "replaceList", hidden = true)
    private List<List<String>> replaceList;


    @JSONField(serialize = false)
    @TreeLeaf(serialize = false)
    @ApiModelProperty(name = "other", hidden = true)
    private String other;


    @JSONField(serialize = false)
    private void addMap(List<String> list, Map<String, Object> map) {
        list.addAll(map != null ? map.keySet() : Collections.EMPTY_LIST);
    }

    @JSONField(serialize = false)
    @JsonIgnore
    public void queryPlace(String placeWhere) {
        queryPlace(placeWhere, null, null);
    }

    @JSONField(serialize = false)
    @JsonIgnore
    public void queryPlace(String placeWhere, String tag) {
        queryPlace(placeWhere, null, tag);
    }


    @JSONField(serialize = false)
    @JsonIgnore
    public void queryPlace(String placeWhere, List<String> include) {
        queryPlace(placeWhere, include, null);
    }


    @JSONField(serialize = false)
    @JsonIgnore
    public void queryPlace(String placeWhere, List<String> list, String tag) {
        if (list != null && list.size() > 0) {
            if (replaceList == null) {
                replaceList = new ArrayList<>();
            }
            replaceList.add(list);
        }
        if (sql.indexOf(placeWhere) != -1) {
            StringBuilder appendWhere = new StringBuilder();
            appendWhere(appendWhere, Dao.QueryType.QUERY, list, query, tag);
            appendWhere(appendWhere, Dao.QueryType.GREATER, list, greater, tag);
            appendWhere(appendWhere, Dao.QueryType.GREATERE, list, greaterE, tag);
            appendWhere(appendWhere, Dao.QueryType.LOWER, list, lower, tag);
            appendWhere(appendWhere, Dao.QueryType.LOWERE, list, lowerE, tag);
            appendWhere(appendWhere, Dao.QueryType.BEGINLLIKE, list, beginLike, tag);
            appendWhere(appendWhere, Dao.QueryType.LIKE, list, like, tag);
            appendWhere(appendWhere, Dao.QueryType.BETWEEN, list, between, tag);
            appendWhere(appendWhere, Dao.QueryType.IN, list, in, tag);
            appendWhere(appendWhere, Dao.QueryType.OR, list, or, tag);
            appendWhere(appendWhere, Dao.QueryType.ORMAYBE, list, orMaybes, tag);
            appendWhere(appendWhere, Dao.QueryType.NULL, list, isNull, tag);
            appendWhere(appendWhere, Dao.QueryType.NOTNULL, list, isNotNull, tag);
            sql = sql.replace(placeWhere, appendWhere.toString());
        }
    }

    @JSONField(serialize = false)
    @JsonIgnore
    private void appendWhere(StringBuilder sb, Dao.QueryType type, List list, List valueList, String tag) {
        if (valueList != null && valueList.size() > 0) {
            switch (type) {
                case OR:
                    valueList.forEach(entity -> {
                        Maybe maybe = (Maybe) entity;
                        List<String> keys = maybe.getKeys();
                        String value = maybe.getValue();
                        String query = maybe.getQuery();
                        if (value != null && !"".equals(value.trim())) {
                            StringBuilder tmpOr = new StringBuilder("");
                            for (String key : keys) {
                                key = key.replaceAll(" ", "");
                                if (include != null && "exclude".equals(include) && list != null && list.size() > 0) {
                                    if (list.contains(key)) {
                                        continue;
                                    }
                                }
                                if (include != null && "include".equals(include) && list != null && list.size() > 0) {
                                    if (!list.contains(key)) {
                                        continue;
                                    }
                                }
                                if (tag != null && !"".equals(tag.trim())) {
                                    if ("query".equals(query)) {
                                        tmpOr.append(tag + "." + key + "=:or" + key + " or ");
                                    } else if ("like".equals(query)) {
                                        tmpOr.append(tag + "." + key + " like :or" + key + " or ");
                                    }
                                } else {
                                    if ("query".equals(query)) {
                                        tmpOr.append(key + "=:or" + key + " or ");
                                    } else if ("like".equals(query)) {
                                        tmpOr.append(key + " like :or" + key + " or ");
                                    }
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
                    for (Object entity : valueList) {
                        String field = entity.toString();
                        field = field.replaceAll(" ", "");
                        if (list != null && list.size() > 0 && include != null && "exclude".equals(include)) {
                            if (list.contains(field)) {
                                continue;
                            }
                        }
                        if (include != null && "include".equals(include) && list != null && list.size() > 0) {
                            if (!list.contains(field)) {
                                continue;
                            }
                        }
                        if (!"".equals(field.trim())) {
                            if (tag != null && !"".equals(tag.trim())) {
                                sb.append(" and " + tag + "." + field + " is null ");
                            } else {
                                sb.append(" and " + field + " is null ");
                            }
                        }
                    }
                    break;
                case ORMAYBE:
                    for (Object entity : valueList) {
                        OrMaybe orMaybe = (OrMaybe) entity;
                        String key = orMaybe.getKey();
                        String query = orMaybe.getQuery();
                        key = key.replaceAll(" ", "");
                        List<String> values = orMaybe.getValues();
                        if (include != null && "exclude".equals(include) && list != null && list.size() > 0) {
                            if (list.contains(key)) {
                                continue;
                            }
                        }
                        if (include != null && "include".equals(include) && list != null && list.size() > 0) {
                            if (!list.contains(key)) {
                                continue;
                            }
                        }
                        if (values != null && values.size() > 0) {
                            StringBuilder tempOrMay = new StringBuilder("");
                            tempOrMay.append(" (");
                            for (Integer index = 0; index < values.size(); ++index) {
                                String value = values.get(index);
                                if (!"".equals(value.trim())) {
                                    if (tag != null && !"".equals(tag.trim())) {
                                        if ("like".equals(query)) {
                                            tempOrMay.append(tag + "." + key + " like :" + key + index + " or ");
                                        } else if ("query".equals(query)) {
                                            tempOrMay.append(tag + "." + key + " = :" + key + index + " or ");
                                        }
                                    } else {
                                        if ("like".equals(query)) {
                                            tempOrMay.append(key + " like :" + key + index + " or ");
                                        } else if ("query".equals(query)) {
                                            tempOrMay.append(key + " = :" + key + index + " or ");
                                        }
                                    }
                                }
                            }
                            String tmp = tempOrMay.toString();
                            int indexOf = tmp.lastIndexOf("or");
                            tmp = tmp.substring(0, indexOf - 1);
                            tmp += " ) ";
                            sb.append(" and " + tmp);
                        }
                    }
                    break;
                case NOTNULL:
                    for (Object entity : valueList) {
                        String field = entity.toString();
                        field = field.replaceAll(" ", "");
                        if (list != null && list.size() > 0 && include != null && "include".equals(include)) {
                            if (!list.contains(field)) {
                                continue;
                            }
                        }
                        if (list != null && list.size() > 0 && include != null && "exclude".equals(include)) {
                            if (list.contains(field)) {
                                continue;
                            }
                        }
                        if (!"".equals(field.trim())) {
                            if (tag != null && !"".equals(tag.trim())) {
                                sb.append(" and " + tag + "." + field + " is not null ");
                            } else {
                                sb.append(" and " + field + " is not null ");
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @JSONField(serialize = false)
    @JsonIgnore
    private void appendWhere(StringBuilder sb, Dao.QueryType type, List<String> list, Map<String, Object> condition, String tag) {
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
                if (condition != null && condition.containsKey(name)) {
                    if (value != null && !"".equals(value.toString().trim())) {
                        switch (type) {
                            case QUERY:
                                if (tag != null && !"".equals(tag.trim())) {
                                    sb.append(" and " + tag + "." + name + "=:query" + name);
                                } else {
                                    sb.append(" and " + name + " =:query" + name);
                                }
                                break;
                            case GREATER:
                                if (tag != null && !"".equals(tag.trim())) {
                                    sb.append(" and " + tag + "." + name + ">:greater" + name);
                                } else {
                                    sb.append(" and " + name + ">:greater" + name);
                                }
                                break;
                            case GREATERE:
                                if (tag != null && !"".equals(tag.trim())) {
                                    sb.append(" and " + tag + "." + name + ">=:greatere" + name);
                                } else {
                                    sb.append(" and " + name + ">=:greatere" + name);
                                }
                                break;
                            case LOWER:
                                if (tag != null && !"".equals(tag.trim())) {
                                    sb.append(" and " + tag + "." + name + "<:lower" + name);
                                } else {
                                    sb.append(" and " + name + "<:lower" + name);
                                }
                                break;
                            case LOWERE:
                                if (tag != null && !"".equals(tag.trim())) {
                                    sb.append(" and " + tag + "." + name + "<=:lowere" + name);
                                } else {
                                    sb.append(" and " + name + "<=:lowere" + name);
                                }
                                break;
                            case BEGINLLIKE:
                                if (tag != null && !"".equals(tag.trim())) {
                                    sb.append(" and " + tag + "." + name + " like :beginLike" + name);
                                } else {
                                    sb.append(" and " + name + " like :beginLike" + name);
                                }
                                break;
                            case LIKE:
                                if (tag != null && !"".equals(tag.trim())) {
                                    sb.append(" and " + tag + "." + name + " like :like" + name);
                                } else {
                                    sb.append(" and " + name + " like :like" + name);
                                }
                                break;
                            case BETWEEN:
                                if (tag != null && !"".equals(tag.trim())) {
                                    sb.append(" and " + tag + "." + name + " between :between" + name + "first and :between" + name + "second");
                                } else {
                                    sb.append(" and " + name + " between :between" + name + "first and :between" + name + "second");
                                }
                                break;
                            case IN:
                                if (tag != null && !"".equals(tag.trim())) {
                                    sb.append(" and " + tag + "." + name + " in(:in" + name + ")");
                                } else {
                                    sb.append(" and " + name + " in(:in" + name + ")");
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
    }

    @JSONField(serialize = false)
    @JsonIgnore
    public void verify(String[] must) {
        List<String> already = new ArrayList<>();
        addMap(already, query);
        addMap(already, greater);
        addMap(already, greaterE);
        addMap(already, lower);
        addMap(already, lowerE);
        addMap(already, beginLike);
        addMap(already, like);
        addMap(already, between);
        addMap(already, in);
        for (String m : must) {
            if (!already.contains(m)) {
                throw new RuntimeException(m + "为必传参数！！！");
            }
        }
    }

    @JSONField(serialize = false)
    @JsonIgnore
    public void verify(Map<String, Object> map, String must) {
        verify(map, new String[]{must});
    }

    @JSONField(serialize = false)
    @JsonIgnore
    public void verify(Map<String, Object> map, String[] must) {
        Set<String> set = map.keySet();
        for (String m : must) {
            if (!set.contains(m)) {
                throw new RuntimeException(m + "为必传参数！！！");
            }
        }
    }

    @JSONField(serialize = false)
    @JsonIgnore
    public void verify(Map<String, Object> map) {
        Asserts.notNull(map, "不能为空");
        if (map.size() == 0) {
            throw new RuntimeException("大小不能为0");
        }
    }


    @JSONField(serialize = false)
    @JsonIgnore
    public void verify(List list) {
        Asserts.notNull(list, "list 不能为空");
        if (list.size() == 0) {
            throw new RuntimeException("大小不能为0");
        }
    }

    @JSONField(serialize = false)
    @JsonIgnore
    public boolean verifyBlank(Object object) {
        if (object != null && !"".equals(object.toString().trim())) {
            return true;
        } else {
            return false;
        }
    }

    @JSONField(serialize = false)
    @JsonIgnore
    public boolean verifyBlank(Map<String, Object> map, String key) {
        if (map != null && map.size() > 0 && map.containsKey(key)) {
            return true;
        } else {
            return false;
        }
    }
}
