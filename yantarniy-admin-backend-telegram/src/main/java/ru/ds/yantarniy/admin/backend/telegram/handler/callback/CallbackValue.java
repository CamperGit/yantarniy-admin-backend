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
    OPEN_SALES("handleSalesButton"),
    OPEN_SCHEDULE("handleSchedulesMenuButton"),
    OPEN_CLUB_CARDS("handleClubCardButton"),
    PRICE_SPA("handleSpaServiceMenuButton"),
    EMPLOYEES("handleEmployeeMenuButton"),
    RETURN_MAIN_MENU("handleReturnMainMenuButton");

    String value;
}
