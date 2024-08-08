package ru.ds.yantarniy.admin.backend.rest.telegram;

import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.ds.yantarniy.admin.backend.rest.customer.CustomerDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel("Модель ответа на телеграмм рассылку")
public class TelegramSendResponse {

    List<CustomerDto> correctSendCustomers;

    List<CustomerDto> errorSendCustomers;
}
