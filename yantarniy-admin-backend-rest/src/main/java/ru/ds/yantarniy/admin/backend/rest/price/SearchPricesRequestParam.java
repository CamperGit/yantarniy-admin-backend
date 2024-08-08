package ru.ds.yantarniy.admin.backend.rest.price;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchPricesRequestParam {

    Long locationId;

    int pageNumber;

    int pageSize;

    Sort.Direction sortDirection;

    String sortProperty;
}
