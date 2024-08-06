package ru.ds.yantarniy.admin.backend.core.sale.service;

import ru.ds.yantarniy.admin.backend.core.sale.model.SaleCreateRequest;
import ru.ds.yantarniy.admin.backend.core.sale.model.SaleUpdateRequest;
import ru.ds.yantarniy.admin.backend.core.search.SpecificationsSearchService;
import ru.ds.yantarniy.admin.backend.dao.entity.price.PriceEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.sale.SaleEntity;

public interface SaleService extends SpecificationsSearchService<SaleEntity> {

    SaleEntity create(SaleCreateRequest request);

    SaleEntity update(SaleUpdateRequest request);

    SaleEntity save(SaleEntity entity);

    SaleEntity findById(Long id);

    void deleteById(Long id);
}
