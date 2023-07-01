package ru.ds.yantarniy.admin.backend.rest.schedule;

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
import ru.ds.yantarniy.admin.backend.core.file.model.FileUploadRequest;
import ru.ds.yantarniy.admin.backend.core.schedule.model.ScheduleCreateRequest;
import ru.ds.yantarniy.admin.backend.core.schedule.model.ScheduleUpdateRequest;
import ru.ds.yantarniy.admin.backend.core.schedule.service.ScheduleService;
import ru.ds.yantarniy.admin.backend.dao.entity.schedule.ScheduleEntity;
import ru.ds.yantarniy.admin.backend.rest.common.MultipartFileUtils;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/v1/schedules")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Api(tags = {"Контроллер для работы с расписанием"})
public class ScheduleController {

    ScheduleService scheduleService;

    @DefaultMapper
    OrikaMapper mapper;

    @PostMapping("/creating")
    @ApiOperation("Загрузка расписания")
    public ResponseEntity<ScheduleDto> create(
            @ApiParam(value = "Сущность расписания")
            @RequestPart(value = "schedule") ScheduleDto scheduleDto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        FileUploadRequest fileUploadRequest = MultipartFileUtils.convert(image);
        ScheduleEntity entity = mapper.map(scheduleDto, ScheduleEntity.class);
        ScheduleEntity createdSchedule = scheduleService.create(ScheduleCreateRequest.builder()
                .entity(entity)
                .fileUploadRequest(fileUploadRequest)
                .build());
        return ResponseEntity.ok(mapper.map(createdSchedule, ScheduleDto.class));
    }

    @PostMapping("/updating")
    @ApiOperation("Обновление расписания")
    public ResponseEntity<ScheduleDto> update(
            @ApiParam(value = "Сущность расписания")
            @RequestPart(value = "schedule") ScheduleDto scheduleDto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        FileUploadRequest fileUploadRequest = MultipartFileUtils.convert(image);
        ScheduleEntity entity = mapper.map(scheduleDto, ScheduleEntity.class);
        ScheduleEntity updatedSchedule = scheduleService.update(ScheduleUpdateRequest.builder()
                .entity(entity)
                .fileUploadRequest(fileUploadRequest)
                .build());
        return ResponseEntity.ok(mapper.map(updatedSchedule, ScheduleDto.class));
    }

    @GetMapping("/{id}")
    @ApiOperation("Получение данных о расписании по ID")
    public ResponseEntity<ScheduleDto> findById(
            @ApiParam(value = "ID расписания в системе", required = true)
            @PathVariable
            Long id
    ) {
        ScheduleEntity schedule = scheduleService.findById(id);
        return ResponseEntity.ok(mapper.map(schedule, ScheduleDto.class));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Удаление расписания по ID")
    public ResponseEntity<Void> deleteById(
            @ApiParam(value = "ID расписания", required = true)
            @PathVariable Long id
    ) {
        scheduleService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}