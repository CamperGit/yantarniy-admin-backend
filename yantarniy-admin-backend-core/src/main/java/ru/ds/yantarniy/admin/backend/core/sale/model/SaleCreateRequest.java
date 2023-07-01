package ru.ds.yantarniy.admin.backend.core.sale.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.ds.yantarniy.admin.backend.core.file.model.FileUploadRequest;
import ru.ds.yantarniy.admin.backend.dao.entity.price.PriceEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.sale.SaleEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaleCreateRequest {

    /**
     * Модель скидки для загрузки
     */
    SaleEntity entity;

    /**
     * Изображение
     */
    FileUploadRequest fileUploadRequest;
}
