package ru.ds.yantarniy.admin.backend.rest.mapper;

import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;
import ru.ds.yantarniy.admin.backend.common.orika.OrikaMapperConfigurer;
import ru.ds.yantarniy.admin.backend.dao.entity.customer.CustomerEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.employee.EmployeeEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.employee.EmployeeTypeEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.file.FileEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.location.LocationEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.price.PriceEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.sale.SaleEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.schedule.ScheduleEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.schedule.ScheduleTypeEntity;
import ru.ds.yantarniy.admin.backend.rest.customer.CustomerDto;
import ru.ds.yantarniy.admin.backend.rest.employee.EmployeeDto;
import ru.ds.yantarniy.admin.backend.rest.employee.EmployeeTypeDto;
import ru.ds.yantarniy.admin.backend.rest.file.FileDto;
import ru.ds.yantarniy.admin.backend.rest.location.LocationDto;
import ru.ds.yantarniy.admin.backend.rest.price.PriceDto;
import ru.ds.yantarniy.admin.backend.rest.sale.SaleDto;
import ru.ds.yantarniy.admin.backend.rest.schedule.ScheduleDto;
import ru.ds.yantarniy.admin.backend.rest.schedule.ScheduleTypeDto;

@Component
public class RestMapperConfigurer implements OrikaMapperConfigurer {
    @Override
    public void configure(MapperFactory factory) {
        factory.classMap(EmployeeDto.class, EmployeeEntity.class)
                .byDefault()
                .register();

        factory.classMap(EmployeeTypeDto.class, EmployeeTypeEntity.class)
                .byDefault()
                .register();

        factory.classMap(CustomerDto.class, CustomerEntity.class)
                .byDefault()
                .register();

        factory.classMap(FileDto.class, FileEntity.class)
                .byDefault()
                .register();

        factory.classMap(LocationDto.class, LocationEntity.class)
                .byDefault()
                .register();

        factory.classMap(PriceDto.class, PriceEntity.class)
                .byDefault()
                .register();

        factory.classMap(SaleDto.class, SaleEntity.class)
                .byDefault()
                .register();

        factory.classMap(ScheduleDto.class, ScheduleEntity.class)
                .byDefault()
                .register();

        factory.classMap(ScheduleTypeDto.class, ScheduleTypeEntity.class)
                .byDefault()
                .register();
    }
}