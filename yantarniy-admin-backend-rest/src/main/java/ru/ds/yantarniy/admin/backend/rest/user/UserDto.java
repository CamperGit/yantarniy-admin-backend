package ru.ds.yantarniy.admin.backend.rest.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel("Модель пользователя")
public class UserDto {

    @ApiModelProperty(value = "ID пользователя", required = true)
    String id;

    @ApiModelProperty(value = "Логин пользователя", required = true)
    String username;

    @ApiModelProperty(value = "Имя пользователя", required = true)
    String firstName;

    @ApiModelProperty(value = "Фамилия пользователя", required = true)
    String lastName;

    @ApiModelProperty(value = "Изображение пользователя")
    String userPicture;

    @ApiModelProperty(value = "Email")
    String email;

    @ApiModelProperty(value = "Гендер")
    String gender;

    @ApiModelProperty(value = "Локаль")
    String locale;

    @ApiModelProperty(value = "Дата последнего входа")
    LocalDateTime lastVisit;

    @ApiModelProperty(value = "Роли")
    List<UserRoleDto> roles;
}
