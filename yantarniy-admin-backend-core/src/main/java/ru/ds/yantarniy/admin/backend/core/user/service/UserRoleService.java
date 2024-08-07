package ru.ds.yantarniy.admin.backend.core.user.service;

import ru.ds.yantarniy.admin.backend.dao.entity.user.UserRoleEntity;

import java.util.List;

public interface UserRoleService {

    UserRoleEntity save(UserRoleEntity userRoleEntity);

    UserRoleEntity getById(Long id);

    List<UserRoleEntity> findAll();

    List<UserRoleEntity> findAllRolesByAuthorities(List<String> authorities);

    void deleteById(Long id);
}
