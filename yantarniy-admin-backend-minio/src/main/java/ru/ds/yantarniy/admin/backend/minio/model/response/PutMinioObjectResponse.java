package ru.ds.yantarniy.admin.backend.minio.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("Объект ответа на загрузку файла в minio")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PutMinioObjectResponse {

    @ApiModelProperty("Корзина объекта в minio")
    String bucket;

    @ApiModelProperty("Имя файла в minio")
    String object;

    @ApiModelProperty("Etag (хеш файла) в minio")
    String etag;
}
