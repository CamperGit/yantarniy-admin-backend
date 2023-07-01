package ru.ds.yantarniy.admin.backend.telegram.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.ds.yantarniy.admin.backend.core.config.CoreConfiguration;

@Import({
        CoreConfiguration.class
})
public class TelegramConfiguration {
}
