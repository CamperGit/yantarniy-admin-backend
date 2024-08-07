package ru.ds.yantarniy.admin.backend.core.user.service;

import ru.ds.yantarniy.admin.backend.dao.entity.user.UserEntity;

public interface UserService {

    UserEntity save(UserEntity entity);

    UserEntity findById(String id);

    void deleteById(String id);

    boolean existsById(String id);
}
