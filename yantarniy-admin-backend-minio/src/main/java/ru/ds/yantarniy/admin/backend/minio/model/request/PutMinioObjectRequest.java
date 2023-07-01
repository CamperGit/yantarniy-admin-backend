package ru.ds.yantarniy.admin.backend.minio.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.InputStream;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("Объект запроса на загрузку файла в minio")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PutMinioObjectRequest {

    @ApiModelProperty("Имя файла")
    String object;

    @ApiModelProperty("Файл в потоковом представлении")
    InputStream inputStream;
}
