package ru.ds.yantarniy.admin.backend.rest.sale;

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
import ru.ds.yantarniy.admin.backend.core.sale.model.SaleCreateRequest;
import ru.ds.yantarniy.admin.backend.core.sale.model.SaleUpdateRequest;
import ru.ds.yantarniy.admin.backend.core.sale.service.SaleService;
import ru.ds.yantarniy.admin.backend.dao.entity.sale.SaleEntity;
import ru.ds.yantarniy.admin.backend.rest.common.MultipartFileUtils;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/v1/sales")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Api(tags = {"Контроллер для работы со скидками"})
public class SaleController {

    SaleService saleService;

    @DefaultMapper
    OrikaMapper mapper;

    @PostMapping("/creating")
    @ApiOperation("Загрузка скидки")
    public ResponseEntity<SaleDto> create(
            @ApiParam(value = "Сущность скидки")
            @RequestPart(value = "sale") SaleDto saleDto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        FileUploadRequest fileUploadRequest = MultipartFileUtils.convert(image);
        SaleEntity entity = mapper.map(saleDto, SaleEntity.class);
        SaleEntity createdSale = saleService.create(SaleCreateRequest.builder()
                .entity(entity)
                .fileUploadRequest(fileUploadRequest)
                .build());
        return ResponseEntity.ok(mapper.map(createdSale, SaleDto.class));
    }

    @PostMapping("/updating")
    @ApiOperation("Обновление скидки")
    public ResponseEntity<SaleDto> update(
            @ApiParam(value = "Сущность скидки")
            @RequestPart(value = "sale") SaleDto saleDto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        FileUploadRequest fileUploadRequest = MultipartFileUtils.convert(image);
        SaleEntity entity = mapper.map(saleDto, SaleEntity.class);
        SaleEntity updatedSale = saleService.update(SaleUpdateRequest.builder()
                .entity(entity)
                .fileUploadRequest(fileUploadRequest)
                .build());
        return ResponseEntity.ok(mapper.map(updatedSale, SaleDto.class));
    }

    @GetMapping("/{id}")
    @ApiOperation("Получение данных о скидке по ID")
    public ResponseEntity<SaleDto> findById(
            @ApiParam(value = "ID скидки в системе", required = true)
            @PathVariable
            Long id
    ) {
        SaleEntity sale = saleService.findById(id);
        return ResponseEntity.ok(mapper.map(sale, SaleDto.class));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Удаление скидки по ID")
    public ResponseEntity<Void> deleteById(
            @ApiParam(value = "ID скидки", required = true)
            @PathVariable Long id
    ) {
        saleService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}