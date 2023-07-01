package ru.ds.yantarniy.admin.backend.dao.specification;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import ru.ds.yantarniy.admin.backend.dao.utils.TypeUtils;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Contains common jpa specification implementations
 */
public final class Specifications {
    private interface FromSpecification {
        Predicate toFromPredicate(From<?, ?> from, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder);

        /**
         * Returns responsible column name from columns chain
         * <p>
         * Example:
         * - input parameters: myEntry.name
         * - returns: name
         *
         * @param column - column chain, separated by '.'
         * @return
         */
        default String column(String column) {
            List<String> joins = Arrays.asList(column.split("\\."));
            return joins.get(joins.size() - 1);
        }

        /**
         * Returns responsible join for specification
         *
         * @param root
         * @param column - responsible column name
         * @return
         */
        default From<?, ?> from(Root root, String column) {
            List<String> joins = Arrays.asList(column.split("\\."));

            if (joins.size() <= 1) {
                return root;
            }

            Join _root = root.join(joins.get(0), JoinType.LEFT);
            for (String value : joins.subList(1, joins.size() - 1)) {
                _root = _root.join(value, JoinType.LEFT);
            }
            return _root;
        }

    }

    private Specifications() {
        throw new IllegalStateException(
                "Forbidden to create an instance of ru.ds.rsb.escm.content.dao.specification.Specifications class"
        );
    }

    /**
     * Common 'equal' specification implementation
     */
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Equal<T> implements Specification<T>, FromSpecification {
        String column;
        Object value;

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            return toFromPredicate(from(root, column), query, criteriaBuilder);
        }

