package ru.ds.yantarniy.admin.backend.core.price.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.ds.yantarniy.admin.backend.core.file.model.FileUploadRequest;
import ru.ds.yantarniy.admin.backend.dao.entity.employee.EmployeeEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.price.PriceEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PriceCreateRequest {

    /**
     * Модель прайса для загрузки
     */
    PriceEntity entity;

    /**
     * Изображение прайса
     */
    FileUploadRequest fileUploadRequest;
}
