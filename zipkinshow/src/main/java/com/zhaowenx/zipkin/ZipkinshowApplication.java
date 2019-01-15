package com.zhaowenx.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import zipkin.server.EnableZipkinServer;

/**
 * Created by zhaowenx on 2019/1/15.
 */

@SpringBootApplication
@EnableEurekaClient
@EnableZipkinServer//启用Zipkin服务
public class ZipkinshowApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZipkinshowApplication.class, args);
    }
}
