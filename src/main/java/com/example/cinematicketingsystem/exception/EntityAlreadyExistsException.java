package com.example.cinematicketingsystem.exception;

import org.springframework.util.StringUtils;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(Class<?> entityClass, String id) {
        super(buildMessage(entityClass.getSimpleName(), id));
    }

    private static String buildMessage(String entity, String id) {
        return StringUtils.capitalize(entity) + " already exists with id=" + id;
    }
}
