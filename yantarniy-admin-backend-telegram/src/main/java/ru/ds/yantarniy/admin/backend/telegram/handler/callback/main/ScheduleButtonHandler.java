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
public class ScheduleButtonHandler implements BotCallbackHandler {

    static String SCHEDULE_CHANGES_INIT_CALLBACK_FORMAT = "%s.(%s)";

    static String SCHEDULE_CURRENT_LABEL_MESSAGE_SOURCE = "schedule.current.label";

    static String SCHEDULE_CHANGES_LABEL_MESSAGE_SOURCE = "schedule.changes.label";

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

    @Override
    public boolean isApplicable(String callback) {
        return callback.equals(CallbackValue.OPEN_SCHEDULE.getValue());
    }

    private InlineKeyboardMarkup getSchedulesMenuMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton groupsSchedulesButton = new InlineKeyboardButton(localeMessageSource.getMessage(SCHEDULE_CURRENT_LABEL_MESSAGE_SOURCE));
        InlineKeyboardButton changesInSchedulesButton = new InlineKeyboardButton(localeMessageSource.getMessage(SCHEDULE_CHANGES_LABEL_MESSAGE_SOURCE));
        InlineKeyboardButton returnButton = new InlineKeyboardButton(localeMessageSource.getMessage(MOVE_BACK_MESSAGE_SOURCE));

        groupsSchedulesButton.setCallbackData(CallbackValue.CURRENT_SCHEDULE.getValue());
        changesInSchedulesButton.setCallbackData(String.format(SCHEDULE_CHANGES_INIT_CALLBACK_FORMAT, CallbackValue.SCHEDULE_CHANGES.getValue(), ScrollState.INIT.getValue()));
        returnButton.setCallbackData(CallbackValue.RETURN_MAIN_MENU.getValue());

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(groupsSchedulesButton);
        firstRow.add(changesInSchedulesButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(returnButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Arrays.asList(firstRow, secondRow));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
}
