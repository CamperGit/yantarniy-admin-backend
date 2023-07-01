package ru.ds.yantarniy.admin.backend.dao.entity.file;

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
@Table(name = "file")
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "file_id_seq", sequenceName = "file_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_id_seq")
    Long id;

    @Column(name = "storage_url")
    String storageUrl;

    @Column(name = "minio_object_id")
    String minioObjectId;

    @Column(name = "name")
    String name;

    @Column(name = "name")
    Long size;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    FileStatus status;
}
