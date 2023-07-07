package ru.ds.yantarniy.admin.backend.dao.entity.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long>, JpaSpecificationExecutor<ScheduleEntity> {
}
