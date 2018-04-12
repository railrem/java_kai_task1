package ru.kai.utils;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.servlet.ServletContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseHelper {


    public static DriverManagerDataSource getDataSource(ServletContext servletContext) {
        Properties properties = new Properties();
        DriverManagerDataSource dataSource =
                new DriverManagerDataSource();
        try {
            properties.load(new FileInputStream(servletContext.getRealPath("/WEB-INF/classes/db.properties")));
            String dbUrl = properties.getProperty("db.url");
            String dbUsername = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");
            String driverClassName = properties.getProperty("db.driverClassName");

            dataSource.setUsername(dbUsername);
            dataSource.setPassword(dbPassword);
            dataSource.setUrl(dbUrl);
            dataSource.setDriverClassName(driverClassName);
            return dataSource;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
