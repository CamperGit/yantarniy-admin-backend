package ru.ds.yantarniy.admin.backend.security.property;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Data
@Validated
@Configuration
@ConfigurationProperties("cross.origin")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CrossOriginProperties {

    @NotEmpty(message = "Cross origin url property not found")
    String url;
}
