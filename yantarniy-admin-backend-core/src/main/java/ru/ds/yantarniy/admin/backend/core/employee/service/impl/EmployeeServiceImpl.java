package ru.ds.yantarniy.admin.backend.core.employee.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ds.yantarniy.admin.backend.core.employee.model.EmployeeCreateRequest;
import ru.ds.yantarniy.admin.backend.core.employee.service.EmployeeService;
import ru.ds.yantarniy.admin.backend.core.file.service.FileService;
import ru.ds.yantarniy.admin.backend.dao.entity.employee.EmployeeEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.employee.EmployeeRepository;
import ru.ds.yantarniy.admin.backend.dao.entity.file.FileEntity;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository;

    FileService fileService;

    @Override
    public EmployeeEntity create(EmployeeCreateRequest request) {
        FileEntity file = null;
        if (request.getFileUploadRequest() != null) {
            file = fileService.upload(request.getFileUploadRequest());
        }
        EmployeeEntity entity = request.getEntity();
        entity.setFile(file);
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
}
