package ru.ds.yantarniy.admin.backend.dao.entity.sale;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.ds.yantarniy.admin.backend.dao.entity.file.FileEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.location.LocationEntity;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sale")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaleEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "sale_id_seq", sequenceName = "sale_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sale_id_seq")
    Long id;

    @Column(name = "description")
    String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    FileEntity file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    LocationEntity location;
}
