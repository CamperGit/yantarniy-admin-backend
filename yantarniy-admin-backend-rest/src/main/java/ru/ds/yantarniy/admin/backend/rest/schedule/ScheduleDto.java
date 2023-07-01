package ru.ds.yantarniy.admin.backend.rest.schedule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.ds.yantarniy.admin.backend.rest.file.FileDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel("Модель расписания")
public class ScheduleDto {

    @ApiModelProperty(value = "ID расписания", required = true)
    Long id;

    @ApiModelProperty("Описание расписания")
    String description;

    @ApiModelProperty(value = "Изображение расписания")
    FileDto file;

    @ApiModelProperty(value = "Тип расписания", required = true)
    ScheduleTypeDto type;
}
