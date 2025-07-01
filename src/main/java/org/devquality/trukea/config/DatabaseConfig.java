package org.devquality.trukea.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig{

    private static final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);
    private static DatabaseConfig instance;
    private HikariDataSource dataSource;

    private DatabaseConfig(){
        setupDataSource();
        log.info("Loaded database configuration");
    }
    public static synchronized DatabaseConfig getInstance(){
        if(instance == null){
            instance = new DatabaseConfig();
        }
        return instance;
    }

    private void setupDataSource(){
        HikariConfig config = new HikariConfig();

        String url = "localhost";
        String port = "3306";
        String username = "root";
        String password = "Angelito7";
        String database = "bd_trukea";

        String jdbcUrl = String.format("jdbc:mysql://%s:%s/%s?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", url, port, database);

        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        config.setMaximumPoolSize(20);
        config.setMinimumIdle(5);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        config.setLeakDetectionThreshold(60000);

        Properties props = new Properties();
        props.setProperty("ApplicationName", "JavalinAPI");
        props.setProperty("useServerPrepStmts", "true");
        props.setProperty("cachePrepStmts", "true");
        props.setProperty("prepStmtCacheSize", "250");
        props.setProperty("prepStmtCacheSqlLimit", "2048");
        props.setProperty("useLocalSessionState", "true");
        props.setProperty("rewriteBatchedStatements", "true");
        props.setProperty("cacheResultSetMetadata", "true");
        props.setProperty("cacheServerConfiguration", "true");
        props.setProperty("elideSetAutoCommits", "true");
        props.setProperty("maintainTimeStats", "false");
        props.setProperty("useUnicode", "true");
        props.setProperty("characterEncoding", "UTF-8");
        props.setProperty("autoReconnect", "true");
        props.setProperty("failOverReadOnly", "false");
        props.setProperty("maxReconnects", "3");
        props.setProperty("initialTimeout", "2");
        config.setDataSourceProperties(props);


        config.setPoolName("JavalinAPI-HikariCP-MySQL");
        config.setConnectionTestQuery("SELECT 1");
        config.setValidationTimeout(5000);
        config.setRegisterMbeans(true);

        try {
            this.dataSource = new HikariDataSource(config);
            log.info("JDBC URL  se inicio el pool: {}", jdbcUrl);

            try (Connection connection = dataSource.getConnection()) {
                log.info("Se conecto a la bd: {}", jdbcUrl);
            }

        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("No se pudo conectar a la bd", e);
        }
    }

    public Connection getConnection() throws SQLException {
        if(dataSource == null || dataSource.isClosed()){
            throw new IllegalStateException("No se pudo conectar a la bd");
        }
        return dataSource.getConnection();
    }







}
