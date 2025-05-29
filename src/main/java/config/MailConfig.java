package config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "spring.mail.host")
public class MailConfig {
    // Spring Boot will auto-configure JavaMailSender if mail properties are present
    // This class just ensures the configuration is loaded when mail properties exist
}
