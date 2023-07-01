package ru.ds.yantarniy.admin.backend.minio.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.InputStream;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("Объект ответа на получение файла в minio")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetMinioObjectResponse {

    @ApiModelProperty("Файл в потоковом представлении")
    InputStream inputStream;
}
