package com.chen.mingz.common.utils;

import org.apache.commons.beanutils.*;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by chenmingzhi on 18/2/27.
 */
public class CBeanUtils {

    private static final Log log = LogFactory.getLog(CBeanUtils.class);

    private static Set<String> excludeProperties = new HashSet<String>();

    static {
        ConvertUtils.register(new BigDecimalConverter(BigDecimal.ZERO), java.math.BigDecimal.class);
        ConvertUtils.register(new DateConverter(null), Date.class);
    }

    static {
        excludeProperties.add("class");
    }

    public static String[] getFields(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        String[] names = new String[fields.length];
        for (int index = 0, length = fields.length; index < length; ++index) {
            names[index] = fields[index].getName();
        }
        return names;
    }

    public static String[] getFields(Class<?> clazz, List<String> exclude) {
        List<String> list = new ArrayList<String>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            if (!exclude.contains(name)) {
                list.add(field.getName());
            }
        }
        return (String[]) list.toArray();
    }

    public static void map2Bean() {

    }

    public static <T> T map2bean(Map<String, ? extends Object> map,
                                 Class<?> clazz) {
        if (map == null) {
            return null;
        }
        Object object = null;
        try {
            object = clazz.newInstance();
            org.apache.commons.beanutils.BeanUtils.populate(object, map);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return (T) object;
    }

    public static Map<? extends Object, ? extends Object> bean2Map(Object object) {
        try {
            return new org.apache.commons.
                    beanutils.BeanMap(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 对象转Map
    public static Map<String, Object> describe(Object bean) throws IllegalAccessException,
            InvocationTargetException,
            NoSuchMethodException {
        return describe(bean, null);
    }

    //只转包含指定属性的值
    public static Map<String, Object> describe(Object bean, List<String> include)
            throws IllegalAccessException,
            InvocationTargetException,
            NoSuchMethodException {
        if (bean == null) {
            return (new HashMap<String, Object>());
        }
        Map<String, Object> description = new HashMap<String, Object>();
        if (bean instanceof DynaBean) {
            DynaProperty[] descriptors = ((DynaBean) bean).getDynaClass().getDynaProperties();
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                if (include == null) {
                    description.put(name, org.apache.commons.beanutils.BeanUtils.getProperty(bean, name));
                } else {
                    if (include.contains(name)) {
                        description.put(name, org.apache.commons.beanutils.BeanUtils.getProperty(bean, name));
                    }
                }
            }
        } else {
            PropertyDescriptor[] descriptors = BeanUtilsBean.getInstance().getPropertyUtils().getPropertyDescriptors(bean);
            Class<?> clazz = bean.getClass();
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                if (MethodUtils.getAccessibleMethod(clazz, descriptors[i].getReadMethod()) != null && include.contains(name)) {
                    description.put(name, PropertyUtils.getNestedProperty(bean, name));
                }
            }
        }
        return (description);
    }


    public static void copyProperties(Object dest, Object orig) throws IllegalAccessException, InvocationTargetException {
        if (dest == null) {
            throw new IllegalArgumentException("copyProperties dest 不能为NULL");
        }
        if (orig == null) {
            throw new IllegalArgumentException("copyProperties 不能为NULL");
        }
        if (log.isDebugEnabled()) {
            log.debug("BeanUtils.copyProperties(" + dest + ", " + orig + ")");
        }
        // 复制属性，进行必要的类型转换
        BeanUtilsBean bub = BeanUtilsBean.getInstance();
        if (orig instanceof DynaBean) {
            DynaProperty[] origDescriptors = ((DynaBean) orig).getDynaClass().getDynaProperties();
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                // WrapDynaBean检查是否有可写
                if (bub.getPropertyUtils().isReadable(orig, name) && bub.getPropertyUtils().isWriteable(dest, name)) {
                    Object value = ((DynaBean) orig).get(name);
                    bub.copyProperty(dest, name, value);
                }
            }
        } else if (orig instanceof Map) {
            Iterator<?> entries = ((Map<?, ?>) orig).entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<?, ?> entry = (Map.Entry<?, ?>) entries.next();
                String name = (String) entry.getKey();
                if (bub.getPropertyUtils().isWriteable(dest, name)) {
                    bub.copyProperty(dest, name, entry.getValue());
                }
            }
        } else /* orig是标准的java bean */ {
            PropertyDescriptor[] origDescriptors = bub.getPropertyUtils().getPropertyDescriptors(orig);
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if (excludeProperties.contains(name) || name.startsWith("$")) {
                    continue; // 过滤掉部分属性
                }
                if (bub.getPropertyUtils().isReadable(orig, name) && bub.getPropertyUtils().isWriteable(dest, name)) {
                    try {
                        Object value = bub.getPropertyUtils().getSimpleProperty(orig, name);
                        bub.copyProperty(dest, name, value);
                    } catch (NoSuchMethodException e) {
                        //不存在方法
                    }
                }
            }
        }
    }

    //将List对象中的对象指定元素映射为Map
    public static <T> List<Map<String, Object>> list2Map(List<T> list) {
        return list2Map(list, null);
    }

    public static <T> List<Map<String, Object>> list2Map(List<T> list, String[] include) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<String> inList = java.util.Arrays.asList(include != null ? include : new String[]{});
        for (T entity : list) {
            Map<String, Object> rows = new HashMap<>();
            try {
                if (include == null) {
                    rows = describe(entity);
                } else {
                    rows = describe(entity, inList);
                }
            } catch (IllegalAccessException |
                    InvocationTargetException |
                    NoSuchMethodException e) {
                e.printStackTrace();
            }
            result.add(rows);
        }
        return result;
    }

    public static <T> void list2Map(Map<String, List<Map<String, Object>>> map,
                                    List<T> list, String name) {
        List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();
        for (T entity : list) {
            Map<String, Object> rows = new HashMap<String, Object>();
            try {
                rows = describe(entity);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            values.add(rows);
        }
        map.put(name, values);
    }

    public static Map<String, Object> transBean2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    try {
                        Object value = getter.invoke(obj);
                        map.put(key, value);
                    } catch (Exception e) {
                        //System.out.println(" transBean2Map inner error " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            //  System.out.println("transBean2Map Error " + e);
        }

        return map;

    }
}
