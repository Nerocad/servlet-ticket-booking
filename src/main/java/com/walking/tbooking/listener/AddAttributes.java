package com.walking.tbooking.listener;


import com.walking.tbooking.service.MigrationService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AddAttributes implements ServletContextListener {

    private final String PROPERTIES_PATH = "./resources/hikari.properties";

    @Override
    public void contextInitialized(ServletContextEvent event) {

        ServletContext servletContext = event.getServletContext();

        DataSource dataSource = hikariDataSource(servletContext);
        servletContext.setAttribute("dataSource", dataSource);

        MigrationService migrationService = new MigrationService(dataSource);
        servletContext.setAttribute("migrationService", migrationService);

    }

    private DataSource hikariDataSource(ServletContext servletContext) {
        try (InputStream propertiesInputStream = servletContext.getResourceAsStream(PROPERTIES_PATH)) {
            Properties hikariProperties = new Properties();
            hikariProperties.load(propertiesInputStream);

            HikariConfig configuration = new HikariConfig(hikariProperties);

            return new HikariDataSource(configuration);
        } catch (IOException e) {
            System.out.println("Невозможно загрузить конфигурацию для Hikari");

            throw new RuntimeException(e);
        }
    }
}
