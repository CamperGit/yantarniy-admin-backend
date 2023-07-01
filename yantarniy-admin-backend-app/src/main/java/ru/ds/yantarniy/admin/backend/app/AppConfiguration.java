package ru.ds.yantarniy.admin.backend.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.ds.yantarniy.admin.backend.rest.config.RestConfiguration;
import ru.ds.yantarniy.admin.backend.telegram.config.TelegramConfiguration;

@Configuration
@Import({
        RestConfiguration.class
})
public class AppConfiguration {}