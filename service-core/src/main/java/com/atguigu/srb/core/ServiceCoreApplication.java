package com.atguigu.srb.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @program: srb
 * @EnableTransactionManagement //事务处理
 * @description: 主类
 * @author: jiankai
 * @create: 2021-09-06 17:03
 **/
@SpringBootApplication
@ComponentScan({"com.atguigu.srb"})
public class ServiceCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceCoreApplication.class, args);
    }
}
