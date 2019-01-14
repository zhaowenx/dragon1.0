package com.zhaowenx.resource.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhaowenx on 2019/1/14.
 */
@RestController
//@RequestMapping("/resource")
public class TestController {

    @RequestMapping("/test")
    public String aaaa(){
        return "1";
    }
}
