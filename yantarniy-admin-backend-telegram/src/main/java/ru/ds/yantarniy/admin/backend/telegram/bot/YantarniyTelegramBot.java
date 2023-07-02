package ru.ds.yantarniy.admin.backend.telegram.bot;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ds.yantarniy.admin.backend.core.locale.LocaleMessageSource;
import ru.ds.yantarniy.admin.backend.telegram.exception.YantarniyBotTelegramException;
import ru.ds.yantarniy.admin.backend.telegram.handler.callback.BotCallbackHandler;
import ru.ds.yantarniy.admin.backend.telegram.handler.callback.CallbackValue;
import ru.ds.yantarniy.admin.backend.telegram.handler.command.BotCommandHandler;
import ru.ds.yantarniy.admin.backend.telegram.handler.provider.BotCallbackHandlerProvider;
import ru.ds.yantarniy.admin.backend.telegram.handler.provider.BotCommandHandlerProvider;
import ru.ds.yantarniy.admin.backend.telegram.property.TelegramBotProperties;

import java.io.InputStream;
import java.util.*;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class YantarniyTelegramBot extends TelegramLongPollingBot {

    private static final String SALES_LABEL_MESSAGE_SOURCE = "main.sales.label";
    private static final String SCHEDULE_LABEL_MESSAGE_SOURCE = "main.schedule.label";
    private static final String CLUB_CARDS_LABEL_MESSAGE_SOURCE = "main.club-cards.label";
    private static final String SPA_PRICE_LABEL_MESSAGE_SOURCE = "main.spa-price.label";
    private static final String EMPLOYEES_LABEL_MESSAGE_SOURCE = "main.employees.label";
    private static final String SBERQR_LABEL_MESSAGE_SOURCE = "main.sberqr.label";
    private static final String CALL_MANAGER_LABEL_MESSAGE_SOURCE = "main.call-manager.label";
    private static final String CALL_ADMIN_LABEL_MESSAGE_SOURCE = "main.call-admin.label";
    static String MAIN_MENU_LOCALE_MESSAGE = "main.label";

    static String START_MESSAGE_MESSAGE_SOURCE = "main.start-command";
    TelegramBotProperties botProperties;

    BotCallbackHandlerProvider botCallbackHandlerProvider;

    BotCommandHandlerProvider botCommandHandlerProvider;

    LocaleMessageSource localeMessageSource;

    public YantarniyTelegramBot(TelegramBotProperties botProperties,
                                BotCallbackHandlerProvider botCallbackHandlerProvider,
                                BotCommandHandlerProvider botCommandHandlerProvider, LocaleMessageSource localeMessageSource) {
        super(new DefaultBotOptions());
        this.botProperties = botProperties;
        this.botCallbackHandlerProvider = botCallbackHandlerProvider;
        this.botCommandHandlerProvider = botCommandHandlerProvider;
        this.localeMessageSource = localeMessageSource;
    }

    @Override
    public String getBotUsername() {
        return botProperties.getUsername();
    }

    @Override
    public String getBotToken() {
        return botProperties.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            //CallbackQuery handling
            if (update.hasCallbackQuery() && !update.getCallbackQuery().getData().equals("null")) {
                String callback = update.getCallbackQuery().getData();
                BotCallbackHandler botCallbackHandler = botCallbackHandlerProvider.getBotCallbackHandler(callback);
                if (botCallbackHandler != null) {
                    botCallbackHandler.handle(this, update);
                }
            }

            //Commands and nonCommands messages handling
            if (update.getMessage() != null && update.getMessage().hasText()) {
                String text = update.getMessage().getText();
                BotCommandHandler botCommandHandler = botCommandHandlerProvider.getBotCommandHandler(text);
                botCommandHandler.handle(this, update);
            }
        } catch (TelegramApiException e) {
            log.error("Commands and none commands handling exception", e);
            throw new YantarniyBotTelegramException("Error while trying to handle telegram command or callback query", e);
        }
    }

    public void changeMessage(Message message, String text, InlineKeyboardMarkup markup) throws TelegramApiException {
        String chatId = message.getChatId().toString();
        if (message.hasPhoto()) {
            SendMessage newMessage = new SendMessage(chatId, text);
            newMessage.enableHtml(true);
            newMessage.setReplyMarkup(markup);

            this.deleteMessage(chatId, message.getMessageId());
            this.execute(newMessage);
        } else {
            EditMessageText newText = new EditMessageText();
            newText.enableHtml(true);
            newText.setText(text);
            newText.setChatId(chatId);
            newText.setMessageId(message.getMessageId());
            newText.setReplyMarkup(markup);

            this.execute(newText);
        }
    }

    public void deleteMessage(String chatId, Integer messageId) throws TelegramApiException {
        this.execute(new DeleteMessage(chatId, messageId));
    }

    public void changeMessagePhoto(String chatId, Integer messageId, InlineKeyboardMarkup markup,
                                   InputStream image, String description, String filename) throws TelegramApiException {
        EditMessageMedia editMessageMedia = new EditMessageMedia();
        editMessageMedia.setChatId(chatId);
        editMessageMedia.setMessageId(messageId);
        editMessageMedia.setReplyMarkup(markup);

        InputMedia in = new InputMediaPhoto();

        if (description != null) {
            in.setCaption(description);
        }

        in.setMedia(image, filename);
        editMessageMedia.setMedia(in);

        this.execute(editMessageMedia);
    }

    public void scrollMenuItem(String chatId, Message message, CallbackQuery query, InlineKeyboardMarkup markup, InputStream image, String filename, String description) throws TelegramApiException {
        int messageId = message.getMessageId();
        if (image != null) {
            if (query.getMessage().hasPhoto()) {
                this.changeMessagePhoto(chatId, messageId, markup, image, description, filename);
            } else {
                this.deleteMessage(chatId, messageId);
                this.execute(SendPhoto.builder()
                        .chatId(chatId)
                        .photo(new InputFile(image, filename))
                        .replyMarkup(markup)
                        .caption(description)
                        .build());
            }
        } else {
            if (query.getMessage().hasPhoto()) {
                this.deleteMessage(chatId, messageId);
                this.execute(SendMessage.builder()
                        .replyMarkup(markup)
                        .chatId(chatId)
                        .text(description)
                        .build());
            } else {
                changeMessage(message, description, markup);
            }
        }
    }

    public void sendMainMenuMessage(String chatId, String text) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage(chatId, text);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton salesButton = new InlineKeyboardButton(localeMessageSource.getMessage(SALES_LABEL_MESSAGE_SOURCE));
        InlineKeyboardButton scheduleButton = new InlineKeyboardButton(localeMessageSource.getMessage(SCHEDULE_LABEL_MESSAGE_SOURCE));
        InlineKeyboardButton clubCardsButton = new InlineKeyboardButton(localeMessageSource.getMessage(CLUB_CARDS_LABEL_MESSAGE_SOURCE));
        InlineKeyboardButton spaServicesButton = new InlineKeyboardButton(localeMessageSource.getMessage(SPA_PRICE_LABEL_MESSAGE_SOURCE));
        InlineKeyboardButton employeesButton = new InlineKeyboardButton(localeMessageSource.getMessage(EMPLOYEES_LABEL_MESSAGE_SOURCE));
        InlineKeyboardButton sberQrButton = new InlineKeyboardButton(localeMessageSource.getMessage(SBERQR_LABEL_MESSAGE_SOURCE));
        InlineKeyboardButton contactAdminButton = new InlineKeyboardButton(localeMessageSource.getMessage(CALL_ADMIN_LABEL_MESSAGE_SOURCE));
        InlineKeyboardButton contactManagerButton = new InlineKeyboardButton(localeMessageSource.getMessage(CALL_MANAGER_LABEL_MESSAGE_SOURCE));


        salesButton.setCallbackData(CallbackValue.OPEN_SALES.getValue());
        scheduleButton.setCallbackData(CallbackValue.OPEN_SCHEDULE.getValue());
        clubCardsButton.setCallbackData(CallbackValue.OPEN_CLUB_CARDS.getValue());
        spaServicesButton.setCallbackData(CallbackValue.OPEN_CLUB_CARDS.getValue());
        employeesButton.setCallbackData(CallbackValue.EMPLOYEES.getValue());
        sberQrButton.setCallbackData(CallbackValue.SBER_QR.getValue());
        contactAdminButton.setCallbackData(CallbackValue.CALL_ADMIN.getValue());
        contactManagerButton.setCallbackData(CallbackValue.CALL_MANAGER.getValue());

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(employeesButton);
        firstRow.add(sberQrButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(clubCardsButton);
        secondRow.add(scheduleButton);

        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        thirdRow.add(salesButton);
        thirdRow.add(spaServicesButton);

        List<InlineKeyboardButton> fourthRow = new ArrayList<>();
        fourthRow.add(contactManagerButton);
        fourthRow.add(contactAdminButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Arrays.asList(firstRow, secondRow, thirdRow, fourthRow));

        inlineKeyboardMarkup.setKeyboard(rowList);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        this.execute(sendMessage);
    }

    public void sendReplyMarkup(String chatId) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage(chatId, localeMessageSource.getMessage(START_MESSAGE_MESSAGE_SOURCE));
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow firstRow = new KeyboardRow();
        KeyboardButton button = new KeyboardButton(localeMessageSource.getMessage(MAIN_MENU_LOCALE_MESSAGE));
        firstRow.add(button);

        keyboard.add(firstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        this.execute(sendMessage);
    }

    public void moveToMainMenu(String chatId, Integer messageId) throws TelegramApiException {
        deleteMessage(chatId, messageId);
        sendMainMenuMessage(chatId, localeMessageSource.getMessage(MAIN_MENU_LOCALE_MESSAGE));
    }
}
