package ru.ds.yantarniy.admin.backend.rest.telegram;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.ds.yantarniy.admin.backend.common.orika.DefaultMapper;
import ru.ds.yantarniy.admin.backend.common.orika.OrikaMapper;
import ru.ds.yantarniy.admin.backend.core.file.service.FileService;
import ru.ds.yantarniy.admin.backend.rest.common.MultipartFileUtils;
import ru.ds.yantarniy.admin.backend.telegram.model.SendingReport;
import ru.ds.yantarniy.admin.backend.telegram.service.SenderService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/v1/telegram-sends")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Api(tags = {"Контроллер для работы с рассылками телеграмм"})
public class TelegramSendController {

    SenderService senderService;

    FileService fileService;

    @DefaultMapper
    OrikaMapper mapper;

    @PostMapping
    @ApiOperation("Загрузка расписания")
    public ResponseEntity<TelegramSendResponse> send(
            @ApiParam(value = "Сущность расписания")
            @RequestPart(value = "request") TelegramSendRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {

        byte[] bytes = getBytes(request, image);
        SendingReport sendingReport = senderService.sendMessageToCustomers(bytes, request.getDescription(), request.getOnlyAdmins());
        return ResponseEntity.ok(mapper.map(sendingReport, TelegramSendResponse.class));
    }

    private byte[] getBytes(TelegramSendRequest request, MultipartFile image) {
        return Optional.ofNullable(MultipartFileUtils.getBytes(image))
                .orElse(Optional.ofNullable(request.getFileId())
                        .map(fileService::getFileInputStreamById)
                        .map(inputStream -> {
                            try {
                                return inputStream.readAllBytes();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .orElse(null));
    }
}
