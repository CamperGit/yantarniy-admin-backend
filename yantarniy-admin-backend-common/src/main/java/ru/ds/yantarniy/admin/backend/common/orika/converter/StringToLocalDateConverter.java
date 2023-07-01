package ru.ds.yantarniy.admin.backend.common.orika.converter;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class StringToLocalDateConverter extends BidirectionalConverter<String, LocalDate> {
    @Override
    public LocalDate convertTo(String source, Type<LocalDate> destinationType, MappingContext mappingContext) {
        return LocalDate.parse(source);
    }

    @Override
    public String convertFrom(LocalDate source, Type<String> destinationType, MappingContext mappingContext) {
        return source.toString();
    }
}