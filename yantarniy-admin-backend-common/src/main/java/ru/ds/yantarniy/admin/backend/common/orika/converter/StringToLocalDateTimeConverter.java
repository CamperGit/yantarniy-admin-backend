package ru.ds.yantarniy.admin.backend.common.orika.converter;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class StringToLocalDateTimeConverter extends BidirectionalConverter<String, LocalDateTime> {
    @Override
    public LocalDateTime convertTo(String source, Type<LocalDateTime> destinationType, MappingContext mappingContext) {
        return LocalDateTime.parse(source);
    }

    @Override
    public String convertFrom(LocalDateTime source, Type<String> destinationType, MappingContext mappingContext) {
        return source.toString();
    }
}