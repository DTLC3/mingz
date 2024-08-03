package com.chen.mingz.config;

public interface UrlConfig {


    String SUCCESS = "1";


    String SUCCESSMSG = "操作成功";


    String ERROR = "0";


    String ERRORMSG = "操作失败";

    interface Basic {
        String ADD = "/add";
        String GET = "/get";
        String DELETE = "/delete";
        String UPDATE = "/update";
    }
}

