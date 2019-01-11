package com.zhaowenx.eurekatwo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by zhaowenx on 2018/11/6.
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekatwoApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekatwoApplication.class, args);
    }
}
