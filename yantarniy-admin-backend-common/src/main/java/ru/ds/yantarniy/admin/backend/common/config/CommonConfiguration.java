package ru.ds.yantarniy.admin.backend.common.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.ds.yantarniy.admin.backend.common.interceptor.HttpLoggingInterceptor;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;

@Configuration
public class CommonConfiguration {
    public static final String DEFAULT_OBJECT_MAPPER_BEAN = "objectMapperBean";
    public static final String DEFAULT_JACKSON_2_HTTP_CONVERTER = "jackson2HttpConverter";
    public static final String DEFAULT_HTTP_LOGGING_INTERCEPTOR_BEAN = "httpLoggingInterceptorBean";
    public static final String HTTP = "http://";
    public static final String HTTPS = "https://";

    @Bean(name = DEFAULT_OBJECT_MAPPER_BEAN)
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX"));
        return mapper;
    }

    @Bean(name = DEFAULT_JACKSON_2_HTTP_CONVERTER)
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }

    @Bean(name = DEFAULT_HTTP_LOGGING_INTERCEPTOR_BEAN)
    public ClientHttpRequestInterceptor httpLoggingInterceptor() {
        return new HttpLoggingInterceptor();
    }

    public static RestTemplate restTemplateForService(
            String serviceUrl,
            Collection<ClientHttpRequestInterceptor> interceptors,
            Collection<? extends HttpMessageConverter<?>> messageConverters
    ) {
        String rootUri = (serviceUrl.startsWith(HTTP) || serviceUrl.startsWith(HTTPS))
                ? serviceUrl : HTTP + serviceUrl;

        return new RestTemplateBuilder()
                .uriTemplateHandler(new DefaultUriBuilderFactory(rootUri))
                .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .interceptors(interceptors != null ? interceptors : Collections.emptyList())
                .messageConverters(messageConverters != null ? messageConverters : Collections.emptyList())
                .build();
    }
}