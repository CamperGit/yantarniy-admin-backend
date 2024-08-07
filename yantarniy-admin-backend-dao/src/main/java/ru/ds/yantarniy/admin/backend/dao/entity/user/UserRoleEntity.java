package ru.ds.yantarniy.admin.backend.dao.entity.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_role")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRoleEntity {

    @Id
    @SequenceGenerator(name = "user_role_id_seq", sequenceName = "user_role_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_id_seq")
    @Column(name = "id")
    Long id;

    @Column(name = "title")
    String title;
}
