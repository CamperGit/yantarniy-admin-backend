package ru.ds.yantarniy.admin.backend.telegram.handler.callback.sale;

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
import ru.ds.yantarniy.admin.backend.core.location.type.LocationType;
import ru.ds.yantarniy.admin.backend.core.price.service.PriceService;
import ru.ds.yantarniy.admin.backend.core.sale.service.SaleService;
import ru.ds.yantarniy.admin.backend.core.search.SpecificationsSearchService;
import ru.ds.yantarniy.admin.backend.dao.entity.file.FileEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.price.PriceEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.sale.SaleEntity;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpaSalesButtonHandler implements BotCallbackHandler, ScrollHelperService<SaleEntity> {

    static String SALES_SPA_LOCATION_EMPTY_MESSAGE_SOURCE = "sales.spa.empty";
    static String SALES_SPA_CONTACT_US_MESSAGE_SOURCE = "sales.spa.contact-us";

    LocaleMessageSource localeMessageSource;

    ScrollStateHandlerProvider<SaleEntity> provider;

    MarkupService markupService;

    FileService fileService;

    SaleService saleService;

    @Override
    public void handle(YantarniyTelegramBot bot, Update update) throws TelegramApiException {
        handleScroll(bot, update);
    }

    @Override
    public InlineKeyboardMarkup getNotEmptyScrollResponseReplyMarkup(ScrollResponse<SaleEntity> scrollResponse) {
        SaleEntity value = scrollResponse.getValue();
        return markupService.getScrollMenuMarkup(
                scrollResponse.getNumberOfItems(),
                value.getId(),
                scrollResponse.getCurrentPosition(),
                CallbackValue.OPEN_SPA_SALES.getValue(),
                CallbackValue.OPEN_SALES.getValue(),
                localeMessageSource.getMessage(SALES_SPA_CONTACT_US_MESSAGE_SOURCE),
                CallbackValue.OPEN_SPA_SALES_CONTACT_US.getValue()
        );
    }

    @Override
    public String getNotEmptyScrollResponseDescription(ScrollResponse<SaleEntity> scrollResponse) {
        return scrollResponse.getValue().getDescription();
    }

    @Override
    public InputStream getNotEmptyScrollResponseFile(ScrollResponse<SaleEntity> scrollResponse) {
        SaleEntity value = scrollResponse.getValue();
        return Optional.ofNullable(value.getFile()).map(FileEntity::getId).map(fileService::getFileInputStreamById).orElse(null);
    }

    @Override
    public String getNotEmptyScrollResponseFilename(ScrollResponse<SaleEntity> scrollResponse) {
        SaleEntity value = scrollResponse.getValue();
        return Optional.ofNullable(value.getFile()).map(FileEntity::getName).orElse(null);
    }

    @Override
    public ScrollStateHandler<SaleEntity> getScrollStateHandler(ScrollState scrollState) {
        return provider.getScrollHandler(scrollState);
    }

    @Override
    public SpecificationsSearchService<SaleEntity> getSpecificationSearchService() {
        return saleService;
    }

    @Override
    public List<Specification<SaleEntity>> getAdditionalSpecifications() {
        return Collections.singletonList(
                Specifications.inOrReturnNull("location.type", Collections.singletonList(LocationType.SPA.getCode()))
        );
    }

    @Override
    public String getEmptyScrollResponseAnswerMessage() {
        return localeMessageSource.getMessage(SALES_SPA_LOCATION_EMPTY_MESSAGE_SOURCE);
    }

    @Override
    public InlineKeyboardMarkup getEmptyScrollResponseReplyKeyboard() {
        return markupService.getReturnMarkup(CallbackValue.OPEN_SALES.getValue(), true);
    }

    @Override
    public boolean isApplicable(String callback) {
        return callback.contains(CallbackValue.OPEN_SPA_SALES.getValue());
    }
}
