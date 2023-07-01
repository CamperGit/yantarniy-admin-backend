package ru.ds.yantarniy.admin.backend.rest.customer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ds.yantarniy.admin.backend.common.orika.DefaultMapper;
import ru.ds.yantarniy.admin.backend.common.orika.OrikaMapper;
import ru.ds.yantarniy.admin.backend.core.customer.service.CustomerService;
import ru.ds.yantarniy.admin.backend.dao.entity.customer.CustomerEntity;

import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/v1/customers")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Api(tags = {"Контроллер для работы с сотрудниками"})
public class CustomerController {

    CustomerService customerService;

    @DefaultMapper
    OrikaMapper mapper;


    @GetMapping("/{id}")
    @ApiOperation("Получение данных о пользователе ботом по его ID")
    public ResponseEntity<CustomerDto> findById(
            @ApiParam(value = "ID пользователя ботом в системе", required = true)
            @PathVariable
            Long id
    ) {
        CustomerEntity customer = customerService.findById(id);
        return ResponseEntity.ok(mapper.map(customer, CustomerDto.class));
    }

    @GetMapping
    @ApiOperation("Получение данных о пользователях ботом")
    public ResponseEntity<List<CustomerDto>> findAll() {
        return ResponseEntity.ok(mapper.mapAsList(customerService.findAll(), CustomerDto.class));
    }
}