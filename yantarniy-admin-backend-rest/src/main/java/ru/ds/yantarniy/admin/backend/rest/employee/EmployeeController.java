package ru.ds.yantarniy.admin.backend.rest.employee;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ds.yantarniy.admin.backend.common.orika.DefaultMapper;
import ru.ds.yantarniy.admin.backend.common.orika.OrikaMapper;
import ru.ds.yantarniy.admin.backend.core.employee.model.EmployeeCreateRequest;
import ru.ds.yantarniy.admin.backend.core.employee.model.EmployeeUpdateRequest;
import ru.ds.yantarniy.admin.backend.core.employee.service.EmployeeService;
import ru.ds.yantarniy.admin.backend.core.file.model.FileUploadRequest;
import ru.ds.yantarniy.admin.backend.dao.entity.employee.EmployeeEntity;
import ru.ds.yantarniy.admin.backend.rest.common.MultipartFileUtils;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/v1/employees")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Api(tags = {"Контроллер для работы с сотрудниками"})
public class EmployeeController {

    EmployeeService employeeService;

    @DefaultMapper
    OrikaMapper mapper;

    @PostMapping("/creating")
    @ApiOperation("Создание сотрудника")
    public ResponseEntity<EmployeeDto> create(
            @ApiParam(value = "Сущность сотрудника")
            @RequestPart(value = "employee") EmployeeDto employeeDto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        FileUploadRequest fileUploadRequest = MultipartFileUtils.convert(image);
        EmployeeEntity entity = mapper.map(employeeDto, EmployeeEntity.class);
        EmployeeEntity createdEmployee = employeeService.create(EmployeeCreateRequest.builder()
                .entity(entity)
                .fileUploadRequest(fileUploadRequest)
                .build());
        return ResponseEntity.ok(mapper.map(createdEmployee, EmployeeDto.class));
    }

    @PostMapping("/updating")
    @ApiOperation("Обновление сотрудника")
    public ResponseEntity<EmployeeDto> update(
            @ApiParam(value = "Сущность сотрудника")
            @RequestPart(value = "employee") EmployeeDto employeeDto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        FileUploadRequest fileUploadRequest = MultipartFileUtils.convert(image);
        EmployeeEntity entity = mapper.map(employeeDto, EmployeeEntity.class);
        EmployeeEntity updatedEmployee = employeeService.update(EmployeeUpdateRequest.builder()
                .entity(entity)
                .fileUploadRequest(fileUploadRequest)
                .build());
        return ResponseEntity.ok(mapper.map(updatedEmployee, EmployeeDto.class));
    }

    @GetMapping("/{id}")
    @ApiOperation("Получение данных о сотруднике по его ID")
    public ResponseEntity<EmployeeDto> findById(
            @ApiParam(value = "ID пользователя в системе", required = true)
            @PathVariable
            Long id
    ) {
        EmployeeEntity employee = employeeService.findById(id);
        return ResponseEntity.ok(mapper.map(employee, EmployeeDto.class));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Удаление сотрудника по ID")
    public ResponseEntity<Void> deleteById(
            @ApiParam(value = "ID сотрудника", required = true)
            @PathVariable Long id
    ) {
        employeeService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}