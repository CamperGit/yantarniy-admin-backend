package ru.ds.yantarniy.admin.backend.core.location.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ds.yantarniy.admin.backend.core.location.service.LocationService;
import ru.ds.yantarniy.admin.backend.dao.entity.location.LocationEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.location.LocationRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LocationServiceImpl implements LocationService {

    LocationRepository locationRepository;

    @Override
    public LocationEntity save(LocationEntity entity) {
        return locationRepository.save(entity);
    }

    @Override
    public LocationEntity findByType(String type) {
        return locationRepository.findByType(type)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found LocationEntity with type = %s", type)));
    }

    @Override
    public LocationEntity findById(Long id) {
        return locationRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(String.format("Not found LocationEntity with id = %d", id)));
    }

    @Override
    public List<LocationEntity> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        locationRepository.deleteById(id);
    }
}
