package ru.ds.yantarniy.admin.backend.dao.entity.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduleTypeRepository extends JpaRepository<ScheduleTypeEntity, Long> {

    Optional<ScheduleTypeEntity> findByType(String type);
}
