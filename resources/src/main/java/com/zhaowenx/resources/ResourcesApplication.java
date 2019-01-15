package com.zhaowenx.resources;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by zhaowenx on 2019/1/15.
 */

@SpringBootApplication
@EnableEurekaClient
public class ResourcesApplication {

    /**
     * @param args
     * EnableEurekaClient 该注解表明该台服务可以注册到eureka server
     */

    public static void main(String[] args) {
        SpringApplication.run(ResourcesApplication.class, args);
    }
}
