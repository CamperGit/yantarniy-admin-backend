package ru.ds.yantarniy.admin.backend.core.customer.service;

import ru.ds.yantarniy.admin.backend.dao.entity.customer.CustomerEntity;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    CustomerEntity save(CustomerEntity entity);

    Optional<CustomerEntity> findByChatId(String chatId);

    CustomerEntity findById(Long id);

    List<CustomerEntity> findAll();
}
