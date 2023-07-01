package ru.ds.yantarniy.admin.backend.core.employee.service;

import ru.ds.yantarniy.admin.backend.core.employee.model.EmployeeCreateRequest;
import ru.ds.yantarniy.admin.backend.dao.entity.employee.EmployeeEntity;

public interface EmployeeService {

    EmployeeEntity create(EmployeeCreateRequest request);

    EmployeeEntity save(EmployeeEntity entity);

    EmployeeEntity findById(Long id);

    void deleteById(Long id);
}
