package ru.ds.yantarniy.admin.backend.dao.entity.price;

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
@Table(name = "price")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PriceEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "price_id_seq", sequenceName = "price_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "price_id_seq")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    FileEntity file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    LocationEntity location;
}
