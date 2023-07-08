package ru.ds.yantarniy.admin.backend.core.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationsSearchService<T> {

    Page<T> findAll(Specification<T> specification, PageRequest request);

    long countItemsByFilter(Specification<T> specification);
}
