package ru.ds.yantarniy.admin.backend.telegram.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface MarkupService {

    InlineKeyboardMarkup getScrollMenuMarkup(long numberOfItems, long currentItemId, long currentItem, String callbackBaseData,
                                             String exitButtonCallbackData, String textOfContactUsButton, String contactUsCallbackData);

    InlineKeyboardMarkup getReturnMarkup(String exitCallbackData, boolean createMainMenuButton);
}
