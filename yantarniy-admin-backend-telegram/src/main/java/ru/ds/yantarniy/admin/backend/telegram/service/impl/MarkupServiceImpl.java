package ru.ds.yantarniy.admin.backend.telegram.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.ds.yantarniy.admin.backend.core.locale.LocaleMessageSource;
import ru.ds.yantarniy.admin.backend.telegram.handler.callback.CallbackValue;
import ru.ds.yantarniy.admin.backend.telegram.service.MarkupService;
import ru.ds.yantarniy.admin.backend.telegram.model.ScrollState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MarkupServiceImpl implements MarkupService {

    static int MIN_POSITION_NUMBER = 1;

    static String SCROLL_CALLBACK_FORMAT = "%s.(%s).[%d].{%d}";

    static String COUNT_FORMAT = "%d/%d";

    static String MARKUP_SCROLL_PREV_MESSAGE_SOURCE = "markup.scroll.prev";

    static String MARKUP_SCROLL_NEXT_MESSAGE_SOURCE = "markup.scroll.next";

    static String MARKUP_BACK_MESSAGE_SOURCE = "markup.back";

    static String MARKUP_MAIN_MENU_MESSAGE_SOURCE = "markup.main-menu";

    LocaleMessageSource localeMessageSource;

    @Override
    public InlineKeyboardMarkup getScrollMenuMarkup(long numberOfItems, long currentItemId, long currentItem, String callbackBaseData, String exitButtonCallbackData, String textOfContactUsButton, String contactUsCallbackData) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton prevButton = new InlineKeyboardButton(localeMessageSource.getMessage(MARKUP_SCROLL_PREV_MESSAGE_SOURCE));
        InlineKeyboardButton countButton = new InlineKeyboardButton(String.format(COUNT_FORMAT, currentItem, numberOfItems));
        InlineKeyboardButton nextButton = new InlineKeyboardButton(localeMessageSource.getMessage(MARKUP_SCROLL_NEXT_MESSAGE_SOURCE));
        InlineKeyboardButton returnButton = new InlineKeyboardButton(localeMessageSource.getMessage(MARKUP_BACK_MESSAGE_SOURCE));
        InlineKeyboardButton mainMenuButton = new InlineKeyboardButton(localeMessageSource.getMessage(MARKUP_MAIN_MENU_MESSAGE_SOURCE));

        prevButton.setCallbackData(getScrollCallbackValueFromState(ScrollState.PREVIOUS, callbackBaseData, currentItemId, currentItem));
        nextButton.setCallbackData(getScrollCallbackValueFromState(ScrollState.NEXT, callbackBaseData, currentItemId, currentItem));
        countButton.setCallbackData("null");
        returnButton.setCallbackData(exitButtonCallbackData);
        mainMenuButton.setCallbackData(CallbackValue.RETURN_MAIN_MENU.getValue());

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(prevButton);
        firstRow.add(countButton);
        firstRow.add(nextButton);

        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        thirdRow.add(returnButton);
        thirdRow.add(mainMenuButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        rowList.add(firstRow);
        if (textOfContactUsButton != null && contactUsCallbackData != null) {
            InlineKeyboardButton contactUsButton = new InlineKeyboardButton(textOfContactUsButton);
            contactUsButton.setCallbackData(contactUsCallbackData);
            List<InlineKeyboardButton> secondRow = new ArrayList<>();
            secondRow.add(contactUsButton);
            rowList.add(secondRow);
        }
        rowList.add(thirdRow);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    private String getScrollCallbackValueFromState(ScrollState state, String baseCallBack, long currentItemId, long currentPosition) {
        return String.format(SCROLL_CALLBACK_FORMAT, baseCallBack, state.getValue(), currentItemId, currentPosition);
    }

    @Override
    public InlineKeyboardMarkup getReturnMarkup(String exitCallbackData, boolean createMainMenuButton) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton exitButton = new InlineKeyboardButton(localeMessageSource.getMessage(MARKUP_BACK_MESSAGE_SOURCE));
        exitButton.setCallbackData(exitCallbackData);
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(exitButton);
        if (createMainMenuButton) {
            InlineKeyboardButton mainMenuButton = new InlineKeyboardButton(localeMessageSource.getMessage(MARKUP_MAIN_MENU_MESSAGE_SOURCE));
            mainMenuButton.setCallbackData(CallbackValue.RETURN_MAIN_MENU.getValue());
            firstRow.add(mainMenuButton);
        }
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Collections.singletonList(firstRow));
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}
