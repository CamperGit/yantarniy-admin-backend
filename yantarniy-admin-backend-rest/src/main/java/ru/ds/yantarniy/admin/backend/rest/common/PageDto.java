package ru.ds.yantarniy.admin.backend.rest.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Страница")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageDto<T> {

    @ApiModelProperty("Список элементов")
    List<T> content;

    @ApiModelProperty("Общее кол-во элементов")
    Long totalElements;

    @ApiModelProperty("Общее кол-во страниц")
    int totalPages;

    @ApiModelProperty("Размер страницы")
    int pageSize;

    @ApiModelProperty("Номер текущей страницы")
    int pageNumber;

    public static <T> PageDto<T> from(Page<T> page) {
        return PageDto.<T>builder()
                .pageSize(page.getSize())
                .pageNumber(page.getNumber())
                .totalElements(page.getTotalElements())
                .content(page.getContent())
                .build();
    }
}