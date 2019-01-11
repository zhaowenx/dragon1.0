package com.zhaowenx.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//sping boot启动类
@SpringBootApplication
//表明这是一个eureka客户端
@EnableEurekaClient
//开启Feign功能
@EnableFeignClients
@EnableDiscoveryClient
//开启断路器功能
@EnableCircuitBreaker
@MapperScan("com.zhaowenx.resource.mapper")
public class ManageApplication {

	/**
	 * @param args
	 * EnableEurekaClient 该注解表明该台服务可以注册到eureka server
	 * EnableFeignClients 启用feign进行远程调用
	 * EnableDiscoveryClient 将该应用注册为Eureka客户端应用
	 */

	@Bean
	@LoadBalanced
	RestTemplate restTemplate(){
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(ManageApplication.class, args);
	}
}
