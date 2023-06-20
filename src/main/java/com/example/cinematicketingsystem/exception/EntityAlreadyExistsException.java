package com.example.cinematicketingsystem.exception;

import org.springframework.util.StringUtils;

/**
 * Exception thrown when attempting to create an entity that already exists.
 * <p>
 * This exception is thrown when an attempt is made to create an entity that already exists in the system, typically
 * identified by its ID. It is a runtime exception, indicating a violation of the application's business logic.
 */
public class EntityAlreadyExistsException extends RuntimeException {
    /**
     * Constructs a new {@code EntityAlreadyExistsException} with the specified entity class and ID.
     *
     * @param entityClass the class of the entity that already exists
     * @param id          the ID of the entity
     */
    public EntityAlreadyExistsException(Class<?> entityClass, String id) {
        super(buildMessage(entityClass.getSimpleName(), id));
    }

    /**
     * Builds the exception message based on the entity class and ID.
     *
     * @param entity the name of the entity
     * @param id     the ID of the entity
     * @return the formatted exception message
     */
    private static String buildMessage(String entity, String id) {
        return StringUtils.capitalize(entity) + " already exists with id=" + id;
    }
}
