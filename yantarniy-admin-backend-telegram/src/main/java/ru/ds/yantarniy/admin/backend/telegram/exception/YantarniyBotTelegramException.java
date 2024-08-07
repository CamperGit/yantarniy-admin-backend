package ru.ds.yantarniy.admin.backend.telegram.exception;

public class YantarniyBotTelegramException extends RuntimeException {

    public YantarniyBotTelegramException() {
    }

    public YantarniyBotTelegramException(String message) {
        super(message);
    }

    public YantarniyBotTelegramException(String message, Throwable cause) {
        super(message, cause);
    }
}
