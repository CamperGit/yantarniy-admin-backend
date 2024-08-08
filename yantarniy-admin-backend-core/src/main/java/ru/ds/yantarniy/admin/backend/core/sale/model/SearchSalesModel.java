package ru.ds.yantarniy.admin.backend.core.sale.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchSalesModel {

    Long locationId;

    String descriptionLike;

    int pageNumber;

    int pageSize;

    Sort.Direction sortDirection;

    String sortProperty;
}
