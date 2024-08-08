package ru.ds.yantarniy.admin.backend.rest.schedule;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchSchedulesRequestParam {

    Long typeId;

    String descriptionLike;

    int pageNumber;

    int pageSize;

    Sort.Direction sortDirection;

    String sortProperty;
}
