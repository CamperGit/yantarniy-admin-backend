package ru.ds.yantarniy.admin.backend.core.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.ds.yantarniy.admin.backend.common.config.CommonConfiguration;
import ru.ds.yantarniy.admin.backend.dao.DaoConfiguration;
import ru.ds.yantarniy.admin.backend.minio.config.MinioConfiguration;

@Configuration
@Import({
        CommonConfiguration.class,
        DaoConfiguration.class,
        MinioConfiguration.class,
})
@EnableScheduling
@EnableConfigurationProperties
@ComponentScan(basePackages = { "ru.ds.yantarniy.admin.backend.core" })
public class CoreConfiguration {
}
