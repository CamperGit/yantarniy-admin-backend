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
    CURRENT_SCHEDULE("handleFitnessCurrentScheduleButton"),
    SCHEDULE_CHANGES("handleFitnessChangesButton"),
    OPEN_SPECIALISTS("handleSpecialistsButton"),
    OPEN_COACHES("handleCoachesButton"),
    OPEN_CLUB_CARDS("handleClubCardButton"),
    OPEN_GYM_COACHES("handleFitnessGymButton"),
    OPEN_FITNESS_GROUP_COACHES("handleFitnessGroupsButton"),
    OPEN_POOL_COACHES("handleFitnessPoolButton"),
    OPEN_EMPLOYEES_CONTACT_US("handleFitnessContactUsButton"),
    OPEN_NAILS_SPA_SPECIALISTS("handleSpaNailsButton"),
    OPEN_MASSAGE_SPA_SPECIALISTS("handleSpaMassageButton"),
    OPEN_COSMETOLOGY_SPA_SPECIALISTS("handleSpaCosmetologyButton"),
    OPEN_STYLISTS_SPA_SPECIALISTS("handleSpaStylistsButton"),
    OPEN_SPA_CONTACT_US("handleSpaContactUsButton"),
    PRICE_SPA("handleSpaServiceMenuButton"),
    OPEN_SPA_PRICE_NAILS("handleSSNailsButton"),
    OPEN_SPA_PRICE_COSMETOLOGY("handleSSCosmetologyButton"),
    OPEN_SPA_PRICE_STYLISTS("handleSSStylistsButton"),
    OPEN_SPA_PRICE_MASSAGE("handleSSMassageButton"),
    OPEN_SPA_PRICE_BATHHOUSE("handleSSBathhouseButton"),
    OPEN_PRICE_SPA_CONTACT_US("handlePriceSpaContactUs"),
    OPEN_INDIVIDUAL_GOLD_CARD_INFO("handleIndGoldClubCardButton"),
    OPEN_DAY_GOLD_CARD_INFO("handleDayGoldClubCardButton"),
    OPEN_WEEKEND_CARD_INFO("handleWeekendClubCardButton"),
    OPEN_POOL_CARD_INFO("handlePoolClubCardButton"),
    OPEN_CLUB_CARDS_SALES("handleClubCardsSalesButton"),
    OPEN_CLUB_CARDS_CONTACT_US("handleCCSalesContactUsButton"),
    OPEN_SPA_SALES("handleSpaSalesButton"),
    OPEN_SPA_SALES_CONTACT_US("handleSpaSalesContactUsButton"),
    EMPLOYEES("handleEmployeeMenuButton"),
    RETURN_MAIN_MENU("handleReturnMainMenuButton");

    String value;
}
