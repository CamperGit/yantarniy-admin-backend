package ru.ds.yantarniy.admin.backend.core.price.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchPricesModel {

    Long locationId;

    int pageNumber;

    int pageSize;

    Sort.Direction sortDirection;

    String sortProperty;
}
