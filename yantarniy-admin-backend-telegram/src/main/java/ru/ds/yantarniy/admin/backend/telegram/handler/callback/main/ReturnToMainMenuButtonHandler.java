package ru.ds.yantarniy.admin.backend.telegram.handler.callback.main;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ds.yantarniy.admin.backend.telegram.bot.YantarniyTelegramBot;
import ru.ds.yantarniy.admin.backend.telegram.handler.callback.BotCallbackHandler;
import ru.ds.yantarniy.admin.backend.telegram.handler.callback.CallbackValue;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReturnToMainMenuButtonHandler implements BotCallbackHandler {

    @Override
    public void handle(YantarniyTelegramBot bot, Update update) throws TelegramApiException {
        Message message = update.getCallbackQuery().getMessage();
        bot.moveToMainMenu(message.getChatId().toString(), message.getMessageId());
    }

    @Override
    public boolean isApplicable(String callback) {
        return callback.equals(CallbackValue.RETURN_MAIN_MENU.getValue());
    }
}
