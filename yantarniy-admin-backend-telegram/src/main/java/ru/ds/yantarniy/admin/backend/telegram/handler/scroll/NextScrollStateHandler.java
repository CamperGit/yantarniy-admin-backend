package ru.ds.yantarniy.admin.backend.telegram.handler.scroll;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.ds.yantarniy.admin.backend.core.search.SpecificationsSearchService;
import ru.ds.yantarniy.admin.backend.dao.specification.Specifications;
import ru.ds.yantarniy.admin.backend.telegram.model.ScrollResponse;
import ru.ds.yantarniy.admin.backend.telegram.model.ScrollState;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NextScrollStateHandler<T> implements ScrollStateHandler<T> {

    static String ID = "id";

    static int PAGE_NUMBER = 0;

    static int PAGE_SIZE = 2;

    static String ID_REGEX = "\\[\\d+\\]";

    @Override
    public ScrollResponse<T> getEntityFromData(String data, List<Specification<T>> additionalFilters, SpecificationsSearchService<T> service) {
        List<Specification<T>> specifications = new ArrayList<>(additionalFilters);

        Long currentId = getIdFromData(data);
        specifications.add(Specifications.greaterThanEqualToOrNull(ID, currentId));

        Page<T> content = service.findAll(Specifications.And
                        .<T>builder()
                        .specifications(specifications)
                        .build(), PageRequest.of(PAGE_NUMBER, PAGE_SIZE, Sort.Direction.ASC, ID));

        return ScrollResponse.<T>builder()
                .first(content.isFirst())
                .last(content.isLast())
                .numberOfItems(content.getTotalElements())
                .currentPosition(getPositionFromData(data))
                .value(CollectionUtils.lastElement(content.getContent()))
                .build();
    }

    @Override
    public boolean isApplicable(ScrollState state) {
        return state.equals(ScrollState.NEXT);
    }
}
