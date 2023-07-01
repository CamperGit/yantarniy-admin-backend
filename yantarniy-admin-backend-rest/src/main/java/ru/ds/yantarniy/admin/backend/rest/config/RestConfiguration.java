package ru.ds.yantarniy.admin.backend.rest.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.ds.yantarniy.admin.backend.telegram.config.TelegramConfiguration;

@Configuration
@Import({
        TelegramConfiguration.class,
        SwaggerConfiguration.class
})
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class RestConfiguration {
}