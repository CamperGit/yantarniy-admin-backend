package ru.ds.yantarniy.admin.backend.telegram.handler.scroll;

import org.springframework.data.jpa.domain.Specification;
import ru.ds.yantarniy.admin.backend.core.search.SpecificationsSearchService;
import ru.ds.yantarniy.admin.backend.telegram.model.ScrollResponse;
import ru.ds.yantarniy.admin.backend.telegram.model.ScrollState;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface ScrollStateHandler<T> {

    String SCROLL_STATE_ID_REGEX = "\\[\\d+\\]";

    String SCROLL_STATE_CURRENT_POSITION_REGEX = "\\{\\d+\\}";

    int SCROLL_STATE_COUNTER_INCREMENT = 1;

    /**
     * Обрабатывает состояние скролла фотографии для получения сущности из базы данных. Ищет в БД сущность типа T.
     * При необходимости добавляет к поиску доп.фильтры additionalFilters
     *
     * @param data              строка, которая содержит в себе дополнительную информацию
     * @param additionalFilters дополнительные фильтры
     * @param service           сервис поиска по спецификациям переданной сущности
     * @return сущность, если была найдена после обработки состояния скролла
     */
    ScrollResponse<T> getEntityFromData(String data, List<Specification<T>> additionalFilters, SpecificationsSearchService<T> service);

    boolean isApplicable(ScrollState state);

    default Long getIdFromData(String data) {
        Pattern pattern = Pattern.compile(SCROLL_STATE_ID_REGEX);
        Matcher matcher = pattern.matcher(data);
        if (!matcher.find()) {
            throw new IllegalStateException(String.format("Not found id for data string %s", data));
        }
        String idString = matcher.group().replaceAll("[\\]\\[]", "");
        return Long.valueOf(idString);
    }

    default Long getPositionFromData(String data) {
        Pattern pattern = Pattern.compile(SCROLL_STATE_CURRENT_POSITION_REGEX);
        Matcher matcher = pattern.matcher(data);
        if (!matcher.find()) {
            throw new IllegalStateException(String.format("Not found current position for data string %s", data));
        }
        String positionString = matcher.group().replaceAll("[\\{\\}]", "");
        return Long.valueOf(positionString);
    }

    default boolean isCanScroll(ScrollResponse<T> response, ScrollState state) {
        if (state == ScrollState.PREVIOUS && response.isFirst()) {
            return false;
        }
        if (state == ScrollState.NEXT && response.isLast()) {
            return false;
        }
        return true;
    }
}
