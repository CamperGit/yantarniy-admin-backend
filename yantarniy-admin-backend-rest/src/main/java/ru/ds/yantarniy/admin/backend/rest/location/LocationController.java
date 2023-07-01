package ru.ds.yantarniy.admin.backend.rest.location;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ds.yantarniy.admin.backend.common.orika.DefaultMapper;
import ru.ds.yantarniy.admin.backend.common.orika.OrikaMapper;
import ru.ds.yantarniy.admin.backend.core.employee.service.EmployeeTypeService;
import ru.ds.yantarniy.admin.backend.core.location.service.LocationService;
import ru.ds.yantarniy.admin.backend.rest.employee.EmployeeTypeDto;

import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/v1/locations")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Api(tags = {"Контроллер для работы с локациями"})
public class LocationController {

    LocationService locationService;

    @DefaultMapper
    OrikaMapper mapper;

    @GetMapping
    @ApiOperation("Получение данных о локациях")
    public ResponseEntity<List<LocationDto>> findAll() {
        return ResponseEntity.ok(mapper.mapAsList(locationService.findAll(), LocationDto.class));
    }
}
