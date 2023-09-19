package com.example.cinematicketingsystem.security.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.core.annotation.Order;

/**
 * Entity class representing user roles.
 * <p>
 * This class is used to define the structure of the "role" table in the database. It represents
 * user roles and their associated attributes.
 */
@Setter
@Getter
@ToString
@Entity
@Table(name = "role")
@Order(6)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
