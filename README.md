## mingz

(**认为你已经具有基本开发能力**)

#使用方法:
   ##在新建的spring boot工程中的pom.xml添加依赖(version根据具体版本号进行调整)
    
    <dependency>
        <groupId>com.chen</groupId>
        <artifactId>mingz</artifactId>
        <version>0.0.1</version>
    </dependency>


## 其中mingz包中已包含的jar包在新工程中不需要重新导入，注意包冲突问题，如果个别jar包想要使用不同版本可以通过以下方式排除mingz包中已经导入的jar包，然后在新工程中加入自己的jar包版本(建议最好不要这么做)
    
######   
    <dependency>
        <groupId>com.chen</groupId>
        <artifactId>mingz</artifactId>
        <version>0.0.1</version>
        <exclusions>
            <exclusion>
                <groupId>sample.ProjectB</groupId>
                <artifactId>Project-B</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    
##### 常用方法介绍:
######前端请求参数:
不需要的查询条件可以不传送对应关键字如：query,like... <br>
rows 每页显示几条数据, page 第几页,不是分页查询可不传这2个参数 <br>
query为条件相等 field=value <br>
beginLike为字段对应查询条件为-> like 'value%' <br>
like 为模糊查询 like '%value%' <br>
greater为大于 field > value <br>
greaterE为大于等于 field>=value  <br>
low为小于 field < value <br>
lowerE为小于等于 field<=value <br>
between -> field between first and second <br>
in -> field in (1,2,3) <br>
criteria 为特殊查询条件原封不用作为查询条件(会有SQL注入问题，后台注意必传参数的校验)
sortedConditions为排序 order by field asc
```json
{
    "rows":3,
    "page":1,
    "query":{
    	"filed(字段名)":"value"
    },
    "beginLike":{
    	"filed":"value"
    },
    "like":{
    	"field":"value"
    },
    "greater":{
    	"field":"value"
    },
    "greaterE":{
    	"field":"value"
    },
    "low":{
    	"field":"value"
    },
    "lowerE":{
    	"field":"value"
    },
    "between":{
    	"field":{
    		"first":"first",
    		"second":"second"
    	}
    },
    "in":{
    	"field":"1,2,3"
    },
    "criteria":" field > 3 or field < 4",
    "sortedConditions":{
    	"field":"asc"
    }
}
```

####在spring boot启动类中添加JPA相关注解

```java
@SpringBootApplication
@EnableJpaRepositories
@EntityScan
public class DatianApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatianApplication.class, args);
    }

}
```

####Java Controller层,如果不使用以下方法也可以直接用JPA的方法


```java
@RestController
@RequestMapping(value = "/demo")
public class DemoController {

    @Resource
    private CommonService service;

    @ResponseBody
    @RequestMapping(value = "/path")
    public JSONObject path(@RequestBody BaseParameter parameter) {
        JSONObject result = new JSONObject();
        try {
            //查询条件
            String condition = parameter.getConditions(null);
            String sql = "select * from demo where " + condition;
            //分页查询，rows(每页显示的条数),page(第几页)
            Pagination pagination = parameter.getPagination();
            //service.queryMap(sql)  ---->查询SQL(可能会返回太多数据)
            result.put("d", service.queryPage(sql, pagination));
            result.put("sign", UrlConfig.SUCCESS);
            result.put("msg", UrlConfig.SUCCESSMSG);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("sign", UrlConfig.ERROR);
            result.put("msg", UrlConfig.ERRORMSG);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping(value = "/demo2")
    public JSONObject demo2() {
        JSONObject result = new JSONObject();
        try {
            //SQL结果转为List<Model>,注意：返回字段需要和对应model属性对应的上
            List<Model> list = service.sqlQuery(Model.class,"select * from demo");
           
            // 直接执行原生SQL
            service.nativeQuery(" insert || update");
            
            //存储过程返回值名和对应类型
            Map<String, Class> out = new HashMap<String, Class>(){
                {
                    put("out1",String.class);
                }
            };
            //调用存储过程 
            //procedure(v_qsrq00 in char,v_jzrq00 in char, out1 out char,)
            Map<String, Object> output = service.doProcedure("procedure",
            new String[]{"v_qsrq00","v_jzrq00"}, /*入参名字*/
            new Class[]{String.class, String.class}, /*入参对应类型*/
            new Object[]{"20190101","20190201"},  /*入参值*/
            out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

```
###服务征程启动以后，控制台出现以下图标则为正常启动:

 

默认已将配置文件中的数据库单表查询完成，调用路径为: <br>

localhost:9090/datian/auto/service

请求参数为:（查询参数同上，添加service参数，值为对应的数据库表名） 
```json
{
	"service":"role",
	"rows":1,
	"page":1
}

```

###运行中发布接口功能
-默认情况下是不开启的需要在配置文件中配置以下参数才能在浏览器
中正常访问
```yaml
cmz:
    html:true
```
 启动成后可以再浏览器中输入以下地址<br>
 
 http://localhost:9090/datian/flight/html
 
显示如下: <br>

 

对应接口查询路径为: <br>
http://localhost:9090/datian/auto/service <br>
调用方法同上，查询条件同上


 
 
 