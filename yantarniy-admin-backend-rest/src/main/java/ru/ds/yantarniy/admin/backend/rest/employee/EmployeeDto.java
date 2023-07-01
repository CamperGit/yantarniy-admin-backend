package ru.ds.yantarniy.admin.backend.rest.employee;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.ds.yantarniy.admin.backend.rest.file.FileDto;
import ru.ds.yantarniy.admin.backend.rest.location.LocationDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel("Модель сотрудника")
public class EmployeeDto {

    @ApiModelProperty(value = "ID сотрудника", required = true)
    Long id;

    @ApiModelProperty("Описание сотрудника")
    String description;

    @ApiModelProperty(value = "Изображение сотрудника")
    FileDto file;

    @ApiModelProperty(value = "Тип сотрудника", required = true)
    EmployeeTypeDto type;

    @ApiModelProperty(value = "Место работы сотрудника", required = true)
    LocationDto location;
}
