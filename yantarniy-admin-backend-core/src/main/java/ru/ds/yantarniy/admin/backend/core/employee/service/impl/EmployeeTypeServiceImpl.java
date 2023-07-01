package ru.ds.yantarniy.admin.backend.core.employee.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.ds.yantarniy.admin.backend.core.employee.service.EmployeeTypeService;
import ru.ds.yantarniy.admin.backend.dao.entity.employee.EmployeeTypeEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.employee.EmployeeTypeRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeTypeServiceImpl implements EmployeeTypeService {

    EmployeeTypeRepository employeeTypeRepository;

    @Override
    public EmployeeTypeEntity save(EmployeeTypeEntity entity) {
        return employeeTypeRepository.save(entity);
    }

    @Override
    public EmployeeTypeEntity findByType(String type) {
        return employeeTypeRepository.findByType(type)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found EmployeeTypeEntity with type = %s", type)));
    }

    @Override
    public EmployeeTypeEntity findById(Long id) {
        return employeeTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found EmployeeTypeEntity with id = %d", id)));
    }

    @Override
    public List<EmployeeTypeEntity> findAll() {
        return employeeTypeRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        employeeTypeRepository.deleteById(id);
    }
}
