package ru.ds.yantarniy.admin.backend.rest.sale;

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
@ApiModel("Модель скидки")
public class SaleDto {

    @ApiModelProperty(value = "ID скидки", required = true)
    Long id;

    @ApiModelProperty(value = "Описание скидки")
    String description;

    @ApiModelProperty(value = "Изображение")
    FileDto file;

    @ApiModelProperty(value = "Локация, к которой относится скидка", required = true)
    LocationDto location;
}
