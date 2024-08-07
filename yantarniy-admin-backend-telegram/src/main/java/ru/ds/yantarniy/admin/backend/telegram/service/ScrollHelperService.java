package ru.ds.yantarniy.admin.backend.telegram.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ds.yantarniy.admin.backend.core.search.SpecificationsSearchService;
import ru.ds.yantarniy.admin.backend.dao.entity.file.FileEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.schedule.ScheduleEntity;
import ru.ds.yantarniy.admin.backend.telegram.bot.YantarniyTelegramBot;
import ru.ds.yantarniy.admin.backend.telegram.exception.YantarniyBotTelegramException;
import ru.ds.yantarniy.admin.backend.telegram.handler.callback.CallbackValue;
import ru.ds.yantarniy.admin.backend.telegram.handler.scroll.ScrollStateHandler;
import ru.ds.yantarniy.admin.backend.telegram.model.ScrollResponse;
import ru.ds.yantarniy.admin.backend.telegram.model.ScrollState;
import ru.ds.yantarniy.admin.backend.telegram.provider.ScrollStateHandlerProvider;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ScrollHelperService<T> {

    ScrollStateHandler<T> getScrollStateHandler(ScrollState scrollState);

    SpecificationsSearchService<T> getSpecificationSearchService();

    default List<Specification<T>> getAdditionalSpecifications() {
        return new ArrayList<>();
    }

    default void handleScroll(YantarniyTelegramBot bot, Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String data = callbackQuery.getData();

        ScrollState scrollState = ScrollState.getScrollStateFromStringWithRegex(data);
        ScrollStateHandler<T> scrollHandler = getScrollStateHandler(scrollState);

        List<Specification<T>> additionalSpecifications = getAdditionalSpecifications();
        SpecificationsSearchService<T> specificationSearchService = getSpecificationSearchService();

        ScrollResponse<T> scrollResponse = scrollHandler.getEntityFromData(data, additionalSpecifications, specificationSearchService);
        if (scrollHandler.isCanScroll(scrollResponse, scrollState)) {
            Optional.ofNullable(scrollResponse.getValue()).ifPresentOrElse(
                    (entity) -> handleNotEmptyScrollResponse(scrollResponse, bot, callbackQuery),
                    () -> handleEmptyScrollResponse(callbackQuery, bot)
            );
        }
    }

    default void handleEmptyScrollResponse(CallbackQuery callbackQuery, YantarniyTelegramBot bot) {
        Message message = callbackQuery.getMessage();
        String chatId = message.getChatId().toString();
        String emptyScrollResponseAnswerMessage = getEmptyScrollResponseAnswerMessage();
        InlineKeyboardMarkup emptyScrollResponseReplyKeyboard = getEmptyScrollResponseReplyKeyboard();

        SendMessage sendMessage = new SendMessage(chatId, emptyScrollResponseAnswerMessage);
        sendMessage.setReplyMarkup(emptyScrollResponseReplyKeyboard);
        try {
            bot.execute(sendMessage);
            bot.deleteMessage(message);
        } catch (TelegramApiException e) {
            throw new YantarniyBotTelegramException(
                    String.format(
                            "Exception while trying to scroll empty item for callback value = [%s]",
                            callbackQuery.getData()
                    ),
                    e
            );
        }
    }

    String getEmptyScrollResponseAnswerMessage();

    InlineKeyboardMarkup getEmptyScrollResponseReplyKeyboard();

    default void handleNotEmptyScrollResponse(ScrollResponse<T> scrollResponse, YantarniyTelegramBot bot, CallbackQuery callbackQuery) {
        T entity = scrollResponse.getValue();
        InlineKeyboardMarkup scrollMenuMarkup = getNotEmptyScrollResponseReplyMarkup(scrollResponse);

        try (InputStream file = getNotEmptyScrollResponseFile(scrollResponse)) {
            String filename = getNotEmptyScrollResponseFilename(scrollResponse);
            String description = getNotEmptyScrollResponseDescription(scrollResponse);
            bot.scrollMenuItem(callbackQuery, scrollMenuMarkup, file, filename, description);
        } catch (IOException | TelegramApiException e) {
            throw new YantarniyBotTelegramException(
                    String.format(
                            "Exception while trying to scroll not empty item for entity %s and callback value = [%s]",
                            entity.getClass().getSimpleName(),
                            callbackQuery.getData()
                    ),
                    e
            );
        }
    }

    InlineKeyboardMarkup getNotEmptyScrollResponseReplyMarkup(ScrollResponse<T> scrollResponse);

    String getNotEmptyScrollResponseDescription(ScrollResponse<T> scrollResponse);

    InputStream getNotEmptyScrollResponseFile(ScrollResponse<T> scrollResponse);

    String getNotEmptyScrollResponseFilename(ScrollResponse<T> scrollResponse);
}
