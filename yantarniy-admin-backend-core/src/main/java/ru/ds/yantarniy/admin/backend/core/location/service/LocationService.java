package ru.ds.yantarniy.admin.backend.core.location.service;

import ru.ds.yantarniy.admin.backend.dao.entity.location.LocationEntity;

import java.util.List;

public interface LocationService {

    LocationEntity save(LocationEntity entity);

    LocationEntity findByType(String type);

    LocationEntity findById(Long id);

    List<LocationEntity> findAll();

    void deleteById(Long id);
}
