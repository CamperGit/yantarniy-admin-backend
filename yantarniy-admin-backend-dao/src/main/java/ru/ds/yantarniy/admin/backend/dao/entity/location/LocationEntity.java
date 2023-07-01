package ru.ds.yantarniy.admin.backend.dao.entity.location;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "location")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "location_id_seq", sequenceName = "location_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_id_seq")
    Long id;

    @Column(name = "type")
    String type;

    @Column(name = "title")
    String title;
}
