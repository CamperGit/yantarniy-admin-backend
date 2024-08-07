package ru.ds.yantarniy.admin.backend.core.user.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.ds.yantarniy.admin.backend.core.user.service.UserService;
import ru.ds.yantarniy.admin.backend.dao.entity.user.UserEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.user.UserRepository;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Override
    public UserEntity save(UserEntity entity) {
        return userRepository.save(entity);
    }

    @Override
    public UserEntity findById(String id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("Not found user with id = %s", id)
                )
        );
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsById(String id) {
        return userRepository.existsById(id);
    }
}
