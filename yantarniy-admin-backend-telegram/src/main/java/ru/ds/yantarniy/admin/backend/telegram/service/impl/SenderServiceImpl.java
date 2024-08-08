package ru.ds.yantarniy.admin.backend.telegram.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ds.yantarniy.admin.backend.core.customer.service.CustomerService;
import ru.ds.yantarniy.admin.backend.telegram.bot.YantarniyTelegramBot;
import ru.ds.yantarniy.admin.backend.telegram.model.SendingReport;
import ru.ds.yantarniy.admin.backend.telegram.service.SenderService;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SenderServiceImpl implements SenderService {

    CustomerService customerService;

    YantarniyTelegramBot bot;

    @Override
    public SendingReport sendMessageToCustomers(byte[] image, String description, boolean onlyAdmins) {
        List<PartialBotApiMethod<?>> result = new ArrayList<>();

        List<String> ids;
        if (onlyAdmins) {
            ids = customerService.findAllAdminChatId();
        } else {
            ids = customerService.findAllChatId();
        }
        for (String chatId : ids) {
            if (image != null) {
                SendPhoto.SendPhotoBuilder builder = SendPhoto.builder();
                builder.chatId(chatId);
                builder.photo(new InputFile(new ByteArrayInputStream(image), "filename"));
                if (description != null) {
                    builder.caption(description);
                }
                result.add(builder.build());
            } else if (description != null) {
                SendMessage message = new SendMessage(chatId,description);
                result.add(message);
            } else {
                throw new IllegalStateException("Incorrect format of the mailling. The mailling should store either a photo or text");
            }
        }
        return sendMessageByBot(result);
    }

    private SendingReport sendMessageByBot(List<PartialBotApiMethod<?>> messages) {
        List<String> correctSendChatIds = new ArrayList<>();
        List<String> errorSendChatIds = new ArrayList<>();
        log.info("Start sending messages by bot");
        for (PartialBotApiMethod<?> message : messages) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (message instanceof SendMessage) {
                try {
                    bot.execute((SendMessage) message);
                    correctSendChatIds.add(((SendMessage) message).getChatId());
                } catch (TelegramApiException e) {
                    log.error("Sending Error, id: " + ((SendMessage) message).getChatId() + " , Error Message: " + e.getMessage());
                    errorSendChatIds.add(((SendMessage) message).getChatId());
                    e.printStackTrace();
                }
            } else if (message instanceof SendPhoto) {
                try {
                    bot.execute((SendPhoto) message);
                    correctSendChatIds.add(((SendPhoto) message).getChatId());
                } catch (TelegramApiException e) {
                    log.error("Sending Error, id: " + ((SendPhoto) message).getChatId() + " , Error Message: " + e.getMessage());
                    errorSendChatIds.add(((SendPhoto) message).getChatId());
                    e.printStackTrace();
                }
            }
        }
        log.info("End sending messages by bot");

        return SendingReport.builder()
                .correctSendCustomers(customerService.findAllByChatIds(correctSendChatIds))
                .errorSendCustomers(customerService.findAllByChatIds(errorSendChatIds))
                .build();
    }
}
