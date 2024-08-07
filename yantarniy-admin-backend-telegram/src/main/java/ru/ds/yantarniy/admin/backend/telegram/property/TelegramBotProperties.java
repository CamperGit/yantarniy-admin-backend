package ru.ds.yantarniy.admin.backend.telegram.property;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Data
@Validated
@Configuration
@ConfigurationProperties("telegram.bot")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramBotProperties {

    @NotEmpty(message = "Telegram bot username property not found")
    String username;

    @NotEmpty(message = "Telegram bot token property not found")
    String token;
}
