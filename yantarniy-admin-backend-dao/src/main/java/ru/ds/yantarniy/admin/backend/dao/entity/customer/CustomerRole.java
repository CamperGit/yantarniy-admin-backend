package ru.ds.yantarniy.admin.backend.dao.entity.customer;

import lombok.Getter;

@Getter
public enum CustomerRole {

    USER("Пользователь"),
    ADMIN("Администратор");

    private final String value;

    CustomerRole(String value) {
        this.value = value;
    }
}
