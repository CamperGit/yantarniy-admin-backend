package ru.ds.yantarniy.admin.backend.dao.entity.schedule;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schedule_type")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleTypeEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "schedule_type_id_seq", sequenceName = "schedule_type_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_type_id_seq")
    Long id;

    @Column(name = "type")
    String type;

    @Column(name = "title")
    String title;
}
