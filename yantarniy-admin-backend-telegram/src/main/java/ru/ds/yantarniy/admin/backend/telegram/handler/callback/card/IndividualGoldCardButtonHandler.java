package ru.ds.yantarniy.admin.backend.telegram.handler.callback.card;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ds.yantarniy.admin.backend.telegram.bot.YantarniyTelegramBot;
import ru.ds.yantarniy.admin.backend.telegram.handler.callback.BotCallbackHandler;
import ru.ds.yantarniy.admin.backend.telegram.handler.callback.CallbackValue;
import ru.ds.yantarniy.admin.backend.telegram.service.MarkupService;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IndividualGoldCardButtonHandler implements BotCallbackHandler {

    MarkupService markupService;

    @Override
    public void handle(YantarniyTelegramBot bot, Update update) throws TelegramApiException {
        Message message = update.getCallbackQuery().getMessage();
        String chatId = message.getChatId().toString();

        String text = "<b><u>Индивидуальная GOLD</u></b>\n" +
                "1 месяц.\n" +
                "3 месяца.\n" +
                "6 месяцев.\n" +
                "12 месяцев.\n" +
                "\n<b>Время посещения\n</b>" +
                "<i>Рабочие дни:</i> с 7:00 до 23:00\n" +
                "<i>Выходные и праздничные дни:</i> с 9:00 до 23:00\n" +
                "\n<b>В клубную карту включено:</b>\n" +
                "1. Тренажерный зал\n" +
                "2. Зал групповых программ\n" +
                "3. Бассейн\n" +
                "4. Финская сауна\n" +
                "5. Турецкий хамам\n" +
                "6. Фитнес диагностика\n" +
                "7. Велнес консультация\n" +
                "8. Гостевой визит\n" +
                "9. Заморозка клубной карты\n" +
                "\n<b>Связаться с менеджером</b>: 2020302\n" +
                "<b>Чат с менеджером</b>: https://t.me/ManagerYantarniy";
        bot.deleteMessage(message);
        SendMessage messageToSend = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(markupService.getReturnMarkup(CallbackValue.OPEN_CLUB_CARDS.getValue(), true))
                .build();
        messageToSend.enableHtml(true);
        bot.execute(messageToSend);
    }

    @Override
    public boolean isApplicable(String callback) {
        return CallbackValue.OPEN_INDIVIDUAL_GOLD_CARD_INFO.getValue().equals(callback);
    }
}
