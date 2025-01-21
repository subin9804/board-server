package com.fastcam.boardserver.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.fastcam.boardserver.mapper") // 인터페이스의 경로와 동일해야 한다.
public class MysqlConfig {

    // Spring이 관리하는 데이터 소스 빈을 주입받아 MyBatis에서 사용할 데이터베이스 연결을 제공
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        // 데이터 소스와 연결하여 MyBatis가 데이터베이스와 통신하도록 설정
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        //XML Mapper 파일을 위치를 기반으로 로드
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath:mappers/*.xml"));

        Resource myBatisConfig = new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml");
        sessionFactory.setConfigLocation(myBatisConfig);

        // SqlSessionFactoryBean을 통해 생성된 SqlSessionFactory 객체를 반환
        return sessionFactory.getObject();
    }
}
