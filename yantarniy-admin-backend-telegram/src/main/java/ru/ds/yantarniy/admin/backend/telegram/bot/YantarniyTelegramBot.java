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
import ru.ds.yantarniy.admin.backend.telegram.handler.callback.BotCallbackHandler;
import ru.ds.yantarniy.admin.backend.telegram.handler.command.BotCommandHandler;
import ru.ds.yantarniy.admin.backend.telegram.handler.provider.BotCallbackHandlerProvider;
import ru.ds.yantarniy.admin.backend.telegram.handler.provider.BotCommandHandlerProvider;
import ru.ds.yantarniy.admin.backend.telegram.property.TelegramBotProperties;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class YantarniyTelegramBot extends TelegramLongPollingBot {

    TelegramBotProperties botProperties;

    BotCallbackHandlerProvider botCallbackHandlerProvider;

    BotCommandHandlerProvider botCommandHandlerProvider;

    public YantarniyTelegramBot(TelegramBotProperties botProperties,
                                BotCallbackHandlerProvider botCallbackHandlerProvider,
                                BotCommandHandlerProvider botCommandHandlerProvider) {
        super(new DefaultBotOptions());
        this.botProperties = botProperties;
        this.botCallbackHandlerProvider = botCallbackHandlerProvider;
        this.botCommandHandlerProvider = botCommandHandlerProvider;
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
            //e.printStackTrace();
            log.error("Commands and none commands handling exception");
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

        InlineKeyboardButton salesButton = new InlineKeyboardButton("Акции");
        InlineKeyboardButton scheduleButton = new InlineKeyboardButton("Расписание");
        InlineKeyboardButton clubCartsButton = new InlineKeyboardButton("Клубные карты");
        InlineKeyboardButton spaServicesButton = new InlineKeyboardButton("Прайс-СПА");
        InlineKeyboardButton employeesButton = new InlineKeyboardButton("Сотрудники");
        InlineKeyboardButton sberQrButton = new InlineKeyboardButton("Плати QR от Сбера");
        InlineKeyboardButton contactAdminButton = new InlineKeyboardButton("Запись в СПА");
        InlineKeyboardButton contactManagerButton = new InlineKeyboardButton("Связь с менеджером");


        salesButton.setCallbackData("handleSalesButton");
        scheduleButton.setCallbackData("handleSchedulesMenuButton");
        clubCartsButton.setCallbackData("handleClubCardButton");
        spaServicesButton.setCallbackData("handleSpaServiceMenuButton");
        employeesButton.setCallbackData("handleEmployeeMenuButton");
        sberQrButton.setCallbackData("handleQrSberButton");
        contactAdminButton.setCallbackData("handleContactAdminButton");
        contactManagerButton.setCallbackData("handleContactManagerButton");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(employeesButton);
        firstRow.add(sberQrButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(clubCartsButton);
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
        SendMessage sendMessage = new SendMessage(chatId, "При необходимости открыть главное меню воспользуйтесь " +
                "командой /start или соответствующей кнопкой в нижней части экрана");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow firstRow = new KeyboardRow();
        KeyboardButton button = new KeyboardButton("Главное меню");
        firstRow.add(button);

        keyboard.add(firstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        this.execute(sendMessage);
    }
}