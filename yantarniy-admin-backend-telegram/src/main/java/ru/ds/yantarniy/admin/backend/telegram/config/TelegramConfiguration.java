package ru.ds.yantarniy.admin.backend.telegram.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.ds.yantarniy.admin.backend.core.config.CoreConfiguration;

import java.nio.charset.StandardCharsets;

@Import({
        CoreConfiguration.class,
        BotConfiguration.class
})
@ComponentScan(basePackages = "ru.ds.yantarniy.admin.backend.telegram")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TelegramConfiguration {

    static String REPLIES_PATH = "classpath:replies";

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(REPLIES_PATH);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        return messageSource;
    }
}
