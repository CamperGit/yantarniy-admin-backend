package ru.ds.yantarniy.admin.backend.dao.utils;

import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.util.Strings;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@UtilityClass
public class TypeUtils {
    /**
     * Parses from input string value
     * and returns value appropriate for input clazz parameters
     * or returns value if value instance of input clazz parameters
     * <p>
     * Supports class types for valueOf:
     * - {@link LocalDateTime}
     * - {@link LocalDate}
     * - {@link Integer}
     * - {@link Long}
     * - {@link BigDecimal}
     *
     * @param value - parsable value
     * @param clazz - appropriate class
     * @param <T>
     * @return
     */
    public <T> T parseValue(Object value, Class<T> clazz) {
        if (value == null) {
            return null;
        }

        if (clazz.isInstance(value)) {
            return clazz.cast(value);
        }

        if (!(value instanceof String)) {
            throw new IllegalArgumentException("Input parameter 'value' must have type of 'clazz' or String");
        }

        if (clazz == LocalDateTime.class) {
            return clazz.cast(LocalDateTime.parse(value.toString(), DateTimeFormatter.ISO_DATE_TIME));
        }

        if (clazz == LocalDate.class) {
            return clazz.cast(LocalDate.parse(value.toString(), DateTimeFormatter.ISO_DATE));
        }

        if (clazz == Long.class) {
            return clazz.cast(Long.valueOf(value.toString()));
        }

        if (clazz == Integer.class) {
            return clazz.cast(Integer.valueOf(value.toString()));
        }

        if (clazz == BigDecimal.class) {
            return clazz.cast(new BigDecimal(value.toString()));
        }

        throw new IllegalArgumentException("Couldn't determinate type for valueOf execution");
    }

    public LocalDateTime parseDateTime(String dateTime) {
        if (Strings.isBlank(dateTime)) {
            return null;
        }

        try {
            return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        } catch (DateTimeParseException e) {
            return LocalDate.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
        }
    }
}