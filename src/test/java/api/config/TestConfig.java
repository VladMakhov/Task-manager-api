package api.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;

/**
 * Configuration of test container (MySQL)
 * */
@TestConfiguration
public class TestConfig {

    @Bean
    @ServiceConnection
    public MySQLContainer<?> mySQLContainer() {
        return new MySQLContainer<>("mysql:latest");
    }
}
