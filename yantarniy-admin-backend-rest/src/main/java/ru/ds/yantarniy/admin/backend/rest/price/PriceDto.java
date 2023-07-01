package ru.ds.yantarniy.admin.backend.rest.price;

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
@ApiModel("Локация")
public class PriceDto {

    @ApiModelProperty(value = "ID прайса", required = true)
    Long id;

    @ApiModelProperty(value = "Изображение", required = true)
    FileDto file;

    @ApiModelProperty(value = "Локация, к которой относится прайс", required = true)
    LocationDto location;
}
