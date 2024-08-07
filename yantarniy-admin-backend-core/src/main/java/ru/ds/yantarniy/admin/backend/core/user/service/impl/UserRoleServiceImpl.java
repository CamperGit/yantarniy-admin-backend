package ru.ds.yantarniy.admin.backend.core.user.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.ds.yantarniy.admin.backend.core.user.service.UserRoleService;
import ru.ds.yantarniy.admin.backend.dao.entity.user.UserRoleEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.user.UserRoleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserRoleServiceImpl implements UserRoleService {

    UserRoleRepository userRoleRepository;

    @Override
    public UserRoleEntity save(UserRoleEntity userRoleEntity){
        return userRoleRepository.save(userRoleEntity);
    }

    @Override
    public UserRoleEntity getById(Long id){
        return userRoleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Not found role entity with id = %d", id)
                )
        );
    }

    @Override
    public List<UserRoleEntity> findAll(){
        return userRoleRepository.findAll();
    }

    @Override
    public List<UserRoleEntity> findAllRolesByAuthorities(List<String> authorities) {
        return userRoleRepository.findAllByTitleIn(authorities);
    }

    @Override
    public void deleteById(Long id){
        userRoleRepository.deleteById(id);
    }
}
