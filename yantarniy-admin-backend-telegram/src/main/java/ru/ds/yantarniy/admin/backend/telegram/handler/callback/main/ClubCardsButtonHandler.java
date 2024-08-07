package ru.ds.yantarniy.admin.backend.telegram.handler.callback.main;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClubCardsButtonHandler implements BotCallbackHandler {
    static String CLUB_CARDS_MENU_MESSAGE_SOURCE = "club-cards.menu";
    LocaleMessageSource localeMessageSource;


    @Override
    public void handle(YantarniyTelegramBot bot, Update update) throws TelegramApiException {
        Message message = update.getCallbackQuery().getMessage();
        String chatId = message.getChatId().toString();
        bot.deleteMessage(message);
        bot.execute(SendMessage.builder()
                .chatId(chatId)
                .text(localeMessageSource.getMessage(CLUB_CARDS_MENU_MESSAGE_SOURCE))
                .replyMarkup(getCardTypesMarkup())
                .build());
    }

    private InlineKeyboardMarkup getCardTypesMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton individualGoldButton = new InlineKeyboardButton(localeMessageSource.getMessage("club-card.type.individual-gold"));
        InlineKeyboardButton dayGoldButton = new InlineKeyboardButton(localeMessageSource.getMessage("club-card.type.day-gold"));
        InlineKeyboardButton weekendButton = new InlineKeyboardButton(localeMessageSource.getMessage("club-card.type.weekend"));
        InlineKeyboardButton poolButton = new InlineKeyboardButton(localeMessageSource.getMessage("club-card.type.pool"));
        InlineKeyboardButton returnButton = new InlineKeyboardButton(localeMessageSource.getMessage("main.move-back"));

        individualGoldButton.setCallbackData(CallbackValue.OPEN_INDIVIDUAL_GOLD_CARD_INFO.getValue());
        dayGoldButton.setCallbackData(CallbackValue.OPEN_DAY_GOLD_CARD_INFO.getValue());
        weekendButton.setCallbackData(CallbackValue.OPEN_WEEKEND_CARD_INFO.getValue());
        poolButton.setCallbackData(CallbackValue.OPEN_POOL_CARD_INFO.getValue());
        returnButton.setCallbackData(CallbackValue.RETURN_MAIN_MENU.getValue());

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(individualGoldButton);
        firstRow.add(dayGoldButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(weekendButton);
        secondRow.add(poolButton);

        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        thirdRow.add(returnButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Arrays.asList(firstRow, secondRow, thirdRow));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    @Override
    public boolean isApplicable(String callback) {
        return CallbackValue.OPEN_CLUB_CARDS.getValue().equals(callback);
    }
}
