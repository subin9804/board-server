package com.fastcam.boardserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    // prefix를 지정해줌으로서 properties 파일에 spring.datasource로 시작하는 데이터 소스를 불러올 수 있음
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource dataSource() {

        return DataSourceBuilder.create().build();
    }
}
