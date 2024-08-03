package com.chen.mingz.common.auto;

import com.alibaba.fastjson.JSON;
import com.chen.mingz.common.auto.query.QuerySql;
import com.chen.mingz.common.basic.model.CmzSearch;
import com.chen.mingz.common.basic.service.impl.CommonService;
import com.chen.mingz.common.compiler.JavaCompiler;
import com.chen.mingz.common.compiler.javassist.CurdCompiler;
import com.chen.mingz.common.compiler.javassist.SqlCompiler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableJpaRepositories(basePackages = "com.chen")
@EntityScan(basePackages = {"com.chen"})
@ComponentScan(basePackages = {"com.chen"})
@Slf4j
@EnableTransactionManagement
public class CmzBootQuery implements ApplicationContextAware, EnvironmentAware, ApplicationListener<ContextRefreshedEvent> {

    public static ConcurrentHashMap<String, String> SERVICE = new ConcurrentHashMap<>();

    private static JavaCompiler compiler = new CurdCompiler();

    private static JavaCompiler sqlCompiler = new SqlCompiler();

    private BeanDefinitionRegistry beanDefinitionRegistry;


    private Environment environment;

    @Resource
    private CommonService service;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.beanDefinitionRegistry = (BeanDefinitionRegistry) applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    public String queryPage(String sql) {
        return QuerySql.queryPage(environment.getProperty("spring.datasource.url"),
                environment.getProperty("cmz.database"), sql);

    }

    public void eject(String beanName) {
        beanDefinitionRegistry.removeBeanDefinition(beanName);
    }

    public void refresh() {
        initTable();
        initCurd();
    }

    private void injectTable(String table) {
        try {
            Class<?> cls = compiler.compile(table, table);
            Assert.notNull(cls, "(table)注册对象Class为空!!!");
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(cls);
            GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
            beanDefinitionRegistry.registerBeanDefinition(table, definition);
            SERVICE.putIfAbsent(table, table);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }

    }

    public void injectSearch(String search) {
        try {
            CmzSearch tableSearch = JSON.parseObject(search, CmzSearch.class);
            Class<?> cls = sqlCompiler.compile(search, "");
            Assert.notNull(cls, "(search)注册对象Class为空!!!");
            String table = tableSearch.getService();
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(cls);
            GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
            definition.setBeanClass(cls);
            beanDefinitionRegistry.registerBeanDefinition(table, definition);
            SERVICE.putIfAbsent(table, table);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }

    }


    private void initCurd() {
        try {
            String url = environment.getProperty("spring.datasource.url");
            String database = environment.getProperty("cmz.database");
            String type = QuerySql.dataType(url, database);
            Assert.hasText(type, "没能识别出数据库类型");
            String query = QuerySql.inQuery(url, type);
            List<Map<String, String>> list = service.queryMap(query);
            try {
                list.forEach((entity) -> {
                    entity.forEach((key, value) -> {
                        try {
                            if (!SERVICE.contains(value) &&
                                    !beanDefinitionRegistry.containsBeanDefinition(value)) {
                                injectTable(value);
                            } else {
                                log.warn("服务:" + value + "已存在");
                            }
                        } catch (NullPointerException e) {
                            log.error(e.getMessage());
                        }
                    });
                });
            } catch (Exception e) {
                log.warn(e.getMessage());
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }

    private void createTableSearch() {
        try {
            String url = environment.getProperty("spring.datasource.url");
            String database = environment.getProperty("cmz.database");
            String type = QuerySql.dataType(url, database);
            String query = QuerySql.createTable(type);
            service.nativeQuery(query);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }


    private void initTable() {
        try {
            try {
                String database = environment.getProperty("cmz.database");
                String url = environment.getProperty("spring.datasource.url");
                String type = QuerySql.dataType(url, database);
                Assert.hasText(type, "没能识别出数据库类型");
                String query = QuerySql.queryExist(type);
                service.queryMap(query);
            } catch (Exception e) {
                log.warn("autoSearch不存在，创建中.....");
                createTableSearch();
                log.warn("autoSearch创建成功!!!");
            }
            List<Map<String, Object>> list = service.queryMap(QuerySql.queryTable);
            list.forEach((row) -> {
                try {
                    String json = JSON.toJSONString(row);
                    CmzSearch tableSearch = JSON.parseObject(json, CmzSearch.class);
                    String service = tableSearch.getService();
                    if (!SERVICE.contains(service)
                            && !beanDefinitionRegistry.containsBeanDefinition(service)) {
                        injectSearch(json);
                    } else {
                        log.warn("服务:" + tableSearch.getService() + "已存在");
                    }
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initTable();
        initCurd();
    }

}
