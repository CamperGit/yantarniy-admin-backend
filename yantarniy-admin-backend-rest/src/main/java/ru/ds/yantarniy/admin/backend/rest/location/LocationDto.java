package ru.ds.yantarniy.admin.backend.rest.location;

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
@ApiModel("Локация")
public class LocationDto {

    @ApiModelProperty(value = "ID локации", required = true)
    Long id;

    @ApiModelProperty(value = "Тип локации", required = true)
    String type;

    @ApiModelProperty(value = "Описание типа")
    String title;
}
