package ru.ds.yantarniy.admin.backend.telegram.handler.command;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ds.yantarniy.admin.backend.core.locale.LocaleMessageSource;
import ru.ds.yantarniy.admin.backend.telegram.bot.YantarniyTelegramBot;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DefaultCommandHandler implements BotCommandHandler {

    static String UNKNOWN_COMMAND_MESSAGE_SOURCE = "main.unknown-non-command-message";

    LocaleMessageSource localeMessageSource;

    @Override
    public void handle(YantarniyTelegramBot bot, Update update) throws TelegramApiException {
        String chatId = update.getMessage().getChatId().toString();
        bot.sendMainMenuMessage(chatId, localeMessageSource.getMessage(UNKNOWN_COMMAND_MESSAGE_SOURCE));
    }

    @Override
    public boolean isApplicable(String message) {
        return false;
    }
}
