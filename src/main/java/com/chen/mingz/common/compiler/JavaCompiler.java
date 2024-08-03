package com.chen.mingz.common.compiler;

public interface JavaCompiler {

    Class<?> compile(String code, ClassLoader classLoader);


    Class<?> compile(String name, String path);


    Class<?> compile(String name, Class clazz);

}
