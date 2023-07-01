package ru.ds.yantarniy.admin.backend.dao.entity.schedule;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.ds.yantarniy.admin.backend.dao.entity.file.FileEntity;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schedule")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "schedule_id_seq", sequenceName = "schedule_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_id_seq")
    Long id;

    @Column(name = "description")
    String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    FileEntity file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_type_id")
    ScheduleTypeEntity type;
}
