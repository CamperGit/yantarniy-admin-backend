package ru.ds.yantarniy.admin.backend.core.schedule.service;

import ru.ds.yantarniy.admin.backend.core.schedule.model.ScheduleCreateRequest;
import ru.ds.yantarniy.admin.backend.core.schedule.model.ScheduleUpdateRequest;
import ru.ds.yantarniy.admin.backend.dao.entity.sale.SaleEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.schedule.ScheduleEntity;

public interface ScheduleService {

    ScheduleEntity create(ScheduleCreateRequest request);

    ScheduleEntity update(ScheduleUpdateRequest request);

    ScheduleEntity save(ScheduleEntity entity);

    ScheduleEntity findById(Long id);

    void deleteById(Long id);
}
