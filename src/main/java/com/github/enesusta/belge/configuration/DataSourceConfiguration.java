package com.github.enesusta.belge.configuration;

import com.github.enesusta.jdbc.datasource.HikariJdbcDataSource;
import com.github.enesusta.jdbc.datasource.JdbcConfiguration;
import com.github.enesusta.jdbc.datasource.JdbcDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@PropertySource("classpath*:application.yml")
public class DataSourceConfiguration {

    @Value("${spring.datasource.hikari.jdbc-url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String pass;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Bean
    public DataSource dataSource() {

        final JdbcConfiguration configuration = new JdbcConfiguration.JdbcConfigurationBuilder()
                .username(userName)
                .password(pass)
                .driverClassName(driverClassName)
                .jdbcUrl(url)
                .build();

        final JdbcDataSource dataSource = new HikariJdbcDataSource();
        return dataSource.getDataSource(configuration);

    }


}
