package ru.ds.yantarniy.admin.backend.telegram.handler.provider;

import ru.ds.yantarniy.admin.backend.telegram.handler.callback.BotCallbackHandler;

public interface BotCallbackHandlerProvider {

    BotCallbackHandler getBotCallbackHandler(String callback);
}
