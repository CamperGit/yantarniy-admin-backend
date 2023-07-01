package ru.ds.yantarniy.admin.backend.core.schedule.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ds.yantarniy.admin.backend.core.schedule.service.ScheduleTypeService;
import ru.ds.yantarniy.admin.backend.dao.entity.schedule.ScheduleEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.schedule.ScheduleTypeEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.schedule.ScheduleTypeRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleTypeServiceImpl implements ScheduleTypeService {

    ScheduleTypeRepository scheduleTypeRepository;

    @Override
    public ScheduleTypeEntity save(ScheduleTypeEntity entity) {
        return scheduleTypeRepository.save(entity);
    }

    @Override
    public ScheduleTypeEntity findByType(String type) {
        return scheduleTypeRepository.findByType(type)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found ScheduleTypeEntity with type = %s", type)));
    }

    @Override
    public ScheduleTypeEntity findById(Long id) {
        return scheduleTypeRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(String.format("Not found ScheduleTypeEntity with id = %d", id)));
    }

    @Override
    public List<ScheduleTypeEntity> findAll() {
        return scheduleTypeRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        scheduleTypeRepository.deleteById(id);
    }
}
