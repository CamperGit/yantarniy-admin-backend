package ru.ds.yantarniy.admin.backend.telegram.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.ds.yantarniy.admin.backend.dao.entity.customer.CustomerEntity;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendingReport {

    List<CustomerEntity> correctSendCustomers;

    List<CustomerEntity> errorSendCustomers;
}
