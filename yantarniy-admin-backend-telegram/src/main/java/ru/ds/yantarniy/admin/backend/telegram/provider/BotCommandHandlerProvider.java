package ru.ds.yantarniy.admin.backend.telegram.provider;

import ru.ds.yantarniy.admin.backend.telegram.handler.command.BotCommandHandler;

public interface BotCommandHandlerProvider {

    BotCommandHandler getBotCommandHandler(String message);
}
