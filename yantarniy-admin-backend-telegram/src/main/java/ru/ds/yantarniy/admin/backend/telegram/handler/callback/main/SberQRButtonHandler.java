package ru.ds.yantarniy.admin.backend.telegram.handler.callback.main;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ds.yantarniy.admin.backend.core.locale.LocaleMessageSource;
import ru.ds.yantarniy.admin.backend.telegram.bot.YantarniyTelegramBot;
import ru.ds.yantarniy.admin.backend.telegram.handler.callback.BotCallbackHandler;
import ru.ds.yantarniy.admin.backend.telegram.handler.callback.CallbackValue;
import ru.ds.yantarniy.admin.backend.telegram.util.MarkupUtils;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SberQRButtonHandler implements BotCallbackHandler {

    static String SBERQR_PNG = "main/sberqr.png";

    static String SBERQR_FILENAME = "sberqr.png";

    static String SBER_QR_MESSAGE_SOURCE = "main.sberqr.text";

    LocaleMessageSource localeMessageSource;

    @Override
    public void handle(YantarniyTelegramBot bot, Update update) throws TelegramApiException {
        Message message = update.getCallbackQuery().getMessage();
        String chatId = message.getChatId().toString();

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(Thread.currentThread().getContextClassLoader().getResourceAsStream(SBERQR_PNG), SBERQR_FILENAME));
        sendPhoto.setCaption(localeMessageSource.getMessage(SBER_QR_MESSAGE_SOURCE));
        sendPhoto.setReplyMarkup(MarkupUtils.getReturnMarkup(CallbackValue.RETURN_MAIN_MENU.getValue(), false));

        bot.deleteMessage(chatId, message.getMessageId());
        bot.execute(sendPhoto);
    }

    @Override
    public boolean isApplicable(String callback) {
        return callback.equals(CallbackValue.SBER_QR.getValue());
    }
}
