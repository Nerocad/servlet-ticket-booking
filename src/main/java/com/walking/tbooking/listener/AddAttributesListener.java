package com.walking.tbooking.listener;


import com.walking.tbooking.service.MigrationService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AddAttributesListener implements ServletContextListener {

    private static final String PROPERTIES_PATH = "./resources/hikari.properties";
    private static final Logger logger = (Logger) LogManager.getLogger(AddAttributesListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {

        ServletContext servletContext = event.getServletContext();

        DataSource dataSource = createHikariDataSource(servletContext);
        servletContext.setAttribute("dataSource", dataSource);

        servletContext.setAttribute("migrationService", new MigrationService(dataSource));

    }

    private DataSource createHikariDataSource(ServletContext servletContext) {
        try (InputStream propertiesInputStream = servletContext.getResourceAsStream(PROPERTIES_PATH)) {
            Properties hikariProperties = new Properties();
            hikariProperties.load(propertiesInputStream);

            HikariConfig configuration = new HikariConfig(hikariProperties);

            return new HikariDataSource(configuration);
        } catch (IOException e) {
            logger.error("Failed to load Hikari configuration from {}", PROPERTIES_PATH, e);
            throw new RuntimeException("Failed to initialize HikariCP", e);
        }
    }
}
