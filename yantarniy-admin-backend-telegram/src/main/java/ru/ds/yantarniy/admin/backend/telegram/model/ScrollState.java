package ru.ds.yantarniy.admin.backend.telegram.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.util.StringUtils;
import ru.ds.yantarniy.admin.backend.telegram.exception.YantarniyBotTelegramException;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ScrollState {

    INIT("init"), PREVIOUS("previous"), NEXT("next");

    String value;

    public static ScrollState fromValue(String value) {
        if (StringUtils.isEmpty(value)) {
            throw new IllegalStateException("Scroll state value is empty");
        }
        for (ScrollState state : ScrollState.values()) {
            if (state.value.equals(value)) {
                return state;
            }
        }
        throw new IllegalStateException(String.format("Not found scroll state for value = %s", value));
    }

    public static String getScrollStateRegex() {
        return Arrays.stream(ScrollState.values()).map(ScrollState::getValue).collect(Collectors.joining("|"));
    }

    public static ScrollState getScrollStateFromStringWithRegex(String string) {
        String scrollStateRegex = ScrollState.getScrollStateRegex();
        Pattern scrollStatePattern = Pattern.compile(scrollStateRegex);
        Matcher scrollStateMatcher = scrollStatePattern.matcher(string);

        if (!scrollStateMatcher.find()) {
            throw new IllegalStateException(
                    String.format("Not found scroll state for string: %s \n and regex: %s", string, scrollStateRegex)
            );
        }
        return ScrollState.fromValue(scrollStateMatcher.group());
    }
}
