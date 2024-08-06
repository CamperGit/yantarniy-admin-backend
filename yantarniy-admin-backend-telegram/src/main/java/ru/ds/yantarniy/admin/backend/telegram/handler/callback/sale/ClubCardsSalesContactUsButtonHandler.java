package ru.ds.yantarniy.admin.backend.telegram.handler.callback.sale;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
public class ClubCardsSalesContactUsButtonHandler implements BotCallbackHandler {
    static String CLUB_CARDS_CONTACT_US_MESSAGE_SOURCE = "sales.club-cards.contact-us.text";
    LocaleMessageSource localeMessageSource;
    MarkupService markupService;


    @Override
    public void handle(YantarniyTelegramBot bot, Update update) throws TelegramApiException {
        Message message = update.getCallbackQuery().getMessage();
        String chatId = message.getChatId().toString();
        bot.deleteMessage(message);
        bot.execute(SendMessage.builder()
                .chatId(chatId)
                .text(localeMessageSource.getMessage(CLUB_CARDS_CONTACT_US_MESSAGE_SOURCE))
                .replyMarkup(markupService.getReturnMarkup(CallbackValue.OPEN_SALES.getValue(), true))
                .build());
    }

    @Override
    public boolean isApplicable(String callback) {
        return CallbackValue.OPEN_CLUB_CARDS_CONTACT_US.getValue().equals(callback);
    }
}
