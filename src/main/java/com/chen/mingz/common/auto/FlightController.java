package com.chen.mingz.common.auto;


import com.alibaba.fastjson.JSONObject;
import com.chen.mingz.common.basic.service.AutoService;
import com.chen.mingz.config.UrlConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/flight")
@ConditionalOnProperty(name = "cmz.html", havingValue = "true")
public class FlightController {


    @Resource
    private AutoService service;

    @ResponseBody
    @RequestMapping(value = "/add")
    @CrossOrigin
    public JSONObject cmz(@RequestBody String json) {
        JSONObject result = new JSONObject();
        try {
            result.put("service", service.register(json));
            result.put("sign", UrlConfig.SUCCESS);
            result.put("msg", UrlConfig.SUCCESSMSG);
        } catch (Exception e) {
            result.put("d", e.getMessage());
            result.put("sign", UrlConfig.ERROR);
            result.put("msg", UrlConfig.ERRORMSG);
        }
        return result;
    }


    @RequestMapping(value = "/html", produces = MediaType.TEXT_HTML_VALUE + ";charset=utf-8")
    public void html(HttpServletResponse response) {
        String html = service.html();
        PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=utf-8");
            out = response.getWriter();
            out.println(html);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }


    @ResponseBody
    @RequestMapping(value = "/refresh")
    public JSONObject refresh() {
        JSONObject result = new JSONObject();
        try {
            service.refresh();
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
