package ru.ds.yantarniy.admin.backend.telegram.handler.callback.employee.spa;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
public class SpaButtonHandler implements BotCallbackHandler {

    static String EMPLOYEE_SPA_MENU_MESSAGE_SOURCE = "spa.specialists.menu";
    static String SPA_INIT_CALLBACK_FORMAT = "%s.(%s)";
    LocaleMessageSource localeMessageSource;

    @Override
    public void handle(YantarniyTelegramBot bot, Update update) throws TelegramApiException {
        Message message = update.getCallbackQuery().getMessage();
        String chatId = message.getChatId().toString();
        bot.deleteMessage(message);
        bot.execute(SendMessage.builder()
                .chatId(chatId)
                .text(localeMessageSource.getMessage(EMPLOYEE_SPA_MENU_MESSAGE_SOURCE))
                .replyMarkup(getSpaMenuMarkup())
                .build());
    }

    private InlineKeyboardMarkup getSpaMenuMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton nailsButton = new InlineKeyboardButton(localeMessageSource.getMessage("spa.specialists.nails"));
        InlineKeyboardButton massageButton = new InlineKeyboardButton(localeMessageSource.getMessage("spa.specialists.massage"));
        InlineKeyboardButton cosmetologyButton = new InlineKeyboardButton(localeMessageSource.getMessage("spa.specialists.cosmetology"));
        InlineKeyboardButton stylistsButton = new InlineKeyboardButton(localeMessageSource.getMessage("spa.specialists.stylists"));
        InlineKeyboardButton returnButton = new InlineKeyboardButton(localeMessageSource.getMessage("markup.back"));
        InlineKeyboardButton mainMenuButton = new InlineKeyboardButton(localeMessageSource.getMessage("markup.main-menu"));

        nailsButton.setCallbackData(String.format(SPA_INIT_CALLBACK_FORMAT, CallbackValue.OPEN_NAILS_SPA_SPECIALISTS.getValue(), ScrollState.INIT.getValue()));
        massageButton.setCallbackData(String.format(SPA_INIT_CALLBACK_FORMAT, CallbackValue.OPEN_MASSAGE_SPA_SPECIALISTS.getValue(), ScrollState.INIT.getValue()));
        cosmetologyButton.setCallbackData(String.format(SPA_INIT_CALLBACK_FORMAT, CallbackValue.OPEN_COSMETOLOGY_SPA_SPECIALISTS.getValue(), ScrollState.INIT.getValue()));
        stylistsButton.setCallbackData(String.format(SPA_INIT_CALLBACK_FORMAT, CallbackValue.OPEN_STYLISTS_SPA_SPECIALISTS.getValue(), ScrollState.INIT.getValue()));
        returnButton.setCallbackData(CallbackValue.EMPLOYEES.getValue());
        mainMenuButton.setCallbackData(CallbackValue.RETURN_MAIN_MENU.getValue());

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(nailsButton);
        firstRow.add(massageButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(cosmetologyButton);
        secondRow.add(stylistsButton);

        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        thirdRow.add(returnButton);
        thirdRow.add(mainMenuButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Arrays.asList(firstRow, secondRow,thirdRow));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    @Override
    public boolean isApplicable(String callback) {
        return CallbackValue.OPEN_SPECIALISTS.getValue().contains(callback);
    }
}
