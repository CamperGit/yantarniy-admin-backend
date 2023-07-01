package ru.ds.yantarniy.admin.backend.core.sale.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.ds.yantarniy.admin.backend.core.file.model.FileUploadRequest;
import ru.ds.yantarniy.admin.backend.dao.entity.sale.SaleEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaleUpdateRequest {

    /**
     * Модель скидки для обновления
     */
    SaleEntity entity;

    /**
     * Изображение
     */
    FileUploadRequest fileUploadRequest;
}
