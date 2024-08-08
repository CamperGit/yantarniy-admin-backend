package ru.ds.yantarniy.admin.backend.core.price.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.ds.yantarniy.admin.backend.core.file.model.FileUploadRequest;
import ru.ds.yantarniy.admin.backend.core.file.service.FileService;
import ru.ds.yantarniy.admin.backend.core.price.model.PriceCreateRequest;
import ru.ds.yantarniy.admin.backend.core.price.model.PriceUpdateRequest;
import ru.ds.yantarniy.admin.backend.core.price.model.SearchPricesModel;
import ru.ds.yantarniy.admin.backend.core.price.service.PriceService;
import ru.ds.yantarniy.admin.backend.dao.entity.file.FileEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.price.PriceEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.price.PriceRepository;
import ru.ds.yantarniy.admin.backend.dao.specification.Specifications;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static ru.ds.yantarniy.admin.backend.dao.specification.Specifications.equalOrReturnNull;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PriceServiceImpl implements PriceService {

    static String ID_PROPERTY_NAME = "id";

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
    public PriceEntity save(PriceEntity entity) {
        return priceRepository.save(entity);
    }

    @Override
    public PriceEntity findById(Long id) {
        return priceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found PriceEntity with id = %d", id)));
    }

    @Override
    public Page<PriceEntity> search(SearchPricesModel searchModel) {
        int pageNumber = searchModel.getPageNumber();
        int pageSize = searchModel.getPageSize();
        Sort.Direction sortDirection = searchModel.getSortDirection();
        String sortProperty = searchModel.getSortProperty();
        List<Specification<PriceEntity>> specifications = Arrays.asList(
                equalOrReturnNull("location.id", searchModel.getLocationId())
        );
        return priceRepository.findAll(
                Specifications.And.<PriceEntity>builder()
                        .specifications(specifications)
                        .build(),
                sortDirection == null || StringUtils.isEmpty(sortProperty)
                        ? PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, ID_PROPERTY_NAME)
                        : PageRequest.of(pageNumber, pageSize, sortDirection, sortProperty));
    }

    @Override
    public void deleteById(Long id) {
        PriceEntity price = findById(id);
        FileEntity file = price.getFile();
        priceRepository.deleteById(id);
        if (file != null) {
            fileService.deleteById(file.getId());
        }
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
