package ru.ds.yantarniy.admin.backend.core.schedule.service;

import org.springframework.data.domain.Page;
import ru.ds.yantarniy.admin.backend.core.schedule.model.ScheduleCreateRequest;
import ru.ds.yantarniy.admin.backend.core.schedule.model.ScheduleUpdateRequest;
import ru.ds.yantarniy.admin.backend.core.schedule.model.SearchSchedulesModel;
import ru.ds.yantarniy.admin.backend.core.search.SpecificationsSearchService;
import ru.ds.yantarniy.admin.backend.dao.entity.schedule.ScheduleEntity;

public interface ScheduleService extends SpecificationsSearchService<ScheduleEntity> {

    ScheduleEntity create(ScheduleCreateRequest request);

    ScheduleEntity update(ScheduleUpdateRequest request);

    ScheduleEntity save(ScheduleEntity entity);

    ScheduleEntity findById(Long id);

    Page<ScheduleEntity> search(SearchSchedulesModel searchModel);

    void deleteById(Long id);
}
