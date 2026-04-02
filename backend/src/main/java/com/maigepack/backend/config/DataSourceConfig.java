package com.maigepack.backend.config;

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

    @Value("${PGHOST:#{null}}")
    private String pgHost;

    @Value("${PGPORT:5432}")
    private int pgPort;

    @Value("${PGDATABASE:#{null}}")
    private String pgDatabase;

    @Value("${PGUSER:#{null}}")
    private String pgUser;

    @Value("${PGPASSWORD:#{null}}")
    private String pgPassword;

    @Bean
    public DataSource dataSource() {
        if (pgHost == null || pgDatabase == null || pgUser == null) {
            throw new IllegalStateException(
                "PostgreSQL connection variables are not set. " +
                "Make sure to add PGHOST, PGDATABASE, PGUSER, PGPASSWORD variables " +
                "as references to your Railway backend service: " +
                "PGHOST=${{Postgres.PGHOST}}, etc."
            );
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://" + pgHost + ":" + pgPort + "/" + pgDatabase);
        config.setUsername(pgUser);
        config.setPassword(pgPassword);
        config.setDriverClassName("org.postgresql.Driver");

        return new HikariDataSource(config);
    }
}
