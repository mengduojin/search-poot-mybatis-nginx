package com.search.searchpootmybatisnginx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.po","com.service","com.controller","com.util"})
@MapperScan(basePackages = {"com.mapper"})
public class SearchPootMybatisNginxApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchPootMybatisNginxApplication.class, args);
    }

}
