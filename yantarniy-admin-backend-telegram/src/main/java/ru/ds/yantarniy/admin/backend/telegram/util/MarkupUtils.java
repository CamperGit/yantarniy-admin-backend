package ru.ds.yantarniy.admin.backend.telegram.util;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.ds.yantarniy.admin.backend.telegram.handler.callback.CallbackValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class MarkupUtils {

    public static InlineKeyboardMarkup getScrollMenuMarkup(int numberOfItems, int currentItem,
                                                    String prevButCallbackData, String nextButCallbackData, String exitButCallbackData,
                                                    String textOfContactUsButton, String contactUsCallbackData) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton prevButton = new InlineKeyboardButton("<--");
        InlineKeyboardButton countButton = new InlineKeyboardButton((numberOfItems == 0 ? 0 : currentItem) + "/" + numberOfItems);
        InlineKeyboardButton nextButton = new InlineKeyboardButton("-->");
        InlineKeyboardButton returnButton = new InlineKeyboardButton("Назад");
        InlineKeyboardButton mainMenuButton = new InlineKeyboardButton("В главное меню");

        prevButton.setCallbackData(prevButCallbackData);
        nextButton.setCallbackData(nextButCallbackData);
        countButton.setCallbackData("null");
        returnButton.setCallbackData(exitButCallbackData);
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

    public static InlineKeyboardMarkup getReturnMarkup(String exitCallbackData, boolean createMainMenuButton) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton exitButton = new InlineKeyboardButton("Назад");
        exitButton.setCallbackData(exitCallbackData);
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(exitButton);
        if (createMainMenuButton) {
            InlineKeyboardButton mainMenuButton = new InlineKeyboardButton("В главное меню");
            mainMenuButton.setCallbackData(CallbackValue.RETURN_MAIN_MENU.getValue());
            firstRow.add(mainMenuButton);
        }
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Collections.singletonList(firstRow));
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}
