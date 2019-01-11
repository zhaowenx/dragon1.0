package com.zhaowenx.eurekaone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaoneApplication {

    /**
     * @param args
     * EnableEurekaServer 该注解表明这是一台服务注册中心
     */

    public static void main(String[] args) {
        SpringApplication.run(EurekaoneApplication.class, args);
    }
}
