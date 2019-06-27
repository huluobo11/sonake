package com.hc.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;


/**
 * 配置中心服务
 */
@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class HcConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(HcConfigApplication.class, args);
    }

}
