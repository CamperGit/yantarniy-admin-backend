package ru.ds.yantarniy.admin.backend.core.locale;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.ds.yantarniy.admin.backend.core.property.LocaleProperties;

import java.util.Locale;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LocaleMessageSource {

    Locale locale;

    MessageSource messageSource;

    public LocaleMessageSource(LocaleProperties localeProperties, MessageSource messageSource) {
        this.locale = Locale.forLanguageTag(localeProperties.getTag());
        this.messageSource = messageSource;
    }

    public String getMessage(String key) {
        return messageSource.getMessage(key, null, locale);
    }
}
