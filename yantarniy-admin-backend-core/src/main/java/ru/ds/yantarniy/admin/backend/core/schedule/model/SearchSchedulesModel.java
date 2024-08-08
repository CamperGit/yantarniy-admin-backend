package ru.ds.yantarniy.admin.backend.core.schedule.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchSchedulesModel {

    Long typeId;

    String descriptionLike;

    int pageNumber;

    int pageSize;

    Sort.Direction sortDirection;

    String sortProperty;
}
