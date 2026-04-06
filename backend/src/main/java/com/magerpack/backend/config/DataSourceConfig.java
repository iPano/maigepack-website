package com.magerpack.backend.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.net.URI;

/**
 * Parses Railway's DATABASE_URL (postgresql://user:pass@host:port/db)
 * into a proper JDBC URL for HikariCP, since the PostgreSQL JDBC driver
 * does not accept the bare "postgresql://" scheme.
 */
@Configuration
@Profile("prod")
public class DataSourceConfig {

    @Value("${DATABASE_URL:#{null}}")
    private String databaseUrl;

    @Bean
    public DataSource dataSource() {
        if (databaseUrl == null || databaseUrl.isEmpty()) {
            throw new IllegalStateException(
                "DATABASE_URL environment variable is not set. " +
                "Check Railway backend service variables."
            );
        }

        // Log the URL format for debugging (without password)
        System.out.println("DATABASE_URL format: " + databaseUrl.replaceAll("://[^:]+:[^@]+@", "://***:***@"));

        URI uri = URI.create(databaseUrl.replace("postgresql://", "http://"));
        String host = uri.getHost();
        int port = uri.getPort() == -1 ? 5432 : uri.getPort();
        String database = uri.getPath().replaceFirst("/", "");
        String[] userInfo = uri.getUserInfo().split(":");
        String username = userInfo[0];
        String password = userInfo.length > 1 ? userInfo[1] : "";

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://" + host + ":" + port + "/" + database);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName("org.postgresql.Driver");

        return new HikariDataSource(config);
    }
}
