package ru.ds.yantarniy.admin.backend.rest.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ds.yantarniy.admin.backend.common.orika.DefaultMapper;
import ru.ds.yantarniy.admin.backend.common.orika.OrikaMapper;
import ru.ds.yantarniy.admin.backend.core.user.service.SecurityService;
import ru.ds.yantarniy.admin.backend.core.user.service.UserService;
import ru.ds.yantarniy.admin.backend.dao.entity.user.UserEntity;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/v1/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Api(tags = {"Контроллер для работы с пользователями"})
public class UserController {

    UserService userService;

    SecurityService securityService;

    @DefaultMapper
    OrikaMapper mapper;

    @GetMapping("/{id}")
    @ApiOperation("Получение данных о пользователе по ID")
    public ResponseEntity<UserDto> findById(
            @ApiParam(value = "ID пользователя в системе", required = true)
            @PathVariable
            String id
    ) {
        UserEntity user = userService.findById(id);
        return ResponseEntity.ok(mapper.map(user, UserDto.class));
    }

    @GetMapping("/info")
    public ResponseEntity<UserDto> getUserInfo(@AuthenticationPrincipal Jwt principal) {
        UserEntity user = securityService.getOrCreateUserFromJwt(principal);
        return ResponseEntity.ok(mapper.map(user, UserDto.class));
    }
}
