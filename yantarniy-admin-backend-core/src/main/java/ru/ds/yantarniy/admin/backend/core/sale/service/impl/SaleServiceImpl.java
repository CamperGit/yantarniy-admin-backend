package ru.ds.yantarniy.admin.backend.core.sale.service.impl;

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
import ru.ds.yantarniy.admin.backend.core.sale.model.SaleCreateRequest;
import ru.ds.yantarniy.admin.backend.core.sale.model.SaleUpdateRequest;
import ru.ds.yantarniy.admin.backend.core.sale.service.SaleService;
import ru.ds.yantarniy.admin.backend.core.search.SpecificationsSearchService;
import ru.ds.yantarniy.admin.backend.dao.entity.file.FileEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.sale.SaleEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.sale.SaleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SaleServiceImpl implements SaleService, SpecificationsSearchService<SaleEntity> {

    SaleRepository saleRepository;

    FileService fileService;

    @Override
    public SaleEntity create(SaleCreateRequest request) {
        SaleEntity entity = request.getEntity();
        Optional.ofNullable(request.getFileUploadRequest()).ifPresent(fileUploadRequest -> {
            FileEntity file = fileService.upload(fileUploadRequest);
            entity.setFile(file);
        });
        return save(entity);
    }

    @Override
    public SaleEntity update(SaleUpdateRequest request) {
        SaleEntity entity = request.getEntity();
        Optional<FileUploadRequest> fileUploadRequest = Optional.ofNullable(request.getFileUploadRequest());
        if (fileUploadRequest.isPresent()) {
            FileEntity newFile = fileService.upload(fileUploadRequest.get());
            FileEntity oldFile = entity.getFile();
            entity.setFile(newFile);
            entity = save(entity);
            Optional.ofNullable(oldFile).ifPresent(currentEntityFile -> fileService.deleteById(currentEntityFile.getId()));
            return entity;
        } else {
            return save(entity);
        }
    }

    @Override
    public SaleEntity save(SaleEntity entity) {
        return saleRepository.save(entity);
    }

    @Override
    public SaleEntity findById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found SaleEntity with id = %d", id)));
    }

    @Override
    public void deleteById(Long id) {
        SaleEntity sale = findById(id);
        FileEntity file = sale.getFile();
        saleRepository.deleteById(id);
        if (file != null) {
            fileService.deleteById(file.getId());
        }
    }

    @Override
    public Page<SaleEntity> findAll(Specification<SaleEntity> specification, PageRequest request) {
        return saleRepository.findAll(specification, request);
    }

    @Override
    public long countItemsByFilter(Specification<SaleEntity> specification) {
        return saleRepository.count(specification);
    }
}
