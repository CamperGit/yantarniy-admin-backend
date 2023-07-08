package ru.ds.yantarniy.admin.backend.core.price.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.ds.yantarniy.admin.backend.core.file.service.FileService;
import ru.ds.yantarniy.admin.backend.core.price.model.PriceCreateRequest;
import ru.ds.yantarniy.admin.backend.core.price.model.PriceUpdateRequest;
import ru.ds.yantarniy.admin.backend.core.price.service.PriceService;
import ru.ds.yantarniy.admin.backend.core.search.SpecificationsSearchService;
import ru.ds.yantarniy.admin.backend.dao.entity.file.FileEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.price.PriceEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.price.PriceRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PriceServiceImpl implements PriceService {

    PriceRepository priceRepository;

    FileService fileService;

    @Override
    public PriceEntity create(PriceCreateRequest request) {
        PriceEntity entity = request.getEntity();
        Optional.ofNullable(request.getFileUploadRequest()).ifPresent(fileUploadRequest -> {
            FileEntity file = fileService.upload(fileUploadRequest);
            entity.setFile(file);
        });
        return save(entity);
    }

    @Override
    public PriceEntity update(PriceUpdateRequest request) {
        PriceEntity entity = request.getEntity();
        Optional.ofNullable(request.getFileUploadRequest()).ifPresent(fileUploadRequest -> {
            FileEntity newFile = fileService.upload(fileUploadRequest);
            Optional.ofNullable(entity.getFile()).ifPresent(currentEntityFile -> fileService.deleteById(currentEntityFile.getId()));
            entity.setFile(newFile);
        });
        return save(entity);
    }

    @Override
    public PriceEntity save(PriceEntity entity) {
        return priceRepository.save(entity);
    }

    @Override
    public PriceEntity findById(Long id) {
        return priceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found PriceEntity with id = %d", id)));
    }

    @Override
    public void deleteById(Long id) {
        PriceEntity price = findById(id);
        FileEntity file = price.getFile();
        if (file != null) {
            fileService.deleteById(file.getId());
        }
        priceRepository.deleteById(id);
    }

    @Override
    public Page<PriceEntity> findAll(Specification<PriceEntity> specification, PageRequest request) {
        return priceRepository.findAll(specification, request);
    }

    @Override
    public long countItemsByFilter(Specification<PriceEntity> specification) {
        return priceRepository.count(specification);
    }
}
