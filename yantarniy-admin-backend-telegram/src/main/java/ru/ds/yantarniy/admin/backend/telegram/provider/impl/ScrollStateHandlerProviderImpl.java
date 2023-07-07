package ru.ds.yantarniy.admin.backend.telegram.provider.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.ds.yantarniy.admin.backend.telegram.handler.scroll.InitScrollStateHandler;
import ru.ds.yantarniy.admin.backend.telegram.handler.scroll.ScrollStateHandler;
import ru.ds.yantarniy.admin.backend.telegram.model.ScrollState;
import ru.ds.yantarniy.admin.backend.telegram.provider.ScrollStateHandlerProvider;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScrollStateHandlerProviderImpl<T> implements ScrollStateHandlerProvider<T> {

    InitScrollStateHandler<T> initScrollStateHandler;

    List<ScrollStateHandler<T>> scrollStateHandlerList;

    @Override
    public ScrollStateHandler<T> getScrollHandler(ScrollState state) {
        if (state == null) {
            return initScrollStateHandler;
        }
        return scrollStateHandlerList.stream()
                .filter((handler) -> handler.isApplicable(state))
                .findFirst()
                .orElse(initScrollStateHandler);
    }
}
