package ru.ds.yantarniy.admin.backend.core.employee.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EmployeeType {

    COACH("COACH"),
    MASTER_COACH("MASTER_COACH"),
    MASTER_COACH_PLUS("MASTER_COACH_PLUS"),
    NAILS_MASTER("NAILS_MASTER"),
    MASSAGE_MASTER("MASSAGE_MASTER"),
    COSMETOLOGY_MASTER("COSMETOLOGY_MASTER"),
    STYLIST("STYLIST");

    String code;
}
