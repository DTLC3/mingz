package com.chen.mingz.common.utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by chenmingzhi on 18/5/25.
 */
public class PackageUtils {


    public static final String DEFAULT_RESOURCE_PATTERN = "*.class";


    public static List<Class<?>> getClassByClass(String pack) {
        List<Class<?>> list = new ArrayList<>();
        if (!StringUtils.isEmpty(pack)) {
            ////获取Spring资源解析器
            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            //创建Spring中用来读取resource为class的工具类
            MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);

            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                    + ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(pack))
                    + "/" + DEFAULT_RESOURCE_PATTERN;

            Set<String> set = checkPackages(packageSearchPath);
            for (String locatioin : set) {
                try {
                    //获取packageSearchPath下的Resource，这里得到的Resource是Class信息
                    Resource[] resources = resourcePatternResolver.getResources(locatioin);

                    for (Resource resource : resources) {
                        //检查resource，这里的resource都是class
                        try {
                            if (resource.isReadable()) {
                                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                                if (metadataReader != null) {
                                    list.add(metadataReader.getClassMetadata().getClass());

                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public static Set<String> checkPackages(String scanPackages) {
        Set<String> set = new TreeSet<>();
        if (StringUtils.isEmpty(scanPackages)) {
            return set;
        }
        Collections.addAll(set, scanPackages.split(","));
        for (String pack : set.toArray(new String[set.size()])) {
            if (StringUtils.isEmpty(pack) || pack.equals(".") || pack.startsWith(".")) {
                continue;
            }
            if (pack.endsWith(".")) {
                pack = pack.substring(0, pack.length() - 1);
            }
            Iterator<String> packIter = set.iterator();
            boolean neetAdd = true;
            while (packIter.hasNext()) {
                String model = packIter.next();
                if (pack.startsWith(model + ".")) {
                    neetAdd = false;
                } else if (model.startsWith(pack + ".")) {
                    packIter.remove();
                }
            }
            if (neetAdd) {
                set.add(pack);
            }
        }
        return set;
    }


    public static List<Class<?>> getClassByJava(String location) {
        List<Class<?>> list = new ArrayList<>();
        if (!StringUtils.isEmpty(location)) {
            try {
                Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(location.replace(".", "/"));
                while (urls.hasMoreElements()) {
                    URL url = urls.nextElement();
                    if (url != null) {
                        String protocol = url.getProtocol();
                        if ("file".equals(protocol)) {
                            final String path = URLDecoder.decode(url.getFile(), "UTF-8");
                            addClass(list, path, location);
                        } else if ("jar".equals(protocol)) {
                            JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                            if (jarURLConnection != null) {
                                JarFile jarFile = jarURLConnection.getJarFile();
                                if (jarFile != null) {
                                    Enumeration<JarEntry> jarEntrys = jarFile.entries();
                                    while (jarEntrys.hasMoreElements()) {
                                        JarEntry entry = jarEntrys.nextElement();
                                        String jarEntryName = entry.getName();
                                        if (jarEntryName.endsWith(".class")) {
                                            String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                            doAddClass(list, className);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return list;
    }

    private static void addClass(List<Class<?>> list, String path, String location) {
        File[] files = new File(path).listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return (pathname.isFile() && pathname.getName().endsWith(".class") || pathname.isDirectory());
            }
        });
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (!StringUtils.isEmpty(className)) {
                    className = location + "." + className;
                }
                doAddClass(list, className);
            } else {  //目录的情况下
                String subPath = fileName;
                if (!StringUtils.isEmpty(path)) {
                    subPath = path + "/" + subPath;
                }
                String subName = fileName;
                if (!StringUtils.isEmpty(location)) {
                    subName = location + "." + subName;
                }
                addClass(list, subPath, subName);
            }

        }
    }

    private static void doAddClass(List<Class<?>> list, String className) {
        try {
            Class<?> clazz = Class.forName(className);
            list.add(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}














