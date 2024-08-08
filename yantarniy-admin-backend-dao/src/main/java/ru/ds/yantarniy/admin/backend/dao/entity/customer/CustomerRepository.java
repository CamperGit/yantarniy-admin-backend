package ru.ds.yantarniy.admin.backend.dao.entity.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByChatId(String chatId);

    List<CustomerEntity> findAllByChatIdIn(List<String> chatIds);

    @Query(value = "SELECT c.chat_id FROM customer c", nativeQuery = true)
    List<String> findAllChatId();

    @Query(value = "SELECT c.chat_id FROM customer c WHERE c.role = 'ADMIN'", nativeQuery = true)
    List<String> findAllAdminChatId();
}
