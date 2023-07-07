package ru.ds.yantarniy.admin.backend.minio.config;

import io.minio.MinioClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.ds.yantarniy.admin.backend.minio.property.MinioProperties;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MinioConfiguration {

    MinioProperties minioProperties;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .endpoint(minioProperties.getUrl(), minioProperties.getPort(), minioProperties.getSecure())
                .build();
    }
}
