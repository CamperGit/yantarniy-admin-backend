package ru.ds.yantarniy.admin.backend.telegram.handler.callback.main;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ds.yantarniy.admin.backend.core.locale.LocaleMessageSource;
import ru.ds.yantarniy.admin.backend.telegram.bot.YantarniyTelegramBot;
import ru.ds.yantarniy.admin.backend.telegram.handler.callback.BotCallbackHandler;
import ru.ds.yantarniy.admin.backend.telegram.handler.callback.CallbackValue;
import ru.ds.yantarniy.admin.backend.telegram.model.ScrollState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SalesMenuButtonHandler implements BotCallbackHandler {

    LocaleMessageSource localeMessageSource;
    static String SALES_MENU_MESSAGE_SOURCE = "sales.menu";
    static String SALES_INIT_CALLBACK_FORMAT = "%s.(%s)";


    @Override
    public void handle(YantarniyTelegramBot bot, Update update) throws TelegramApiException {
        Message message = update.getCallbackQuery().getMessage();
        String chatId = message.getChatId().toString();
        bot.deleteMessage(message);
        bot.execute(SendMessage.builder()
                .chatId(chatId)
                .text(localeMessageSource.getMessage(SALES_MENU_MESSAGE_SOURCE))
                .replyMarkup(getSalesMenuMarkup())
                .build());
    }

    private InlineKeyboardMarkup getSalesMenuMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton clubCardSalesButton = new InlineKeyboardButton(localeMessageSource.getMessage("sales.club-cards.label"));
        InlineKeyboardButton spaSalesButton = new InlineKeyboardButton(localeMessageSource.getMessage("sales.spa.label"));
        InlineKeyboardButton returnButton = new InlineKeyboardButton(localeMessageSource.getMessage("main.move-back"));

        clubCardSalesButton.setCallbackData(String.format(SALES_INIT_CALLBACK_FORMAT, CallbackValue.OPEN_CLUB_CARDS_SALES.getValue(), ScrollState.INIT.getValue()));
        spaSalesButton.setCallbackData(String.format(SALES_INIT_CALLBACK_FORMAT, CallbackValue.OPEN_SPA_SALES.getValue(), ScrollState.INIT.getValue()));
        returnButton.setCallbackData(CallbackValue.RETURN_MAIN_MENU.getValue());

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(clubCardSalesButton);
        firstRow.add(spaSalesButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(returnButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Arrays.asList(firstRow, secondRow));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    @Override
    public boolean isApplicable(String callback) {
        return CallbackValue.OPEN_SALES.getValue().equals(callback);
    }
}
