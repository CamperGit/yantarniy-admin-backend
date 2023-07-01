package ru.ds.yantarniy.admin.backend.rest.employee;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel("Модель типа сотрудника")
public class EmployeeTypeDto {

    @ApiModelProperty(value = "ID типа сотрудника", required = true)
    Long id;

    @ApiModelProperty(value = "Тип сотрудника", required = true)
    String type;

    @ApiModelProperty(value = "Описание типа")
    String title;
}
