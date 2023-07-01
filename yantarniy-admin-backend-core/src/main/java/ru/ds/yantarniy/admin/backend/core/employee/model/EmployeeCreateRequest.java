package ru.ds.yantarniy.admin.backend.core.employee.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.ds.yantarniy.admin.backend.core.file.model.FileUploadRequest;
import ru.ds.yantarniy.admin.backend.dao.entity.employee.EmployeeEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeCreateRequest {

    /**
     * Модель сотрудника для загрузки
     */
    EmployeeEntity entity;

    /**
     * Изображение сотрудника
     */
    FileUploadRequest fileUploadRequest;
}
