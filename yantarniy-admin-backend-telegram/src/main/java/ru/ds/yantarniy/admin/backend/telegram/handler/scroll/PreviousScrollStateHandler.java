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
public class PreviousScrollStateHandler<T> implements ScrollStateHandler<T> {

    static int LAST_ELEMENT_SIZE = 1;

    static String ID = "id";

    static int PAGE_NUMBER = 0;

    static int PAGE_SIZE = 2;

    @Override
    public ScrollResponse<T> getEntityFromData(String data, List<Specification<T>> additionalFilters, SpecificationsSearchService<T> service) {
        List<Specification<T>> specifications = new ArrayList<>(additionalFilters);

        Long currentId = getIdFromData(data);
        specifications.add(Specifications.lessThanEqualToOrNull(ID, currentId));

        Page<T> content = service.findAll(Specifications.And
                .<T>builder()
                .specifications(specifications)
                .build(), PageRequest.of(PAGE_NUMBER, PAGE_SIZE, Sort.Direction.DESC, ID));

        long numberOfElements = service.countItemsByFilter(Specifications.And
                .<T>builder()
                .specifications(additionalFilters)
                .build());

        return ScrollResponse.<T>builder()
                .first(content.isFirst() && content.getTotalElements() == LAST_ELEMENT_SIZE)
                .last(content.isLast() && content.getTotalElements() == LAST_ELEMENT_SIZE)
                .numberOfItems(numberOfElements)
                .currentPosition(getPositionFromData(data) - SCROLL_STATE_COUNTER_INCREMENT)
                .value(CollectionUtils.lastElement(content.getContent()))
                .build();
    }

    @Override
    public boolean isApplicable(ScrollState state) {
        return state.equals(ScrollState.PREVIOUS);
    }
}
