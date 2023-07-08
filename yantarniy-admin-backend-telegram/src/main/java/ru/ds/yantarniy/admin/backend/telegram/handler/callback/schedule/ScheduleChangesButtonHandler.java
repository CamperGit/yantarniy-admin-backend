package ru.ds.yantarniy.admin.backend.telegram.handler.callback.schedule;

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
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ds.yantarniy.admin.backend.core.file.service.FileService;
import ru.ds.yantarniy.admin.backend.core.locale.LocaleMessageSource;
import ru.ds.yantarniy.admin.backend.core.schedule.service.ScheduleService;
import ru.ds.yantarniy.admin.backend.core.schedule.type.ScheduleType;
import ru.ds.yantarniy.admin.backend.dao.entity.file.FileEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.schedule.ScheduleEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.schedule.ScheduleRepository;
import ru.ds.yantarniy.admin.backend.dao.specification.Specifications;
import ru.ds.yantarniy.admin.backend.telegram.bot.YantarniyTelegramBot;
import ru.ds.yantarniy.admin.backend.telegram.exception.YantarniyBotTelegramException;
import ru.ds.yantarniy.admin.backend.telegram.handler.callback.BotCallbackHandler;
import ru.ds.yantarniy.admin.backend.telegram.handler.callback.CallbackValue;
import ru.ds.yantarniy.admin.backend.telegram.handler.scroll.ScrollStateHandler;
import ru.ds.yantarniy.admin.backend.telegram.model.ScrollResponse;
import ru.ds.yantarniy.admin.backend.telegram.model.ScrollState;
import ru.ds.yantarniy.admin.backend.telegram.provider.ScrollStateHandlerProvider;
import ru.ds.yantarniy.admin.backend.telegram.service.MarkupService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleChangesButtonHandler implements BotCallbackHandler {

    static String FITNESS_SCHEDULES_CHANGES_EMPTY_MESSAGE_SOURCE = "fitness.schedules.changes.empty";

    LocaleMessageSource localeMessageSource;

    ScrollStateHandlerProvider<ScheduleEntity> provider;

    MarkupService markupService;

    FileService fileService;

    ScheduleService scheduleService;

    @Override
    public void handle(YantarniyTelegramBot bot, Update update) throws TelegramApiException {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String data = callbackQuery.getData();

        ScrollState scrollState = ScrollState.getScrollStateFromStringWithRegex(data);
        ScrollStateHandler<ScheduleEntity> scrollHandler = provider.getScrollHandler(scrollState);

        List<Specification<ScheduleEntity>> additionalSpecifications = Collections.singletonList(
                Specifications.equalOrReturnNull("type.type", ScheduleType.SCHEDULE_CHANGE.getCode())
        );
        ScrollResponse<ScheduleEntity> scrollResponse = scrollHandler.getEntityFromData(data, additionalSpecifications, scheduleService);
        if (scrollHandler.isCanScroll(scrollResponse, scrollState)) {
            Optional.ofNullable(scrollResponse.getValue()).ifPresentOrElse(
                    (entity) -> handleNotEmptyScrollResponse(scrollResponse, bot, callbackQuery),
                    () -> handleEmptyScrollResponse(callbackQuery, bot)
            );
        }
    }

    private void handleNotEmptyScrollResponse(ScrollResponse<ScheduleEntity> scrollResponse, YantarniyTelegramBot bot, CallbackQuery callbackQuery) {
        ScheduleEntity entity = scrollResponse.getValue();
        InlineKeyboardMarkup scrollMenuMarkup = markupService.getScrollMenuMarkup(
                scrollResponse.getNumberOfItems(),
                entity.getId(),
                scrollResponse.getCurrentPosition(),
                CallbackValue.SCHEDULE_CHANGES.getValue(),
                CallbackValue.OPEN_SCHEDULE.getValue(),
                null,
                null
        );

        try (InputStream file = Optional.ofNullable(entity.getFile()).map(FileEntity::getId).map(fileService::getFileInputStreamById).orElse(null)) {
            String filename = Optional.ofNullable(entity.getFile()).map(FileEntity::getName).orElse(null);
            bot.scrollMenuItem(callbackQuery, scrollMenuMarkup, file, filename, entity.getDescription());
        } catch (IOException | TelegramApiException e) {
            throw new YantarniyBotTelegramException(String.format("Exception while trying to scroll item for schedule change with id = %d", entity.getId()), e);
        }
    }

    private void handleEmptyScrollResponse(CallbackQuery callbackQuery, YantarniyTelegramBot bot) {
        Message message = callbackQuery.getMessage();
        String chatId = message.getChatId().toString();
        SendMessage sendMessage = new SendMessage(chatId, localeMessageSource.getMessage(FITNESS_SCHEDULES_CHANGES_EMPTY_MESSAGE_SOURCE));
        sendMessage.setReplyMarkup(markupService.getReturnMarkup(CallbackValue.OPEN_SCHEDULE.getValue(), true));
        try {
            bot.execute(sendMessage);
            bot.deleteMessage(message);
        } catch (TelegramApiException e) {
            throw new YantarniyBotTelegramException("Exception while trying to send schedule changes not found markup message", e);
        }
    }

    @Override
    public boolean isApplicable(String callback) {
        return callback.contains(CallbackValue.SCHEDULE_CHANGES.getValue());
    }
}
