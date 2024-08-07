package ru.ds.yantarniy.admin.backend.core.employee.service;

import ru.ds.yantarniy.admin.backend.core.employee.model.EmployeeCreateRequest;
import ru.ds.yantarniy.admin.backend.core.employee.model.EmployeeUpdateRequest;
import ru.ds.yantarniy.admin.backend.core.search.SpecificationsSearchService;
import ru.ds.yantarniy.admin.backend.dao.entity.employee.EmployeeEntity;

public interface EmployeeService extends SpecificationsSearchService<EmployeeEntity> {

    EmployeeEntity create(EmployeeCreateRequest request);

    EmployeeEntity update(EmployeeUpdateRequest request);

    EmployeeEntity save(EmployeeEntity entity);

    EmployeeEntity findById(Long id);

    void deleteById(Long id);
}
