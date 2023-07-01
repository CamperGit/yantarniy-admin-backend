package ru.ds.yantarniy.admin.backend.rest.price;

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
import ru.ds.yantarniy.admin.backend.core.price.model.PriceCreateRequest;
import ru.ds.yantarniy.admin.backend.core.price.model.PriceUpdateRequest;
import ru.ds.yantarniy.admin.backend.core.price.service.PriceService;
import ru.ds.yantarniy.admin.backend.dao.entity.price.PriceEntity;
import ru.ds.yantarniy.admin.backend.rest.common.MultipartFileUtils;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/v1/prices")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Api(tags = {"Контроллер для работы с прайсом"})
public class PriceController {

    PriceService priceService;

    @DefaultMapper
    OrikaMapper mapper;

    @PostMapping("/creating")
    @ApiOperation("Загрузка прайса")
    public ResponseEntity<PriceDto> create(
            @ApiParam(value = "Сущность прайса")
            @RequestPart(value = "price") PriceDto priceDto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        FileUploadRequest fileUploadRequest = MultipartFileUtils.convert(image);
        PriceEntity entity = mapper.map(priceDto, PriceEntity.class);
        PriceEntity createdPrice = priceService.create(PriceCreateRequest.builder()
                .entity(entity)
                .fileUploadRequest(fileUploadRequest)
                .build());
        return ResponseEntity.ok(mapper.map(createdPrice, PriceDto.class));
    }

    @PostMapping("/updating")
    @ApiOperation("Обновление прайса")
    public ResponseEntity<PriceDto> update(
            @ApiParam(value = "Сущность прайса")
            @RequestPart(value = "price") PriceDto priceDto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        FileUploadRequest fileUploadRequest = MultipartFileUtils.convert(image);
        PriceEntity entity = mapper.map(priceDto, PriceEntity.class);
        PriceEntity updatedPrice = priceService.update(PriceUpdateRequest.builder()
                .entity(entity)
                .fileUploadRequest(fileUploadRequest)
                .build());
        return ResponseEntity.ok(mapper.map(updatedPrice, PriceDto.class));
    }

    @GetMapping("/{id}")
    @ApiOperation("Получение данных о прайсе по его ID")
    public ResponseEntity<PriceDto> findById(
            @ApiParam(value = "ID прайса в системе", required = true)
            @PathVariable
            Long id
    ) {
        PriceEntity price = priceService.findById(id);
        return ResponseEntity.ok(mapper.map(price, PriceDto.class));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Удаление прайса по ID")
    public ResponseEntity<Void> deleteById(
            @ApiParam(value = "ID прайса", required = true)
            @PathVariable Long id
    ) {
        priceService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}