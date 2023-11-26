package com.itsystem.springbootorderfood;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.itsystem.springbootorderfood.mapper")
public class SpringbootOrderfoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootOrderfoodApplication.class, args);
	}

}
