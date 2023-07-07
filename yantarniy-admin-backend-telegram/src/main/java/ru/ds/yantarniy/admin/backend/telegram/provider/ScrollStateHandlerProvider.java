package ru.ds.yantarniy.admin.backend.telegram.provider;

import ru.ds.yantarniy.admin.backend.telegram.handler.scroll.ScrollStateHandler;
import ru.ds.yantarniy.admin.backend.telegram.model.ScrollState;

public interface ScrollStateHandlerProvider<T> {

    ScrollStateHandler<T> getScrollHandler(ScrollState state);
}
