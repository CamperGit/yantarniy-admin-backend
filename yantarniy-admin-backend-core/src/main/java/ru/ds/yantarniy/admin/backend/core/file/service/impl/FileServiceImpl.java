package ru.ds.yantarniy.admin.backend.core.file.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.ds.yantarniy.admin.backend.core.file.model.FileUploadRequest;
import ru.ds.yantarniy.admin.backend.core.file.service.FileService;
import ru.ds.yantarniy.admin.backend.dao.entity.file.FileEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.file.FileRepository;
import ru.ds.yantarniy.admin.backend.dao.specification.Specifications;
import ru.ds.yantarniy.admin.backend.minio.model.request.GetMinioObjectRequest;
import ru.ds.yantarniy.admin.backend.minio.model.request.PutMinioObjectRequest;
import ru.ds.yantarniy.admin.backend.minio.model.request.RemoveMinioObjectRequest;
import ru.ds.yantarniy.admin.backend.minio.model.response.GetMinioObjectResponse;
import ru.ds.yantarniy.admin.backend.minio.model.response.PutMinioObjectResponse;
import ru.ds.yantarniy.admin.backend.minio.service.MinioService;

import javax.persistence.EntityNotFoundException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileServiceImpl implements FileService {

    static String FILE_WITHOUT_NUM_FORMAT = "%s.%s";

    static String FILE_WITH_NUM_FORMAT = "%s (%d).%s";

    static String FILE_EXTENSION_DOT_REGEX = "\\.(?=[^\\.]+$)";

    static String NAME_COLUMN = "name";

    FileRepository fileRepository;

    MinioService minioService;

    @Override
    public FileEntity upload(FileUploadRequest uploadRequest) {
        String newFileName = getFilenameFromOriginalFileName(uploadRequest.getOriginalFileName());

        PutMinioObjectResponse putMinioObjectResponse = minioService.putObject(PutMinioObjectRequest.builder()
                .inputStream(uploadRequest.getInputStream())
                .object(newFileName)
                .build()
        );

        FileEntity fileEntity = FileEntity.builder()
                .size(uploadRequest.getSize())
                .name(newFileName)
                .storageUrl(minioService.getStorageUrl(newFileName))
                .eTag(putMinioObjectResponse.getEtag())
                .build();
        return save(fileEntity);
    }

    @Override
    public FileEntity save(FileEntity entity) {
        return fileRepository.save(entity);
    }

    @Override
    public InputStream getFileInputStreamById(Long id) {
        FileEntity file = findById(id);
        GetMinioObjectResponse response = minioService.getObject(
                GetMinioObjectRequest.builder()
                        .object(file.getName())
                        .etag(file.getETag())
                        .build()
        );
        return response.getInputStream();
    }

    @Override
    public FileEntity findById(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found FileEntity with id = %d", id)));
    }

    @Override
    public void deleteById(Long id) {
        FileEntity file = findById(id);
        minioService.removeObject(
                RemoveMinioObjectRequest.builder()
                        .object(file.getName())
                        .build()
        );
        fileRepository.deleteById(id);
    }

    private String getFilenameFromOriginalFileName(String originalFileName) {
        String[] parts = originalFileName.split(FILE_EXTENSION_DOT_REGEX);
        List<FileEntity> files = findBySameOriginalFileName(parts[0]);
        return createFileName(parts[0], files.size(), parts[1]);
    }

    private List<FileEntity> findBySameOriginalFileName(String filename) {
        return fileRepository.findAll(
                Specifications.Like.<FileEntity>builder()
                        .column(NAME_COLUMN)
                        .value(filename)
                        .build()
        );
    }

    private String createFileName(String base, int num, String extension) {
        if (num == 0) {
            return String.format(FILE_WITHOUT_NUM_FORMAT, base, extension);
        }
        return String.format(FILE_WITH_NUM_FORMAT, base, num, extension);
    }

    @Override
    public Page<FileEntity> findAll(Specification<FileEntity> specification, PageRequest request) {
        return fileRepository.findAll(specification, request);
    }

    @Override
    public long countItemsByFilter(Specification<FileEntity> specification) {
        return fileRepository.count(specification);
    }
}
