package com.zhaowenx.resource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.zhaowenx.resource.mapper")
public class UserApplication {

	/**
	 * @param args
	 * EnableEurekaClient 该注解表明该台服务可以注册到eureka server
	 */

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}
}
