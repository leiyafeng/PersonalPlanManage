package cn.lyf.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement//开启事务管理注解
public class AccountApplication {



	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}
}


