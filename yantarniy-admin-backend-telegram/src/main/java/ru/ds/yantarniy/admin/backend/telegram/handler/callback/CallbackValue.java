package ru.ds.yantarniy.admin.backend.telegram.handler.callback;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum CallbackValue {
    SBER_QR("handleQrSberButton"),
    CALL_ADMIN("handleContactAdminButton"),
    CALL_MANAGER("handleContactManagerButton"),
    RETURN_MAIN_MENU("handleReturnMainMenuButton");

    String value;
}
