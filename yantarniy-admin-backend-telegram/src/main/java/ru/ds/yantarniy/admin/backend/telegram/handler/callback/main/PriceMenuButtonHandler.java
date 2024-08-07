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
public class PriceMenuButtonHandler implements BotCallbackHandler {
    LocaleMessageSource localeMessageSource;
    static String SPA_SERVICES_MENU_MESSAGE_SOURCE = "spa.services.label";
    static String PRICE_INIT_CALLBACK_FORMAT = "%s.(%s)";

    @Override
    public void handle(YantarniyTelegramBot bot, Update update) throws TelegramApiException {
        Message message = update.getCallbackQuery().getMessage();
        String chatId = message.getChatId().toString();
        bot.deleteMessage(message);
        bot.execute(SendMessage.builder()
                .chatId(chatId)
                .text(localeMessageSource.getMessage(SPA_SERVICES_MENU_MESSAGE_SOURCE))
                .replyMarkup(getSpaServicesMenuMarkup())
                .build());
    }

    private InlineKeyboardMarkup getSpaServicesMenuMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton nailsButton = new InlineKeyboardButton(localeMessageSource.getMessage("spa.services.nails.label"));
        InlineKeyboardButton cosmetologyButton = new InlineKeyboardButton(localeMessageSource.getMessage("spa.services.cosmetology.label"));
        InlineKeyboardButton stylistsButton = new InlineKeyboardButton(localeMessageSource.getMessage("spa.services.stylists.label"));
        InlineKeyboardButton massageButton = new InlineKeyboardButton(localeMessageSource.getMessage("spa.services.massage.label"));
        InlineKeyboardButton bathhouseButton = new InlineKeyboardButton(localeMessageSource.getMessage("spa.services.bathhouse.label"));
        InlineKeyboardButton returnButton = new InlineKeyboardButton(localeMessageSource.getMessage("markup.back"));


        nailsButton.setCallbackData(String.format(PRICE_INIT_CALLBACK_FORMAT, CallbackValue.OPEN_SPA_PRICE_NAILS.getValue(), ScrollState.INIT.getValue()));
        cosmetologyButton.setCallbackData(String.format(PRICE_INIT_CALLBACK_FORMAT, CallbackValue.OPEN_SPA_PRICE_COSMETOLOGY.getValue(), ScrollState.INIT.getValue()));
        stylistsButton.setCallbackData(String.format(PRICE_INIT_CALLBACK_FORMAT, CallbackValue.OPEN_SPA_PRICE_STYLISTS.getValue(), ScrollState.INIT.getValue()));
        massageButton.setCallbackData(String.format(PRICE_INIT_CALLBACK_FORMAT, CallbackValue.OPEN_SPA_PRICE_MASSAGE.getValue(), ScrollState.INIT.getValue()));
        bathhouseButton.setCallbackData(String.format(PRICE_INIT_CALLBACK_FORMAT, CallbackValue.OPEN_SPA_PRICE_BATHHOUSE.getValue(), ScrollState.INIT.getValue()));
        returnButton.setCallbackData(CallbackValue.RETURN_MAIN_MENU.getValue());

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(nailsButton);
        firstRow.add(cosmetologyButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(stylistsButton);
        secondRow.add(massageButton);
        secondRow.add(bathhouseButton);

        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        thirdRow.add(returnButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Arrays.asList(firstRow, secondRow, thirdRow));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    @Override
    public boolean isApplicable(String callback) {
        return CallbackValue.PRICE_SPA.getValue().equals(callback);
    }
}
