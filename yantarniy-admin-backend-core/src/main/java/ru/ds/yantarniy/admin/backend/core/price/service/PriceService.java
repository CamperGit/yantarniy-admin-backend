package ru.ds.yantarniy.admin.backend.core.price.service;

import ru.ds.yantarniy.admin.backend.core.price.model.PriceCreateRequest;
import ru.ds.yantarniy.admin.backend.core.price.model.PriceUpdateRequest;
import ru.ds.yantarniy.admin.backend.core.search.SpecificationsSearchService;
import ru.ds.yantarniy.admin.backend.dao.entity.price.PriceEntity;

public interface PriceService extends SpecificationsSearchService<PriceEntity> {

    PriceEntity create(PriceCreateRequest request);

    PriceEntity update(PriceUpdateRequest request);

    PriceEntity save(PriceEntity entity);

    PriceEntity findById(Long id);

    void deleteById(Long id);
}
