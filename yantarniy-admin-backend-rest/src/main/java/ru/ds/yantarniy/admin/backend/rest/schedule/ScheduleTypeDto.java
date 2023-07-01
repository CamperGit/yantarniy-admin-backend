package ru.ds.yantarniy.admin.backend.rest.schedule;

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
@ApiModel("Модель типа расписания")
public class ScheduleTypeDto {

    @ApiModelProperty(value = "ID типа расписания", required = true)
    Long id;

    @ApiModelProperty(value = "Тип расписания", required = true)
    String type;

    @ApiModelProperty(value = "Описание типа")
    String title;
}
