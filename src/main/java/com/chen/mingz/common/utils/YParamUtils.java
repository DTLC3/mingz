package com.chen.mingz.common.utils;


import com.chen.mingz.common.soap.input.InputParaList;
import com.chen.mingz.common.soap.input.InputRow;
import com.chen.mingz.common.soap.input.InputXml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class YParamUtils {
    //修改InputXml中head属性,并删除掉属性名在removes中的属性
    public static void changeHead(InputXml input, String[] names,
                                  String[] values, String[] removes) {
        addHead(input, names, values);
        removeHead(input, removes);
    }

    //修改InputXml中head属性
    public static void changeHead(InputXml input, String name, String value) {
        addHead(input, new String[]{name}, new String[]{value});
    }

    //修改InputXml中head属性
    public static void changeHead(InputXml input, String[] names, String[] values) {
        addHead(input, names, values);
    }

    //删除InputXml中head属性名在names中的数据
    private static void removeHead(InputXml input, String[] names) {
        Map<String, String> headMap = input.getHeadMap();
        for (int index = 0, length = names.length; index < length; ++index) {
            headMap.remove(names[index]);
        }
    }

    //添加InputXml中head属性
    private static void addHead(InputXml input, String[] names, String[] values) {
        assert names.length == values.length : "names和values长度需相同";
        Map<String, String> headMap = input.getHeadMap();
        for (int index = 0, length = names.length; index < length; ++index) {
            headMap.put(names[index], values[index]);
        }
    }
    //====================================================================

    public static void changeBody(InputXml input, String name, String value) {
        addBody(input, new String[]{name}, new String[]{value});
    }

    public static void changeBody(InputXml input, String[] names, String[] values) {
        addBody(input, names, values);
    }

    public static void changeBody(InputXml input, String[] names,
                                  String[] values, String[] removes) {
        removeBody(input, removes);
        addBody(input, names, values);
    }

    private static void addBody(InputXml input, String[] names, String[] values) {
        assert names.length == values.length : "names和values长度需相同";
        Map<String, String> paraMap = input.getBody().getParaMap();
        for (int index = 0, length = names.length; index < length; ++index) {
            paraMap.put(names[index], values[index]);
        }
    }

    public static void removeBody(InputXml input, String name) {
        removeBody(input, new String[]{name});
    }

    public static void removeBody(InputXml input, String[] names) {
        Map<String, String> paraMap = input.getBody().getParaMap();
        for (int index = 0, length = names.length; index < length; ++index) {
            paraMap.remove(names[index]);
        }
    }

    //====================================================================
    public static void changeParaList(InputXml input, String key,
                                      String[] names, String[] values) {
        Map<String, InputParaList> listMap = input.getBody().getListMap();
        InputParaList paraList = listMap.get(key);
        if (paraList != null) {
            List<InputRow> list = paraList.getList();
            for (InputRow row : list) {
                Map<String, String> keyValue = row.getValues();
                for (int index = 0, length = names.length; index < length; ++index) {
                    keyValue.put(names[index], values[index]);
                }
            }
        }
    }

    public static void removeParaList(InputXml input, String key, String[] names) {
        Map<String, InputParaList> listMap = input.getBody().getListMap();
        InputParaList paraList = listMap.get(key);
        if (paraList != null) {
            List<InputRow> list = paraList.getList();
            for (InputRow row : list) {
                Map<String, String> keyValue = row.getValues();
                for (int index = 0, length = names.length; index < length; ++index) {
                    keyValue.remove(names[index]);
                }
            }
        }
    }

    //获取ParaList中的数据并转为对应model
    public static <T> List<T> getParaList(InputXml input, String key, Class<?> clazz) {
        Map<String, InputParaList> listMap = input.getBody().getListMap();
        InputParaList paraList = listMap.get(key);
        List<T> result = new ArrayList<T>();
        if (paraList != null) {
            List<InputRow> list = paraList.getList();
            for (InputRow row : list) {
                T object = CBeanUtils.map2bean(row.getValues(), clazz);
                result.add(object);
            }

        }
        return result;
    }
    //====================================================================


}
