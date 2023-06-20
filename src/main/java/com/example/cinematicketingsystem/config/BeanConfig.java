package com.example.cinematicketingsystem.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class that defines beans for dependency injection.
 */
@Configuration
public class BeanConfig {

    /**
     * Creates a bean for the {@link ModelMapper}.
     *
     * @return the ModelMapper bean
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

