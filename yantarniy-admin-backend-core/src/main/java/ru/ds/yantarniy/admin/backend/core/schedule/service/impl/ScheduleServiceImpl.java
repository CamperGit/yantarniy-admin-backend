package ru.ds.yantarniy.admin.backend.core.schedule.service.impl;

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
import ru.ds.yantarniy.admin.backend.core.schedule.model.ScheduleCreateRequest;
import ru.ds.yantarniy.admin.backend.core.schedule.model.ScheduleUpdateRequest;
import ru.ds.yantarniy.admin.backend.core.schedule.model.SearchSchedulesModel;
import ru.ds.yantarniy.admin.backend.core.schedule.service.ScheduleService;
import ru.ds.yantarniy.admin.backend.dao.entity.employee.EmployeeEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.file.FileEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.schedule.ScheduleEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.schedule.ScheduleRepository;
import ru.ds.yantarniy.admin.backend.dao.specification.Specifications;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static ru.ds.yantarniy.admin.backend.dao.specification.Specifications.equalOrReturnNull;
import static ru.ds.yantarniy.admin.backend.dao.specification.Specifications.likeOrReturnNull;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleServiceImpl implements ScheduleService {

    static String ID_PROPERTY_NAME = "id";

    ScheduleRepository scheduleRepository;

    FileService fileService;

    @Override
    public ScheduleEntity create(ScheduleCreateRequest request) {
        ScheduleEntity entity = request.getEntity();
        Optional.ofNullable(request.getFileUploadRequest()).ifPresent(fileUploadRequest -> {
            FileEntity file = fileService.upload(fileUploadRequest);
            entity.setFile(file);
        });
        return save(entity);
    }

    @Override
    public ScheduleEntity update(ScheduleUpdateRequest request) {
        ScheduleEntity entity = request.getEntity();
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
    public ScheduleEntity save(ScheduleEntity entity) {
        return scheduleRepository.save(entity);
    }

    @Override
    public ScheduleEntity findById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found ScheduleEntity with id = %d", id)));
    }

    @Override
    public Page<ScheduleEntity> search(SearchSchedulesModel searchModel) {
        int pageNumber = searchModel.getPageNumber();
        int pageSize = searchModel.getPageSize();
        Sort.Direction sortDirection = searchModel.getSortDirection();
        String sortProperty = searchModel.getSortProperty();
        List<Specification<ScheduleEntity>> specifications = Arrays.asList(
                equalOrReturnNull("type.id", searchModel.getTypeId()),
                likeOrReturnNull("description", searchModel.getDescriptionLike())
        );
        return scheduleRepository.findAll(
                Specifications.And.<ScheduleEntity>builder()
                        .specifications(specifications)
                        .build(),
                sortDirection == null || StringUtils.isEmpty(sortProperty)
                        ? PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, ID_PROPERTY_NAME)
                        : PageRequest.of(pageNumber, pageSize, sortDirection, sortProperty));
    }

    @Override
    public void deleteById(Long id) {
        ScheduleEntity schedule = findById(id);
        FileEntity file = schedule.getFile();
        scheduleRepository.deleteById(id);
        if (file != null) {
            fileService.deleteById(file.getId());
        }
    }

    @Override
    public Page<ScheduleEntity> findAll(Specification<ScheduleEntity> specification, PageRequest request) {
        return scheduleRepository.findAll(specification, request);
    }

    @Override
    public long countItemsByFilter(Specification<ScheduleEntity> specification) {
        return scheduleRepository.count(specification);
    }
}
