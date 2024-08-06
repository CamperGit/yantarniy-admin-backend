package ru.ds.yantarniy.admin.backend.telegram.handler.callback.main;

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
public class EmployeeButtonHandler implements BotCallbackHandler {

    static String FITNESS_COACHES_LABEL_MESSAGE_SOURCE = "employee.coaches.label";

    static String SPA_SPECIALISTS_LABEL_MESSAGE_SOURCE = "employee.specialists.label";

    static String MOVE_BACK_MESSAGE_SOURCE = "main.move-back";

    static String SCHEDULE_TEXT_MESSAGE_SOURCE = "main.schedule.text";

    LocaleMessageSource localeMessageSource;

    @Override
    public void handle(YantarniyTelegramBot bot, Update update) throws TelegramApiException {
        Message message = update.getCallbackQuery().getMessage();
        String chatId = message.getChatId().toString();
        bot.deleteMessage(message);
        bot.execute(SendMessage.builder()
                .chatId(chatId)
                .text(localeMessageSource.getMessage(SCHEDULE_TEXT_MESSAGE_SOURCE))
                .replyMarkup(getSchedulesMenuMarkup())
                .build());
    }

    private InlineKeyboardMarkup getSchedulesMenuMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton coachesButton = new InlineKeyboardButton(localeMessageSource.getMessage(FITNESS_COACHES_LABEL_MESSAGE_SOURCE));
        InlineKeyboardButton specialistsButton = new InlineKeyboardButton(localeMessageSource.getMessage(SPA_SPECIALISTS_LABEL_MESSAGE_SOURCE));
        InlineKeyboardButton exitButton = new InlineKeyboardButton(localeMessageSource.getMessage(MOVE_BACK_MESSAGE_SOURCE));

        coachesButton.setCallbackData(CallbackValue.OPEN_COACHES.getValue());
        specialistsButton.setCallbackData(CallbackValue.OPEN_SPECIALISTS.getValue());
        exitButton.setCallbackData(CallbackValue.RETURN_MAIN_MENU.getValue());

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(coachesButton);
        firstRow.add(specialistsButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(exitButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Arrays.asList(firstRow, secondRow));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    @Override
    public boolean isApplicable(String callback) {
        return callback.equals(CallbackValue.EMPLOYEES.getValue());
    }
}
