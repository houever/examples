package com.me;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *	服务的提供者
 *	1、引入dubbo-springboot-starter
 *	2、引入zkclient
 *	3、配置dubbo 注册地址
 *	4、在服务实现类上面加上注解：@Service  @Component
 */
@EnableDubbo
@SpringBootApplication
public class TicketProviderApp {

	public static void main(String[] args) {
		SpringApplication.run(TicketProviderApp.class, args);
	}
}
