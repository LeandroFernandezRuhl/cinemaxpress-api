package com.example.cinematicketingsystem.exception;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class<?> entityClass, String... searchParamsMap) {
        super(EntityNotFoundException.generateMessage(entityClass.getSimpleName(), toMap(searchParamsMap)));
    }

    private static String generateMessage(String entity, Map<String, String> searchParams) {
        return StringUtils.capitalize(entity) + " was not found for parameters " + searchParams;
    }

    private static Map<String, String> toMap(String... entries) {
        if (entries.length % 2 == 1) // if number of entries is not even
            throw new IllegalArgumentException("Invalid entries");

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < entries.length; i += 2) {
            String key = entries[i];
            String value = entries[i + 1];
            map.put(key, value);
        }
        return map;
    }

}
