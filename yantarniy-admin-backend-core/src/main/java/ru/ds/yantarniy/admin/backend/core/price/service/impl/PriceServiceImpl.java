package ru.ds.yantarniy.admin.backend.core.price.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ds.yantarniy.admin.backend.core.file.service.FileService;
import ru.ds.yantarniy.admin.backend.core.price.model.PriceCreateRequest;
import ru.ds.yantarniy.admin.backend.core.price.service.PriceService;
import ru.ds.yantarniy.admin.backend.dao.entity.file.FileEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.price.PriceEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.price.PriceRepository;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PriceServiceImpl implements PriceService {

    PriceRepository priceRepository;

    FileService fileService;

    @Override
    public PriceEntity create(PriceCreateRequest request) {
        FileEntity file = null;
        if (request.getFileUploadRequest() != null) {
            file = fileService.upload(request.getFileUploadRequest());
        }
        PriceEntity entity = request.getEntity();
        entity.setFile(file);
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
}
