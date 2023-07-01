package ru.ds.yantarniy.admin.backend.minio.property;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Validated
@Configuration
@ConfigurationProperties("minio")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MinioProperties {

    @NotBlank(message = "Minio url property not found")
    String url;

    @NotBlank(message = "Minio access key property not found")
    String accessKey;

    @NotBlank(message = "Minio secret key property not found")
    String secretKey;

    @NotBlank(message = "Minio bucket name property not found")
    String bucketName;

    @NotNull(message = "Minio secure property not found")
    Boolean secure;

    @NotNull(message = "Minio port property not found")
    Integer port;

    @NotNull(message = "Minio object size property not found")
    Integer objectSize;

    @NotNull(message = "Part size property not found")
    Integer partSize;
}
