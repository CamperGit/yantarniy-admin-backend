package ru.ds.yantarniy.admin.backend.common.orika;

import ma.glasnost.orika.MapperFactory;

public interface OrikaMapperConfigurer {
    void configure(MapperFactory factory);
}