package ru.ds.yantarniy.admin.backend.dao;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@ComponentScan(basePackages = { "ru.ds.yantarniy.admin.backend.dao" })
@EnableJpaRepositories(basePackages = {
        "ru.ds.yantarniy.admin.backend.dao"
})
@EntityScan(basePackages = {
        "ru.ds.yantarniy.admin.backend.dao",
        "org.springframework.data.jpa.convert.threeten"
})
public class DaoConfiguration {}