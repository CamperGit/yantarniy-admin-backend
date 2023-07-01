package ru.ds.yantarniy.admin.backend.core.file.service;

import ru.ds.yantarniy.admin.backend.core.file.model.FileUploadRequest;
import ru.ds.yantarniy.admin.backend.dao.entity.file.FileEntity;

import java.io.InputStream;

public interface FileService {

    FileEntity upload(FileUploadRequest uploadRequest);

    FileEntity save(FileEntity entity);

    InputStream getFileInputStreamById(Long id);

    FileEntity findById(Long id);

    void deleteById(Long id);
}
