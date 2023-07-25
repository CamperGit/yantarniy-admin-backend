package ru.ds.yantarniy.admin.backend.telegram.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScrollResponse<T> {

    boolean first;

    boolean last;

    long numberOfItems;

    long currentPosition;

    T value;
}
