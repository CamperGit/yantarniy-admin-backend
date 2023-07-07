package ru.ds.yantarniy.admin.backend.core.employee.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.ds.yantarniy.admin.backend.core.employee.model.EmployeeCreateRequest;
import ru.ds.yantarniy.admin.backend.core.employee.model.EmployeeUpdateRequest;
import ru.ds.yantarniy.admin.backend.core.employee.service.EmployeeService;
import ru.ds.yantarniy.admin.backend.core.file.service.FileService;
import ru.ds.yantarniy.admin.backend.core.search.SpecificationsSearchService;
import ru.ds.yantarniy.admin.backend.dao.entity.employee.EmployeeEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.employee.EmployeeRepository;
import ru.ds.yantarniy.admin.backend.dao.entity.file.FileEntity;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository;

    FileService fileService;

    @Override
    public EmployeeEntity create(EmployeeCreateRequest request) {
        EmployeeEntity entity = request.getEntity();
        Optional.ofNullable(request.getFileUploadRequest()).ifPresent(fileUploadRequest -> {
            FileEntity file = fileService.upload(fileUploadRequest);
            entity.setFile(file);
        });
        return save(entity);
    }

    @Override
    public EmployeeEntity update(EmployeeUpdateRequest request) {
        EmployeeEntity entity = request.getEntity();
        Optional.ofNullable(request.getFileUploadRequest()).ifPresent(fileUploadRequest -> {
            FileEntity newFile = fileService.upload(fileUploadRequest);
            Optional.ofNullable(entity.getFile()).ifPresent(currentEntityFile -> fileService.deleteById(currentEntityFile.getId()));
            entity.setFile(newFile);
        });
        return save(entity);
    }

    @Override
    public EmployeeEntity save(EmployeeEntity entity) {
        return employeeRepository.save(entity);
    }

    @Override
    public EmployeeEntity findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found EmployeeEntity with id = %d", id)));
    }

    @Override
    public void deleteById(Long id) {
        EmployeeEntity employee = findById(id);
        FileEntity file = employee.getFile();
        if (file != null) {
            fileService.deleteById(file.getId());
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public Page<EmployeeEntity> findAll(Specification<EmployeeEntity> specification, PageRequest request) {
        return employeeRepository.findAll(specification, request);
    }
}
