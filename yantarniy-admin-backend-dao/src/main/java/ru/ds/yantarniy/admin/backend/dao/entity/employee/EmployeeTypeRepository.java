package ru.ds.yantarniy.admin.backend.dao.entity.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeTypeRepository extends JpaRepository<EmployeeTypeEntity, Long> {

    Optional<EmployeeTypeEntity> findByType(String type);
}
