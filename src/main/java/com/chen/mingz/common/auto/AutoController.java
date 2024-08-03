package com.chen.mingz.common.auto;


import com.alibaba.fastjson.JSONObject;
import com.chen.mingz.common.basic.model.BaseParameter;
import com.chen.mingz.common.basic.service.impl.CommonService;
import com.chen.mingz.config.UrlConfig;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/auto")
public class AutoController implements ApplicationContextAware {


    @Resource
    private CommonService service;

    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    @ResponseBody
    @RequestMapping(value = "/service")
    public JSONObject service(@RequestBody BaseParameter parameter) {
        JSONObject result = new JSONObject();
        try {
            String name = parameter.getService();
            Assert.notNull(name, "服务名(service)没传");
            AutoService autoService = (AutoService) applicationContext.getBean(name);
            result.put("d", autoService.doService(service, parameter));
            result.put("sign", UrlConfig.SUCCESS);
            result.put("msg", UrlConfig.SUCCESSMSG);
        } catch (Exception e) {
            result.put("d", e.getMessage());
            result.put("sign", UrlConfig.ERROR);
            result.put("msg", UrlConfig.ERRORMSG);
        }
        return result;
    }

}
