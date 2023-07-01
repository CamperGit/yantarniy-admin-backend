package ru.ds.yantarniy.admin.backend.core.location.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum LocationType {

    CLUB_CARDS("CLUB_CARDS"),
    GYM("GYM"),
    GROUP_ACTIVITY("GROUP_ACTIVITY"),
    POOL("POOL"),
    SPA("SPA"),
    BATHHOUSE("BATHHOUSE"),
    NAILS("NAILS"),
    COSMETOLOGY("COSMETOLOGY"),
    STYLISTS("STYLISTS"),
    MASSAGE("MASSAGE");

    String code;
}
