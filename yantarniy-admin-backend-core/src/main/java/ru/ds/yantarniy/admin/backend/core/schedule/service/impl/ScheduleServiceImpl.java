package ru.ds.yantarniy.admin.backend.core.schedule.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ds.yantarniy.admin.backend.core.file.service.FileService;
import ru.ds.yantarniy.admin.backend.core.schedule.model.ScheduleCreateRequest;
import ru.ds.yantarniy.admin.backend.core.schedule.service.ScheduleService;
import ru.ds.yantarniy.admin.backend.dao.entity.file.FileEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.sale.SaleEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.sale.SaleRepository;
import ru.ds.yantarniy.admin.backend.dao.entity.schedule.ScheduleEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.schedule.ScheduleRepository;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleServiceImpl implements ScheduleService {

    ScheduleRepository scheduleRepository;

    FileService fileService;

    @Override
    public ScheduleEntity create(ScheduleCreateRequest request) {
        FileEntity file = null;
        if (request.getFileUploadRequest() != null) {
            file = fileService.upload(request.getFileUploadRequest());
        }
        ScheduleEntity entity = request.getEntity();
        entity.setFile(file);
        return save(entity);
    }

    @Override
    public ScheduleEntity save(ScheduleEntity entity) {
        return scheduleRepository.save(entity);
    }

    @Override
    public ScheduleEntity findById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found ScheduleEntity with id = %d", id)));
    }

    @Override
    public void deleteById(Long id) {
        ScheduleEntity schedule = findById(id);
        FileEntity file = schedule.getFile();
        if (file != null) {
            fileService.deleteById(file.getId());
        }
        scheduleRepository.deleteById(id);
    }
}
