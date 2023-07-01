package ru.ds.yantarniy.admin.backend.minio.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("Объект запроса на удаление файла из minio")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RemoveMinioObjectRequest {

    @ApiModelProperty("Имя файла в minio")
    String object;
}
