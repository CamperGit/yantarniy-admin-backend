package ru.ds.yantarniy.admin.backend.rest.common;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;
import ru.ds.yantarniy.admin.backend.core.file.model.FileUploadRequest;

import java.io.IOException;
import java.util.Optional;

@UtilityClass
public class MultipartFileUtils {

    public static FileUploadRequest convert(MultipartFile multipartFile) {
        return Optional.ofNullable(multipartFile).map(file -> {
            try {
                return FileUploadRequest.builder()
                        .inputStream(multipartFile.getInputStream())
                        .size(multipartFile.getSize())
                        .originalFileName(multipartFile.getOriginalFilename())
                        .build();
            } catch (IOException e) {
                throw new RuntimeException("Failed to convert multipart file to upload file request", e);
            }
        }).orElse(null);
    }
}
