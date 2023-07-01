package ru.ds.yantarniy.admin.backend.core.schedule.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ScheduleType {

    GROUP_SCHEDULE("GROUP_SCHEDULE"),
    SCHEDULE_CHANGE("SCHEDULE_CHANGE"),
    SCHEDULE_DESCRIPTION("SCHEDULE_DESCRIPTION");

    String code;
}
