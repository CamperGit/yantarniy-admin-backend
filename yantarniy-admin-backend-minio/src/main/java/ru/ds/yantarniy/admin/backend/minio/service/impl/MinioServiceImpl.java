package ru.ds.yantarniy.admin.backend.minio.service.impl;

import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ds.yantarniy.admin.backend.minio.exception.MinioServiceException;
import ru.ds.yantarniy.admin.backend.minio.model.request.GetMinioObjectRequest;
import ru.ds.yantarniy.admin.backend.minio.model.request.PutMinioObjectRequest;
import ru.ds.yantarniy.admin.backend.minio.model.response.GetMinioObjectResponse;
import ru.ds.yantarniy.admin.backend.minio.model.response.PutMinioObjectResponse;
import ru.ds.yantarniy.admin.backend.minio.property.MinioProperties;
import ru.ds.yantarniy.admin.backend.minio.service.MinioService;

import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;

    private final MinioProperties minioProperties;

    @Override
    public PutMinioObjectResponse putObject(PutMinioObjectRequest request) {
        try (InputStream inputStream = request.getInputStream()) {
            ObjectWriteResponse objectWriteResponse = minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .object(request.getObject())
                            .stream(inputStream, -1, 10485760)
                            .build()
            );
            return PutMinioObjectResponse.builder()
                    .bucket(objectWriteResponse.bucket())
                    .etag(objectWriteResponse.etag())
                    .object(objectWriteResponse.object())
                    .build();
        } catch (Exception e) {
            throw new MinioServiceException("Error while trying to put object into minio object storage", e);
        }
    }

    @Override
    public GetMinioObjectResponse getObject(GetMinioObjectRequest request) {
        try {
            GetObjectResponse getObjectResponse = minioClient
                    .getObject(GetObjectArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .object(request.getObject())
                            .matchETag(request.getEtag())
                            .build());
            return GetMinioObjectResponse.builder()
                    .inputStream(getObjectResponse)
                    .build();
        } catch (Exception e) {
            throw new MinioServiceException("Error while trying to get object from minio object storage", e);
        }
    }
}
