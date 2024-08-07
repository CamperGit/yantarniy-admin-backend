package ru.ds.yantarniy.admin.backend.telegram.handler.callback.main;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ds.yantarniy.admin.backend.core.locale.LocaleMessageSource;
import ru.ds.yantarniy.admin.backend.telegram.bot.YantarniyTelegramBot;
import ru.ds.yantarniy.admin.backend.telegram.handler.callback.BotCallbackHandler;
import ru.ds.yantarniy.admin.backend.telegram.handler.callback.CallbackValue;
import ru.ds.yantarniy.admin.backend.telegram.service.MarkupService;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CallManagerButtonHandler implements BotCallbackHandler {

    static String CALL_MANAGER_MESSAGE_SOURCE = "main.call-manager.text";

    LocaleMessageSource localeMessageSource;

    MarkupService markupService;

    @Override
    public void handle(YantarniyTelegramBot bot, Update update) throws TelegramApiException {
        Message message = update.getCallbackQuery().getMessage();
        bot.changeMessage(
                message,
                localeMessageSource.getMessage(CALL_MANAGER_MESSAGE_SOURCE),
                markupService.getReturnMarkup(CallbackValue.RETURN_MAIN_MENU.getValue(), false)
        );
    }

    @Override
    public boolean isApplicable(String callback) {
        return callback.equals(CallbackValue.CALL_MANAGER.getValue());
    }
}
