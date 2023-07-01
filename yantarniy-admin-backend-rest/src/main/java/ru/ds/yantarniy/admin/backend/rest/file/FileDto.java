package ru.ds.yantarniy.admin.backend.rest.file;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel("Модель файла")
public class FileDto {

    @ApiModelProperty(value = "ID файла", required = true)
    Long id;

    @ApiModelProperty(value = "Ссылка в хранилище", required = true)
    String storageUrl;

    @ApiModelProperty(value = "Etag файла в minio (хеш файла)", required = true)
    String eTag;

    @ApiModelProperty(value = "Имя файла", required = true)
    String name;

    @ApiModelProperty(value = "Размер файла", required = true)
    Long size;

    @ApiModelProperty(value = "Дата создания файла", required = true)
    LocalDateTime createdDate;
}
