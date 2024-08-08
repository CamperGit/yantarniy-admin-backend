package ru.ds.yantarniy.admin.backend.rest.telegram;

import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel("Модель запроса для телеграмм рассылки")
public class TelegramSendRequest {

    String description;

    Boolean onlyAdmins;

    Long fileId;
}
