package ru.ds.yantarniy.admin.backend.dao.entity.customer;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "customer_id_seq", sequenceName = "customer_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_seq")
    Long id;

    @Column(name = "chat_id")
    String chatId;

    @Column(name = "first_name")
    String firstname;

    @Column(name = "last_name")
    String lastname;

    @Column(name = "username")
    String username;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    LocalDateTime createdDate;

    @Column(name = "last_entry")
    LocalDateTime lastEntry;
}
