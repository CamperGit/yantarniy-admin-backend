package ru.ds.yantarniy.admin.backend.rest.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel("Модель пользователя бота")
public class CustomerDto {

    @ApiModelProperty(value = "ID пользователя бота", required = true)
    Long id;

    @ApiModelProperty(value = "ID чата в телеграм", required = true)
    String chatId;

    @ApiModelProperty(value = "Имя пользователя бота")
    String firstname;

    @ApiModelProperty(value = "Фамилия пользователя бота")
    String lastname;

    @ApiModelProperty(value = "Имя пользователя бота", required = true)
    String username;

    @ApiModelProperty(value = "Дата запуска бота", required = true)
    LocalDateTime createdDate;

    @ApiModelProperty(value = "Дата последней выполненной команды", required = true)
    LocalDateTime lastEntry;
}
