package ru.ds.yantarniy.admin.backend.telegram.handler.callback.employee.coach;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okhttp3.Call;
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
public class CoachButtonHandler implements BotCallbackHandler {

    static String EMPLOYEE_COACHES_MENU_MESSAGE_SOURCE = "employee.coaches.menu";
    static String COACH_INIT_CALLBACK_FORMAT = "%s.(%s)";

    LocaleMessageSource localeMessageSource;


    @Override
    public void handle(YantarniyTelegramBot bot, Update update) throws TelegramApiException {
        Message message = update.getCallbackQuery().getMessage();
        String chatId = message.getChatId().toString();
        bot.deleteMessage(message);
        bot.execute(SendMessage.builder()
                .chatId(chatId)
                .text(localeMessageSource.getMessage(EMPLOYEE_COACHES_MENU_MESSAGE_SOURCE))
                .replyMarkup(getCoachesMenuMarkup())
                .build());
    }

    private InlineKeyboardMarkup getCoachesMenuMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton gymButton = new InlineKeyboardButton(localeMessageSource.getMessage("fitness.coaches.gym"));
        InlineKeyboardButton groupsButton = new InlineKeyboardButton(localeMessageSource.getMessage("fitness.coaches.groups"));
        InlineKeyboardButton poolButton = new InlineKeyboardButton(localeMessageSource.getMessage("fitness.coaches.pool"));
        InlineKeyboardButton returnButton = new InlineKeyboardButton(localeMessageSource.getMessage("markup.back"));
        InlineKeyboardButton mainMenuButton = new InlineKeyboardButton(localeMessageSource.getMessage("markup.main-menu"));

        groupsButton.setCallbackData(String.format(COACH_INIT_CALLBACK_FORMAT, CallbackValue.OPEN_FITNESS_GROUP_COACHES.getValue(), ScrollState.INIT.getValue()));
        poolButton.setCallbackData(String.format(COACH_INIT_CALLBACK_FORMAT, CallbackValue.OPEN_POOL_COACHES.getValue(), ScrollState.INIT.getValue()));
        gymButton.setCallbackData(String.format(COACH_INIT_CALLBACK_FORMAT, CallbackValue.OPEN_GYM_COACHES.getValue(), ScrollState.INIT.getValue()));
        returnButton.setCallbackData(CallbackValue.EMPLOYEES.getValue());
        mainMenuButton.setCallbackData(CallbackValue.RETURN_MAIN_MENU.getValue());

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(gymButton);
        firstRow.add(poolButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(groupsButton);

        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        thirdRow.add(returnButton);
        thirdRow.add(mainMenuButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Arrays.asList(firstRow, secondRow, thirdRow));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    @Override
    public boolean isApplicable(String callback) {
        return callback.equals(CallbackValue.OPEN_COACHES.getValue());
    }
}
