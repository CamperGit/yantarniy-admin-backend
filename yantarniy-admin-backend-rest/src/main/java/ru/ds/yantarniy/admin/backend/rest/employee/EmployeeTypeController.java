package ru.ds.yantarniy.admin.backend.rest.employee;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ds.yantarniy.admin.backend.common.orika.DefaultMapper;
import ru.ds.yantarniy.admin.backend.common.orika.OrikaMapper;
import ru.ds.yantarniy.admin.backend.core.employee.service.EmployeeTypeService;
import ru.ds.yantarniy.admin.backend.dao.entity.employee.EmployeeEntity;

import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/v1/employee-types")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Api(tags = {"Контроллер для работы с типами сотрудников"})
public class EmployeeTypeController {

    EmployeeTypeService employeeTypeService;

    @DefaultMapper
    OrikaMapper mapper;

    @GetMapping
    @ApiOperation("Получение данных о типах сотрудников")
    public ResponseEntity<List<EmployeeTypeDto>> findAll() {
        return ResponseEntity.ok(mapper.mapAsList(employeeTypeService.findAll(), EmployeeTypeDto.class));
    }
}
