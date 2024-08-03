package com.chen.mingz.common.spi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenmingzhi
 * @date 2019-08-15
 */
public class DServiceLoader<T> {

    private static final String PREFIX = "META-INF/ylz/";

    private static ConcurrentHashMap<Class<?>, DServiceLoader<?>> SERVICE_LOADERS = new ConcurrentHashMap<>();

    private static ConcurrentHashMap<String, Class<?>> SERVICE_NAMES = new ConcurrentHashMap<>();

    private static ConcurrentHashMap<String, Holder<Object>> cacheInstances = new ConcurrentHashMap<>();

    private Map<String, T> cacheObject = new HashMap<>();

    private Holder<Object> holder = new Holder<Object>();

    private volatile Class<?> cacheClass = null;

    private volatile Throwable holderError;

    private final Class<?> type;

    //存储存在以type为参数的构造函数的对应实现类
    private Set<Class<?>> cacheWrapperClasses;

    private DServiceLoader(Class<?> type) {
        this.type = type;
    }

    public static <T> DServiceLoader<T> getServiceLoader(Class<T> type) {
        if (type == null)
            throw new IllegalArgumentException("Extension type == null");
        if (!type.isInterface()) {
            throw new IllegalArgumentException("Extension type(" + type + ") is not interface!");
        }
        DServiceLoader<T> loader = (DServiceLoader<T>) SERVICE_LOADERS.get(type);
        if (loader == null) { //还没有加载过文件
            SERVICE_LOADERS.putIfAbsent(type, new DServiceLoader<T>(type));
            loader = (DServiceLoader<T>) SERVICE_LOADERS.get(type);
            loader.getObjectClass(); //初始化的时候就加载SPI文件
        }
        return loader;
    }

    public synchronized T getInstance(String name) throws IllegalAccessException, InstantiationException {
        Holder<Object> holder = cacheInstances.get(name);
        if (holder == null) {
            cacheInstances.putIfAbsent(name, new Holder<Object>());
            holder = cacheInstances.get(name);
        }
        Object instance = holder.getValue();
        if (instance == null) {
            instance = createObject(name);
            holder.setValue(instance);
        }
        return (T) instance;
    }


    public T createObject(String name) {
        try {
            return (T) getInstanceClass(name).newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("can not create object " + type + ", cause :" + e.getMessage(), e);
        }
    }

    private Class<?> getInstanceClass(String name) {
        cacheClass = SERVICE_NAMES.get(name);
        if (cacheClass == null) {
            throw new IllegalStateException(" 实例化失败 !!! ");
        }
        return cacheClass;
    }


    //装载所有的class
    private void getObjectClass() {
        String fileName = PREFIX + type.getName();
        try {
            Enumeration<URL> urls;
            ClassLoader classLoader = DServiceLoader.class.getClassLoader();
            if (classLoader != null) {
                urls = classLoader.getResources(fileName);
            } else {
                urls = ClassLoader.getSystemResources(fileName);
            }
            if (urls != null) {
                while (urls.hasMoreElements()) {
                    URL url = urls.nextElement();
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
                        try {
                            String line = null;
                            while ((line = reader.readLine()) != null) {
                                final int ci = line.indexOf("#");
                                if (ci >= 0) {
                                    line = line.substring(0, ci);
                                }
                                line = line.trim();
                                if (line.length() > 0) {
                                    try {
                                        String name = null;
                                        int i = line.indexOf("=");
                                        if (i > 0) {
                                            name = line.substring(0, i).trim();
                                            line = line.substring(i + 1).trim();
                                        }
                                        if (line.length() > 0) {
                                            Class<?> clazz = Class.forName(line, true, classLoader);
                                            if (!type.isAssignableFrom(clazz)) {
                                                throw new IllegalStateException("error load calss type" + type.getName());
                                            }
                                            try {
                                                SERVICE_NAMES.putIfAbsent(name, clazz);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    } catch (Throwable e) {

                                    }
                                }
                            }
                        } catch (IOException e0) {

                        } finally {
                            reader.close();
                        }
                    } catch (IOException io) {

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static class Holder<T> {

        private T value;

        public T getValue() {
            return value;
        }


        public void setValue(T value) {
            this.value = value;
        }
    }

}
