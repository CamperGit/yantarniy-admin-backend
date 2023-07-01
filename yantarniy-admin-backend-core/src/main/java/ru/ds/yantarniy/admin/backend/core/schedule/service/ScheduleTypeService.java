package ru.ds.yantarniy.admin.backend.core.schedule.service;

import ru.ds.yantarniy.admin.backend.dao.entity.schedule.ScheduleEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.schedule.ScheduleTypeEntity;

import java.util.List;

public interface ScheduleTypeService {

    ScheduleTypeEntity save(ScheduleTypeEntity entity);

    ScheduleTypeEntity findByType(String type);

    ScheduleTypeEntity findById(Long id);

    List<ScheduleTypeEntity> findAll();

    void deleteById(Long id);
}
