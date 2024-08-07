package ru.ds.yantarniy.admin.backend.telegram.handler.callback.schedule;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ds.yantarniy.admin.backend.core.file.service.FileService;
import ru.ds.yantarniy.admin.backend.core.locale.LocaleMessageSource;
import ru.ds.yantarniy.admin.backend.core.schedule.service.ScheduleService;
import ru.ds.yantarniy.admin.backend.core.schedule.type.ScheduleType;
import ru.ds.yantarniy.admin.backend.core.search.SpecificationsSearchService;
import ru.ds.yantarniy.admin.backend.dao.entity.file.FileEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.schedule.ScheduleEntity;
import ru.ds.yantarniy.admin.backend.dao.specification.Specifications;
import ru.ds.yantarniy.admin.backend.telegram.bot.YantarniyTelegramBot;
import ru.ds.yantarniy.admin.backend.telegram.handler.callback.BotCallbackHandler;
import ru.ds.yantarniy.admin.backend.telegram.handler.callback.CallbackValue;
import ru.ds.yantarniy.admin.backend.telegram.handler.scroll.ScrollStateHandler;
import ru.ds.yantarniy.admin.backend.telegram.model.ScrollResponse;
import ru.ds.yantarniy.admin.backend.telegram.model.ScrollState;
import ru.ds.yantarniy.admin.backend.telegram.provider.ScrollStateHandlerProvider;
import ru.ds.yantarniy.admin.backend.telegram.service.MarkupService;
import ru.ds.yantarniy.admin.backend.telegram.service.ScrollHelperService;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleGPButtonHandler implements BotCallbackHandler, ScrollHelperService<ScheduleEntity> {

    static String FITNESS_SCHEDULES_GP_EMPTY_MESSAGE_SOURCE = "fitness.schedules.gp.empty";

    LocaleMessageSource localeMessageSource;

    ScrollStateHandlerProvider<ScheduleEntity> provider;

    MarkupService markupService;

    FileService fileService;

    ScheduleService scheduleService;

    @Override
    public void handle(YantarniyTelegramBot bot, Update update) throws TelegramApiException {
        handleScroll(bot, update);
    }

    @Override
    public InlineKeyboardMarkup getNotEmptyScrollResponseReplyMarkup(ScrollResponse<ScheduleEntity> scrollResponse) {
        ScheduleEntity value = scrollResponse.getValue();
        return markupService.getScrollMenuMarkup(
                scrollResponse.getNumberOfItems(),
                value.getId(),
                scrollResponse.getCurrentPosition(),
                CallbackValue.CURRENT_SCHEDULE.getValue(),
                CallbackValue.OPEN_SCHEDULE.getValue(),
                null,
                null
        );
    }

    @Override
    public String getNotEmptyScrollResponseDescription(ScrollResponse<ScheduleEntity> scrollResponse) {
        return scrollResponse.getValue().getDescription();
    }

    @Override
    public InputStream getNotEmptyScrollResponseFile(ScrollResponse<ScheduleEntity> scrollResponse) {
        ScheduleEntity value = scrollResponse.getValue();
        return Optional.ofNullable(value.getFile()).map(FileEntity::getId).map(fileService::getFileInputStreamById).orElse(null);
    }

    @Override
    public String getNotEmptyScrollResponseFilename(ScrollResponse<ScheduleEntity> scrollResponse) {
        ScheduleEntity value = scrollResponse.getValue();
        return Optional.ofNullable(value.getFile()).map(FileEntity::getName).orElse(null);
    }

    @Override
    public ScrollStateHandler<ScheduleEntity> getScrollStateHandler(ScrollState scrollState) {
        return provider.getScrollHandler(scrollState);
    }

    @Override
    public SpecificationsSearchService<ScheduleEntity> getSpecificationSearchService() {
        return scheduleService;
    }

    @Override
    public List<Specification<ScheduleEntity>> getAdditionalSpecifications() {
        return Collections.singletonList(
                Specifications.inOrReturnNull("type.type", Arrays.asList(ScheduleType.GROUP_SCHEDULE.getCode(), ScheduleType.SCHEDULE_DESCRIPTION.getCode()))
        );
    }

    @Override
    public String getEmptyScrollResponseAnswerMessage() {
        return localeMessageSource.getMessage(FITNESS_SCHEDULES_GP_EMPTY_MESSAGE_SOURCE);
    }

    @Override
    public InlineKeyboardMarkup getEmptyScrollResponseReplyKeyboard() {
        return markupService.getReturnMarkup(CallbackValue.OPEN_SCHEDULE.getValue(), true);
    }

    @Override
    public boolean isApplicable(String callback) {
        return callback.contains(CallbackValue.CURRENT_SCHEDULE.getValue());
    }
}
