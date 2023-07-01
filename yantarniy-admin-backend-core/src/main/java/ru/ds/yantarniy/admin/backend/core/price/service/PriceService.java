package ru.ds.yantarniy.admin.backend.core.price.service;

import ru.ds.yantarniy.admin.backend.core.price.model.PriceCreateRequest;
import ru.ds.yantarniy.admin.backend.dao.entity.price.PriceEntity;

public interface PriceService {

    PriceEntity create(PriceCreateRequest request);

    PriceEntity save(PriceEntity entity);

    PriceEntity findById(Long id);

    void deleteById(Long id);
}
