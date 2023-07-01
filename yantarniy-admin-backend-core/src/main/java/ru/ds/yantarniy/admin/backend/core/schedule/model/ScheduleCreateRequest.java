package ru.ds.yantarniy.admin.backend.core.schedule.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.ds.yantarniy.admin.backend.core.file.model.FileUploadRequest;
import ru.ds.yantarniy.admin.backend.dao.entity.sale.SaleEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.schedule.ScheduleEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleCreateRequest {

    /**
     * Модель расписания для загрузки
     */
    ScheduleEntity entity;

    /**
     * Изображение
     */
    FileUploadRequest fileUploadRequest;
}
