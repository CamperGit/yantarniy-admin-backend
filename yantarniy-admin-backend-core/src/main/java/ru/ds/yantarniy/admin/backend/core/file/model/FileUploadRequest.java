package ru.ds.yantarniy.admin.backend.core.file.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.InputStream;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileUploadRequest {

    /**
     * Файл в потоковом представлении
     */
    InputStream inputStream;

    /**
     * Оригинальное имя файла
     */
    String originalFileName;

    /**
     * Размер файла
     */
    Long size;
}
