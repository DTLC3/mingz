package com.chen.mingz.common.compiler.javassist;


import com.chen.mingz.common.compiler.AbstractCompiler;
import com.chen.mingz.common.compiler.ClassHelper;
import com.chen.mingz.common.utils.Constant;
import javassist.*;

public class CurdCompiler extends AbstractCompiler {


    @Override
    protected Class<?> doCompile(String name, String source) throws Throwable {
        String className = "com.chen" + name + "Query$" + Constant.atomicLong.incrementAndGet();
        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath("com.chen.mingz.common");
        CtClass clazz = pool.makeClass(className);

        pool.insertClassPath(new ClassClassPath(com.chen.mingz.common.utils.Pagination.class));

        pool.insertClassPath(new ClassClassPath(com.chen.mingz.common.auto.AutoService.class));

        pool.insertClassPath(new ClassClassPath(com.chen.mingz.common.basic.model.BaseParameter.class));

        pool.insertClassPath(new ClassClassPath(com.chen.mingz.common.basic.service.CService.class));

        String[] packages = {
                "com.chen.mingz.common.utils.Pagination",
                "com.chen.mingz.common.auto.AutoService",
                "com.chen.mingz.common.basic.model.BaseParameter",
                "com.chen.mingz.common.basic.service.CService"
        };
        for (String pack : packages) {
            pool.importPackage(pack);

        }
        //添加实现接口
        clazz.addInterface(pool.get("com.chen.mingz.common.auto.AutoService"));
        String methodBody = "public Object doService(CService service, BaseParameter parameter) {\n" +
                "        String sql = \"select * from " + source + " \";" +
                "        parameter.setSql(sql); \n" +
                "        Pagination pagination = parameter.getPagination();" +
                "        if(pagination == null){ parameter.setPagination(new Pagination());}\n" +
                "        return service.queryPage(parameter);\n" +
                "    }";
        CtMethod method = CtNewMethod.make(methodBody, clazz);
        method.setModifiers(java.lang.reflect.Modifier.PUBLIC);
        clazz.addMethod(method);
        return clazz.toClass(ClassHelper.getCallerClassLoader(getClass()), CurdCompiler.class.getProtectionDomain());
    }
}
