package ru.ds.yantarniy.admin.backend.core.user.service;

import org.springframework.security.oauth2.jwt.Jwt;
import ru.ds.yantarniy.admin.backend.dao.entity.user.UserEntity;

public interface SecurityService {

    UserEntity getOrCreateUserFromJwt(Jwt jwt);
}
