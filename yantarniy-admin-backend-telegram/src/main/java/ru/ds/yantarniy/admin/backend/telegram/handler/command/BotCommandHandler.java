package ru.ds.yantarniy.admin.backend.telegram.handler.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ds.yantarniy.admin.backend.telegram.bot.YantarniyTelegramBot;

public interface BotCommandHandler {

    void handle(YantarniyTelegramBot bot, Update update) throws TelegramApiException;

    boolean isApplicable(String message);
}
