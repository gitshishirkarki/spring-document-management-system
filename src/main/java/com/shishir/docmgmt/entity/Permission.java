package com.shishir.docmgmt.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Represents a permission that can be assigned to documents.
 * Contains an attribute name to specify the permission name (e.g., READ, WRITE).
 * Annotated with @Entity to mark it as a persistent entity in Hibernate.
 * The @Id annotation specifies the primary key.
 * Includes appropriate constructors, getters, and setters.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}

