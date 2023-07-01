package ru.ds.yantarniy.admin.backend.dao.entity.employee;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee_type")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeTypeEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "employee_type_id_seq", sequenceName = "employee_type_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_type_id_seq")
    Long id;

    @Column(name = "type")
    String type;

    @Column(name = "title")
    String title;
}
