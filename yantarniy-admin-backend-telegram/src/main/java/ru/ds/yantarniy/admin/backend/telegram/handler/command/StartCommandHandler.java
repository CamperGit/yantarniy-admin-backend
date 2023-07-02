package ru.ds.yantarniy.admin.backend.telegram.handler.command;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ds.yantarniy.admin.backend.core.customer.service.CustomerService;
import ru.ds.yantarniy.admin.backend.core.locale.LocaleMessageSource;
import ru.ds.yantarniy.admin.backend.dao.entity.customer.CustomerEntity;
import ru.ds.yantarniy.admin.backend.telegram.bot.YantarniyTelegramBot;
import ru.ds.yantarniy.admin.backend.telegram.exception.YantarniyBotTelegramException;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StartCommandHandler implements BotCommandHandler {

    static String MAIN_MENU_MENU_LABEL_MESSAGE_SOURCE = "main.label";

    static String START_COMMAND = "/start";

    static String MAIN_MENU_COMMAND = "Главное меню";

    CustomerService customerService;

    LocaleMessageSource localeMessageSource;

    @Override
    public void handle(YantarniyTelegramBot bot, Update update) throws TelegramApiException {
        Message message = update.getMessage();
        String chatId = message.getChatId().toString();
        Optional<CustomerEntity> customer = customerService.findByChatId(chatId);
        customer.ifPresentOrElse((c) -> {
            c.setLastEntry(LocalDateTime.now());
            customerService.save(c);
        }, () -> {
            User user = message.getFrom();
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String username = user.getUserName();
            CustomerEntity newCustomer = CustomerEntity.builder()
                    .chatId(chatId)
                    .firstname(firstName)
                    .lastname(lastName)
                    .username(username)
                    .lastEntry(LocalDateTime.now())
                    .build();
            customerService.save(newCustomer);
            try {
                bot.sendReplyMarkup(chatId);
            } catch (TelegramApiException e) {
                throw new YantarniyBotTelegramException("Error while trying to get info about customer", e);
            }
        });
        bot.sendMainMenuMessage(chatId, localeMessageSource.getMessage(MAIN_MENU_MENU_LABEL_MESSAGE_SOURCE));
    }

    @Override
    public boolean isApplicable(String message) {
        return message.equals(START_COMMAND) || message.equals(MAIN_MENU_COMMAND);
    }
}
