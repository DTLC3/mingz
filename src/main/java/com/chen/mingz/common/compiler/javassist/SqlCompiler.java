package com.chen.mingz.common.compiler.javassist;


import com.alibaba.fastjson.JSON;
import com.chen.mingz.common.basic.model.CmzSearch;
import com.chen.mingz.common.compiler.AbstractCompiler;
import com.chen.mingz.common.compiler.ClassHelper;
import com.chen.mingz.common.utils.Constant;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtNewMethod;

public class SqlCompiler extends AbstractCompiler {

    @Override
    protected Class<?> doCompile(String name, String isTable) throws Throwable {
        CmzSearch search = JSON.parseObject(name, CmzSearch.class);
        String querySql = search.getInquire();
        String className = "com.chen" + search.getService() + "Query$" + Constant.atomicLong.incrementAndGet();
        String include = search.getInclude() != null ? search.getInclude() : "";
        String[] includes = include.split(",");
        ClassPool pool = ClassPool.getDefault();

        pool.insertClassPath(new ClassClassPath(com.chen.mingz.common.basic.model.BaseParameter.class));

        pool.insertClassPath(new ClassClassPath(com.chen.mingz.common.utils.Pagination.class));

        pool.insertClassPath(new ClassClassPath(com.chen.mingz.common.auto.AutoService.class));

        pool.insertClassPath(new ClassClassPath(com.chen.mingz.common.basic.service.CService.class));


        CtClass clazz = pool.makeClass(className);
        String verify = "";
        if (include.length() > 0) {
            StringBuilder stringBuilder = new StringBuilder("String[] include = new String[]{");
            for (String i : includes) {
                stringBuilder.append("\"" + i + "\",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append("};");
            verify = stringBuilder.toString();
        }
        String[] packages = {
                "com.chen.mingz.common.auto.AutoService",
                "com.chen.mingz.common.basic.model.BaseParameter",
                "com.chen.mingz.common.utils.Pagination",
                "com.chen.mingz.common.basic.service.CService"
        };
        for (String pack : packages) {
            pool.importPackage(pack);

        }
        clazz.addInterface(pool.get("com.chen.mingz.common.auto.AutoService"));

        String methodBody = "public Object doService(CService service, BaseParameter parameter) {\n";
        if (!"".equals(verify.trim())) {
            methodBody = methodBody + verify + "\n";
            methodBody = methodBody + "parameter.verify(include);\n";
        }
        String methodLast = " parameter.setSql(\"" + querySql + "\");\n" +
                "        Pagination pagination = parameter.getPagination();\n" +
                "        if(pagination == null){ parameter.setPagination(new Pagination());}" +
                "        return service.queryPage(parameter);\n" +
                "    }";
        methodBody = methodBody + methodLast;
        clazz.addMethod(CtNewMethod.make(methodBody, clazz));
        return clazz.toClass(ClassHelper.getCallerClassLoader(getClass()), SqlCompiler.class.getProtectionDomain());
    }

}
