package ru.ds.yantarniy.admin.backend.core.user.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import ru.ds.yantarniy.admin.backend.core.user.service.SecurityService;
import ru.ds.yantarniy.admin.backend.core.user.service.UserRoleService;
import ru.ds.yantarniy.admin.backend.core.user.service.UserService;
import ru.ds.yantarniy.admin.backend.dao.entity.user.UserEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.user.UserRoleEntity;
import ru.ds.yantarniy.admin.backend.security.context.SystemSecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityServiceImpl implements SecurityService {

    UserService userService;
    UserRoleService userRoleService;
    SystemSecurityContextHolder systemSecurityContextHolder;
    static String LAST_NAME = "family_name";
    static String FIRST_NAME = "given_name";
    static String PREFERRED_USERNAME = "preferred_username";
    static String SUB = "sub";

    @Override
    public UserEntity getOrCreateUserFromJwt(Jwt principal) {
        String id = principal.getClaimAsString(SUB);

        List<String> currentUserRoles = systemSecurityContextHolder.getCurrentUserRoles();
        List<UserRoleEntity> rolesByAuthorities = userRoleService.findAllRolesByAuthorities(currentUserRoles);
        UserEntity userEntity;
        if (userService.existsById(id)) {
            userEntity = userService.findById(id);
            if (!CollectionUtils.isEqualCollection(userEntity.getRoles(), rolesByAuthorities)) {
                userEntity.setRoles(rolesByAuthorities);
            }
        } else {
            userEntity = new UserEntity();
            userEntity.setId(id);
            userEntity.setLastName(principal.getClaimAsString(LAST_NAME));
            userEntity.setFirstName(principal.getClaimAsString(FIRST_NAME));
            userEntity.setUsername(principal.getClaimAsString(PREFERRED_USERNAME));
            userEntity.setRoles(rolesByAuthorities);
        }
        userEntity.setLastVisit(LocalDateTime.now());
        return userService.save(userEntity);
    }
}
