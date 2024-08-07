package ru.ds.yantarniy.admin.backend.rest.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel("Модель роли пользователя")
public class UserRoleDto {

    @ApiModelProperty(value = "Идентификатор роли", required = true)
    Long id;

    @ApiModelProperty(value = "Наименование роли", required = true)
    String title;
}
