package cn.lyf.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement//开启事务管理注解
@EnableScheduling//定时任务
public class AccountApplication {



	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}
}


