package ru.ds.yantarniy.admin.backend.rest.mapper;

import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;
import ru.ds.yantarniy.admin.backend.common.orika.OrikaMapperConfigurer;
import ru.ds.yantarniy.admin.backend.core.employee.model.SearchEmployeesModel;
import ru.ds.yantarniy.admin.backend.core.price.model.SearchPricesModel;
import ru.ds.yantarniy.admin.backend.core.sale.model.SearchSalesModel;
import ru.ds.yantarniy.admin.backend.core.schedule.model.SearchSchedulesModel;
import ru.ds.yantarniy.admin.backend.dao.entity.customer.CustomerEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.employee.EmployeeEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.employee.EmployeeTypeEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.file.FileEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.location.LocationEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.price.PriceEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.sale.SaleEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.schedule.ScheduleEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.schedule.ScheduleTypeEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.user.UserEntity;
import ru.ds.yantarniy.admin.backend.rest.customer.CustomerDto;
import ru.ds.yantarniy.admin.backend.rest.employee.EmployeeDto;
import ru.ds.yantarniy.admin.backend.rest.employee.EmployeeTypeDto;
import ru.ds.yantarniy.admin.backend.rest.employee.SearchEmployeesRequestParam;
import ru.ds.yantarniy.admin.backend.rest.file.FileDto;
import ru.ds.yantarniy.admin.backend.rest.location.LocationDto;
import ru.ds.yantarniy.admin.backend.rest.price.PriceDto;
import ru.ds.yantarniy.admin.backend.rest.price.SearchPricesRequestParam;
import ru.ds.yantarniy.admin.backend.rest.sale.SaleDto;
import ru.ds.yantarniy.admin.backend.rest.sale.SearchSalesRequestParam;
import ru.ds.yantarniy.admin.backend.rest.schedule.ScheduleDto;
import ru.ds.yantarniy.admin.backend.rest.schedule.ScheduleTypeDto;
import ru.ds.yantarniy.admin.backend.rest.schedule.SearchSchedulesRequestParam;
import ru.ds.yantarniy.admin.backend.rest.telegram.TelegramSendResponse;
import ru.ds.yantarniy.admin.backend.rest.user.UserDto;
import ru.ds.yantarniy.admin.backend.telegram.model.SendingReport;

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

        factory.classMap(UserDto.class, UserEntity.class)
                .byDefault()
                .register();

        factory.classMap(SearchEmployeesRequestParam.class, SearchEmployeesModel.class)
                .byDefault()
                .register();

        factory.classMap(SearchPricesRequestParam.class, SearchPricesModel.class)
                .byDefault()
                .register();

        factory.classMap(SearchSalesRequestParam.class, SearchSalesModel.class)
                .byDefault()
                .register();

        factory.classMap(SearchSchedulesRequestParam.class, SearchSchedulesModel.class)
                .byDefault()
                .register();

        factory.classMap(TelegramSendResponse.class, SendingReport.class)
                .byDefault()
                .register();
    }
}
