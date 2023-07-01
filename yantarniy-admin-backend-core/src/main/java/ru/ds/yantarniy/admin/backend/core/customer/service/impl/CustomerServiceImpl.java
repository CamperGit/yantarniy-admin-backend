package ru.ds.yantarniy.admin.backend.core.customer.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.ds.yantarniy.admin.backend.core.customer.service.CustomerService;
import ru.ds.yantarniy.admin.backend.dao.entity.customer.CustomerEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.customer.CustomerRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerServiceImpl implements CustomerService {

    CustomerRepository customerRepository;

    @Override
    public CustomerEntity save(CustomerEntity entity) {
        return customerRepository.save(entity);
    }

    @Override
    public CustomerEntity findByChatId(String chatId) {
        return customerRepository.findByChatId(chatId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found CustomerEntity with chat id = %s", chatId)));
    }

    @Override
    public CustomerEntity findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found CustomerEntity with id = %d", id)));
    }

    @Override
    public List<CustomerEntity> findAll() {
        return customerRepository.findAll();
    }
}
