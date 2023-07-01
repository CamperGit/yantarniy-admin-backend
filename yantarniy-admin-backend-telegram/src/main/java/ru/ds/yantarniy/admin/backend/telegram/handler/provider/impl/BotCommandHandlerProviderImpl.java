package ru.ds.yantarniy.admin.backend.telegram.handler.provider.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.ds.yantarniy.admin.backend.telegram.handler.command.BotCommandHandler;
import ru.ds.yantarniy.admin.backend.telegram.handler.command.DefaultCommandHandler;
import ru.ds.yantarniy.admin.backend.telegram.handler.provider.BotCommandHandlerProvider;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BotCommandHandlerProviderImpl implements BotCommandHandlerProvider {

    DefaultCommandHandler defaultCommandHandler;

    List<BotCommandHandler> commandHandlers;

    @Override
    public BotCommandHandler getBotCommandHandler(String message) {
        if (StringUtils.isEmpty(message)) {
            return defaultCommandHandler;
        }
        return commandHandlers.stream()
                .filter((handler) -> handler.isApplicable(message))
                .findFirst()
                .orElse(defaultCommandHandler);
    }
}
