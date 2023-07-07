package ru.ds.yantarniy.admin.backend.core.sale.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.ds.yantarniy.admin.backend.core.file.service.FileService;
import ru.ds.yantarniy.admin.backend.core.sale.model.SaleCreateRequest;
import ru.ds.yantarniy.admin.backend.core.sale.model.SaleUpdateRequest;
import ru.ds.yantarniy.admin.backend.core.sale.service.SaleService;
import ru.ds.yantarniy.admin.backend.core.search.SpecificationsSearchService;
import ru.ds.yantarniy.admin.backend.dao.entity.file.FileEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.price.PriceEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.price.PriceRepository;
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
        Optional.ofNullable(request.getFileUploadRequest()).ifPresent(fileUploadRequest -> {
            FileEntity newFile = fileService.upload(fileUploadRequest);
            Optional.ofNullable(entity.getFile()).ifPresent(currentEntityFile -> fileService.deleteById(currentEntityFile.getId()));
            entity.setFile(newFile);
        });
        return save(entity);
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
        if (file != null) {
            fileService.deleteById(file.getId());
        }
        saleRepository.deleteById(id);
    }

    @Override
    public Page<SaleEntity> findAll(Specification<SaleEntity> specification, PageRequest request) {
        return saleRepository.findAll(specification, request);
    }
}
