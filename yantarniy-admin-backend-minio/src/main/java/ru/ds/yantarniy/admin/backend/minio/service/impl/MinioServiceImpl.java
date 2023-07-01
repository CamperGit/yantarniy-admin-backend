package ru.ds.yantarniy.admin.backend.minio.service.impl;

import io.minio.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ds.yantarniy.admin.backend.minio.exception.MinioServiceException;
import ru.ds.yantarniy.admin.backend.minio.model.request.GetMinioObjectRequest;
import ru.ds.yantarniy.admin.backend.minio.model.request.PutMinioObjectRequest;
import ru.ds.yantarniy.admin.backend.minio.model.request.RemoveMinioObjectRequest;
import ru.ds.yantarniy.admin.backend.minio.model.response.GetMinioObjectResponse;
import ru.ds.yantarniy.admin.backend.minio.model.response.PutMinioObjectResponse;
import ru.ds.yantarniy.admin.backend.minio.property.MinioProperties;
import ru.ds.yantarniy.admin.backend.minio.service.MinioService;

import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MinioServiceImpl implements MinioService {

    static String STORAGE_URL_FORMAT = "%s/%s/%s";

    MinioClient minioClient;

    MinioProperties minioProperties;

    @Override
    public PutMinioObjectResponse putObject(PutMinioObjectRequest request) {
        log.info("[MINIO_SERVICE] trying to put object into minio storage with name {}", request.getObject());
        try (InputStream inputStream = request.getInputStream()) {
            ObjectWriteResponse objectWriteResponse = minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .object(request.getObject())
                            .stream(inputStream, -1, 10485760)
                            .build()
            );
            PutMinioObjectResponse response = PutMinioObjectResponse.builder()
                    .bucket(objectWriteResponse.bucket())
                    .etag(objectWriteResponse.etag())
                    .object(objectWriteResponse.object())
                    .build();
            log.info("[MINIO_SERVICE] successfully put object into minio storage with name {}", request.getObject());
            return response;
        } catch (Exception e) {
            log.error("[MINIO_SERVICE] error while trying to put object into minio storage with name {}", request.getObject());
            throw new MinioServiceException("Error while trying to put object into minio object storage", e);
        }
    }

    @Override
    public GetMinioObjectResponse getObject(GetMinioObjectRequest request) {
        log.info("[MINIO_SERVICE] trying to get object from minio storage with name {}", request.getObject());
        try {
            GetObjectResponse getObjectResponse = minioClient
                    .getObject(GetObjectArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .object(request.getObject())
                            .matchETag(request.getEtag())
                            .build());
            GetMinioObjectResponse response = GetMinioObjectResponse.builder()
                    .inputStream(getObjectResponse)
                    .build();
            log.info("[MINIO_SERVICE] successfully get object from minio storage with name {}", request.getObject());
            return response;
        } catch (Exception e) {
            log.error("[MINIO_SERVICE] error while trying to get object from minio storage with name {}", request.getObject());
            throw new MinioServiceException("Error while trying to get object from minio object storage", e);
        }
    }

    @Override
    public void removeObject(RemoveMinioObjectRequest request) {
        log.info("[MINIO_SERVICE] trying to remove object from minio storage with name {}", request.getObject());
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .object(request.getObject())
                    .build());
            log.info("[MINIO_SERVICE] successfully remove object from minio storage with name {}", request.getObject());
        } catch (Exception e) {
            log.error("[MINIO_SERVICE] error while trying to remove object from minio storage with name {}", request.getObject());
            throw new MinioServiceException("Error while trying to remove object from minio object storage", e);
        }
    }

    @Override
    public String getStorageUrl(String objectName) {
        return String.format(STORAGE_URL_FORMAT,
                minioProperties.getUrl(),
                minioProperties.getBucketName(),
                objectName
        );
    }
}
