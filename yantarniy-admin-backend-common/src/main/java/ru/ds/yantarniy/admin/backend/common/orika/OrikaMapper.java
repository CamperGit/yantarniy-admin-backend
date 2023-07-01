package ru.ds.yantarniy.admin.backend.common.orika;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrikaMapper extends ConfigurableMapper {
    List<BidirectionalConverter<?, ?>> converters;

    List<OrikaMapperConfigurer> configurers;

    public OrikaMapper(@Nullable List<BidirectionalConverter<?, ?>> converters, @Nullable List<OrikaMapperConfigurer> configurers) {
        super(false);
        this.converters = converters;
        this.configurers = configurers;
    }

    public OrikaMapper() {
        super(false);
    }

    @PostConstruct
    public void postConstruct() {
        init();
    }

    @Override
    protected void configure(MapperFactory factory) {
        Optional.ofNullable(converters).ifPresent((list) -> list.forEach(factory.getConverterFactory()::registerConverter));
        Optional.ofNullable(configurers).ifPresent((list) -> list.forEach((c) -> c.configure(factory)));
    }
}