package com.zhaowenx.zuultwo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created by zhaowenx on 2019/1/22.
 */

//sping boot启动类
@SpringBootApplication
//表明这是一个eureka客户端
@EnableEurekaClient
//支持网关路由
@EnableZuulProxy
public class ZuultwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuultwoApplication.class, args);
    }
}
