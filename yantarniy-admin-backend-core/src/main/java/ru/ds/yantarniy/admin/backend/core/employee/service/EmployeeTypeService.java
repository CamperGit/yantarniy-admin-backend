package ru.ds.yantarniy.admin.backend.core.employee.service;

import ru.ds.yantarniy.admin.backend.dao.entity.employee.EmployeeTypeEntity;

import java.util.List;

public interface EmployeeTypeService {

    EmployeeTypeEntity save(EmployeeTypeEntity entity);

    EmployeeTypeEntity findByType(String type);

    EmployeeTypeEntity findById(Long id);

    List<EmployeeTypeEntity> findAll();

    void deleteById(Long id);
}
