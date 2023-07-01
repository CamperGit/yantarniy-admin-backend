package ru.ds.yantarniy.admin.backend.minio.service;

import ru.ds.yantarniy.admin.backend.minio.model.request.GetMinioObjectRequest;
import ru.ds.yantarniy.admin.backend.minio.model.request.PutMinioObjectRequest;
import ru.ds.yantarniy.admin.backend.minio.model.response.GetMinioObjectResponse;
import ru.ds.yantarniy.admin.backend.minio.model.response.PutMinioObjectResponse;

public interface MinioService {

    PutMinioObjectResponse putObject(PutMinioObjectRequest request);

    GetMinioObjectResponse getObject(GetMinioObjectRequest request);
}