        @Override
        public Predicate toFromPredicate(From<?, ?> from, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            query.distinct(true);
            Expression exp = from.get(column(column));

            return criteriaBuilder.equal(
                    exp.getJavaType().isEnum() ? exp.as(String.class) : exp,
                    exp.getJavaType().isEnum() ? value.toString() : value
            );
        }
    }

    /**
     * Common 'is null' specification implementation
     */
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class IsNull<T> implements Specification<T>, FromSpecification {
        String column;

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            return toFromPredicate(from(root, column), query, criteriaBuilder);
        }

        @Override
        public Predicate toFromPredicate(From<?, ?> from, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            query.distinct(true);
            return criteriaBuilder.isNull(from.get(column(column)));
        }
    }

    /**
     * Common 'is bot null' specification implementation
     */
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class IsNotNull<T> implements Specification<T>, FromSpecification {
        String column;

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            return toFromPredicate(from(root, column), query, criteriaBuilder);
        }

        @Override
        public Predicate toFromPredicate(From<?, ?> from, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            query.distinct(true);
            return criteriaBuilder.isNotNull(from.get(column(column)));
        }
    }

    /**
     * Common 'is true' specification implementation
     */
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class IsTrue<T> implements Specification<T>, FromSpecification {
        String column;

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            return toFromPredicate(from(root, column), query, criteriaBuilder);
        }

        @Override
        public Predicate toFromPredicate(From<?, ?> from, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            query.distinct(true);
            return criteriaBuilder.isTrue(from.get(column(column)));
        }
    }

    /**
     * Common 'is false' specification implementation
     */
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class IsFalse<T> implements Specification<T>, FromSpecification {
        String column;

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            return toFromPredicate(from(root, column), query, criteriaBuilder);
        }

        @Override
        public Predicate toFromPredicate(From<?, ?> from, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            query.distinct(true);
            return criteriaBuilder.isFalse(from.get(column(column)));
        }
    }

    /**
     * Common 'like' specification implementation
     */
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Like<T> implements Specification<T>, FromSpecification {
        String column;
        Object value;

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            return toFromPredicate(from(root, column), query, criteriaBuilder);
        }

        @Override
        public Predicate toFromPredicate(From<?, ?> from, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            query.distinct(true);
            Expression<String> likeExpression = from.get(column(column));
            return criteriaBuilder.like(
                    criteriaBuilder.upper(likeExpression),
                    "%" + ((String) value).toUpperCase() + "%"
            );
        }
    }

    /**
     * Common 'like' specification implementation
     */
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class LikeIn<T> implements Specification<T>, FromSpecification {
        String column;
        List<String> value;

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            return toFromPredicate(from(root, column), query, criteriaBuilder);
        }

        @Override
        public Predicate toFromPredicate(From<?, ?> from, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            query.distinct(true);
            Expression<String> likeExpression = from.get(column(column));
            return criteriaBuilder.or(value.stream()
                    .map((str) -> criteriaBuilder.like(
                            criteriaBuilder.upper(likeExpression),
                            "%" + str.toUpperCase() + "%"
                    )).toArray(Predicate[]::new));
        }
    }

    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class And<T> implements Specification<T> {
        List<Specification<T>> specifications;

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            criteriaQuery.distinct(true);

            List<Specification<T>> specs = specifications
                    .stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            Predicate[] predicates = new Predicate[specs.size()];

            specs.stream()
                    .map(s -> s.toPredicate(root, criteriaQuery, criteriaBuilder))
                    .collect(Collectors.toList()).toArray(predicates);

            return criteriaBuilder.and(predicates);
        }
    }

    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Or<T> implements Specification<T> {
        List<Specification<T>> specifications;

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            criteriaQuery.distinct(true);

            List<Specification<T>> specs = specifications
                    .stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            Predicate[] predicates = new Predicate[specs.size()];

            specs.stream()
                    .map(s -> s.toPredicate(root, criteriaQuery, criteriaBuilder))
                    .collect(Collectors.toList()).toArray(predicates);

            return criteriaBuilder.or(predicates);
        }
    }

    /**
     * Common 'in' specification implementation
     */
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class In<T> implements Specification<T>, FromSpecification {
        String column;
        List<?> values;

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            return toFromPredicate(from(root, column), query, criteriaBuilder);
        }

        @Override
        public Predicate toFromPredicate(From<?, ?> from, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            query.distinct(true);
            return from.get(column(column)).in(values);
        }
    }

    /**
     * Common 'in' specification implementation
     */
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class NotIn<T> implements Specification<T>, FromSpecification {
        String column;
        Object value;

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            return toFromPredicate(from(root, column), query, criteriaBuilder);
        }

        @Override
        public Predicate toFromPredicate(From<?, ?> from, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            query.distinct(true);
            return criteriaBuilder.not(from.get(column(column)).in(value));
        }
    }

    /**
     * Common 'inList' specification implementation
     */
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class InList<T> implements Specification<T>, FromSpecification {
        String column;
        Object value;

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            return toFromPredicate(from(root, column), query, criteriaBuilder);
        }

        @Override
        public Predicate toFromPredicate(From<?, ?> from, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            query.distinct(true);
            return criteriaBuilder.isMember(value, from.get(column(column)));
        }
    }

    /**
     * Common 'greater than or equal to' specification implementation
     */
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class GreaterThanOrEqualTo<T> implements Specification<T>, FromSpecification {
        String column;
        Object value;

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            return toFromPredicate(from(root, column), query, criteriaBuilder);
        }

        @Override
        public Predicate toFromPredicate(From<?, ?> from, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            query.distinct(true);
            String _column = column(column);

            Expression<?> ltExpression = from.get(_column);
            Class<?> javaType = ltExpression.getJavaType();

            if (javaType == LocalDate.class) {
                return criteriaBuilder.greaterThanOrEqualTo((Expression<LocalDate>) ltExpression, TypeUtils.parseValue(value, LocalDate.class));
            }

            if (javaType == LocalDateTime.class) {
                return criteriaBuilder.greaterThanOrEqualTo((Expression<LocalDateTime>) ltExpression, TypeUtils.parseValue(value, LocalDateTime.class));
            }

            if (javaType == Long.class) {
                return criteriaBuilder.greaterThanOrEqualTo((Expression<Long>) ltExpression, TypeUtils.parseValue(value, Long.class));
            }

            if (javaType == Integer.class) {
                return criteriaBuilder.greaterThanOrEqualTo((Expression<Integer>) ltExpression, TypeUtils.parseValue(value, Integer.class));
            }

            if (javaType == BigDecimal.class) {
                return criteriaBuilder.greaterThanOrEqualTo((Expression<BigDecimal>) ltExpression, TypeUtils.parseValue(value, BigDecimal.class));
            }

            throw new RuntimeException("Couldn't determinate greater then property type");
        }
    }

    /**
     * Common 'less than or equal to' specification implementation
     */
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class LessThanOrEqualTo<T> implements Specification<T>, FromSpecification {
        String column;
        Object value;

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            return toFromPredicate(from(root, column), query, criteriaBuilder);
        }

        @Override
        public Predicate toFromPredicate(From<?, ?> from, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            query.distinct(true);
            String _column = column(column);

            Expression<?> ltExpression = from.get(_column);
            Class<?> javaType = ltExpression.getJavaType();

            switch (javaType.getSimpleName()) {
                case "LocalDate":
                    return criteriaBuilder.lessThanOrEqualTo((Expression<LocalDate>) ltExpression, TypeUtils.parseValue(value, LocalDate.class));
                case "LocalDateTime":
                    return criteriaBuilder.lessThanOrEqualTo((Expression<LocalDateTime>) ltExpression, TypeUtils.parseValue(value, LocalDateTime.class));
                case "Long":
                    return criteriaBuilder.lessThanOrEqualTo((Expression<Long>) ltExpression, TypeUtils.parseValue(value, Long.class));
                case "Integer":
                    return criteriaBuilder.lessThanOrEqualTo((Expression<Integer>) ltExpression, TypeUtils.parseValue(value, Integer.class));
                case "BigDecimal":
                    return criteriaBuilder.lessThanOrEqualTo((Expression<BigDecimal>) ltExpression, TypeUtils.parseValue(value, BigDecimal.class));
                default:
                    throw new RuntimeException("Couldn't determine less than property type");
            }
        }
    }

    /**
     * Common 'between' specification implementation
     */
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Between<T> implements Specification<T>, FromSpecification {
        String column;
        Object valueFrom;
        Object valueTo;

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            return toFromPredicate(from(root, column), query, criteriaBuilder);
        }

        @Override
        public Predicate toFromPredicate(From<?, ?> from, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            query.distinct(true);
            String _column = column(column);
            Class<?> columnType = from.get(_column).getJavaType();

            if (columnType == LocalDateTime.class) {
                return between(criteriaBuilder, from, _column,
                        TypeUtils.parseValue(valueFrom, LocalDateTime.class),
                        TypeUtils.parseValue(valueTo, LocalDateTime.class));
            }

            if (columnType == LocalDate.class) {
                return between(criteriaBuilder, from, _column,
                        TypeUtils.parseValue(valueFrom, LocalDate.class),
                        TypeUtils.parseValue(valueTo, LocalDate.class));
            }

            if (columnType == Long.class) {
                return between(criteriaBuilder, from, _column,
                        TypeUtils.parseValue(valueFrom, Long.class),
                        TypeUtils.parseValue(valueTo, Long.class));
            }

            if (columnType == Integer.class) {
                return between(criteriaBuilder, from, _column,
                        TypeUtils.parseValue(valueFrom, Integer.class),
                        TypeUtils.parseValue(valueTo, Integer.class));
            }

            if (columnType == BigDecimal.class) {
                return between(criteriaBuilder, from, _column,
                        TypeUtils.parseValue(valueFrom, BigDecimal.class),
                        TypeUtils.parseValue(valueTo, BigDecimal.class));
            }

            throw new RuntimeException("Couldn't determinate less then property type");
        }

        private static <V extends Comparable<? super V>> Predicate between(
                CriteriaBuilder criteriaBuilder,
                From<?, ?> from,
                String column,
                V valueFrom,
                V valueTo
        ) {
            Expression<V> expression = from.get(column);
            return between(criteriaBuilder, expression, valueFrom, valueTo);
        }

        private static <V extends Comparable<? super V>> Predicate between(
                CriteriaBuilder criteriaBuilder,
                Expression<V> expression,
                V valueFrom,
                V valueTo
        ) {
            return criteriaBuilder.between(
                    expression,
                    valueFrom,
                    valueTo
            );
        }
    }

    /**
     * Возвращает спецификацию "LessThanOrEqual" или null, если не заполнено значение
     *
     * @param column - наименование поля для сравнения
     * @param value  - значение поля
     * @return
     */
    public static <T> Specification<T> lessThanEqualToOrNull(String column, Object value) {
        if (value == null || (value instanceof String && StringUtils.isEmpty(value))) {
            return null;
        }

        return LessThanOrEqualTo.<T>builder()
                .column(column)
                .value(value)
                .build();
    }

    /**
     * Возвращает спецификацию "GreaterThanOrEqualTo" или null, если не заполнено значение
     *
     * @param column - наименование поля для сравнения
     * @param value  - значение поля
     * @return
     */
    public static <T> Specification<T> greaterThanEqualToOrNull(String column, Object value) {
        if (value == null || (value instanceof String && StringUtils.isEmpty(value))) {
            return null;
        }

        return GreaterThanOrEqualTo.<T>builder()
                .column(column)
                .value(value)
                .build();
    }

    /**
     * Возвращает спецификацию "LIKE" или null, если не заполнено значение
     *
     * @param column - наименование поля для сравнения
     * @param value  - значение поля
     * @return
     */
    public static <T> Specification<T> likeOrReturnNull(String column, Object value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        return Like.<T>builder()
                .column(column)
                .value(value)
                .build();
    }

    /**
     * Возвращает спецификацию "EQUAL" или null, если не заполнено значение
     *
     * @param column - наименование поля для сравнения
     * @param value  - значение поля
     * @return
     */
    public static <T> Specification<T> equalOrReturnNull(String column, Object value) {
        if (value == null || (value instanceof String && StringUtils.isEmpty(value))) {
            return null;
        }

        return Equal.<T>builder()
                .column(column)
                .value(value)
                .build();
    }

    /**
     * Возвращает спецификацию "true/false" или null, если не заполнено значение
     *
     * @param column - наименование поля для сравнения
     * @param value  - значение
     * @return
     */
    public static <T> Specification<T> trueFalseOrReturnNull(String column, Boolean value) {
        if (value == null) {
            return null;
        }

        return value ? IsTrue.<T>builder().column(column).build()
                : IsFalse.<T>builder().column(column).build();

    }

    public static <T> Specification<T> notInOrReturnNull(String column, Object value) {
        if (value == null) {
            return null;
        }

        return NotIn.<T>builder()
                .column(column)
                .value(value)
                .build();
    }

    public static <T> Specification<T> inOrReturnNull(String column, List<?> values) {
        if (values == null || values.isEmpty()) {
            return null;
        }

        return In.<T>builder()
                .column(column)
                .values(values)
                .build();
    }

    public static <T> Specification<T> likeInOrReturnNull(String column, List<String> value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        return LikeIn.<T>builder()
                .column(column)
                .value(value)
                .build();
    }

    public static <T> Specification<T> trueFalseIsNullOrReturnNull(String column, Boolean value) {
        if (value == null) {
            return null;
        }

        return value ? IsNull.<T>builder()
                .column(column)
                .build() : IsNotNull.<T>builder()
                .column(column)
                .build();
    }

    @Getter
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public enum PropertyType {
        LOCAL_DATE(LocalDate.class),
        LOCAL_DATE_TIME(LocalDateTime.class),
        LONG(Long.class),
        INTEGER(Integer.class),
        BIG_DECIMAL(BigDecimal.class);

        Class<?> type;
    }
}