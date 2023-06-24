package com.shishir.docmgmt.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Represents a folder in the document management system.
 * Contains attributes such as name and parentFolder.
 * Annotated with @Entity to mark it as a persistent entity in Hibernate.
 * The @Id annotation specifies the primary key.
 * The @ManyToOne annotation indicates a many-to-one relationship with the Folder entity itself, representing the parent-child folder hierarchy.
 * Includes appropriate constructors, getters, and setters.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_folder_id")
    private Folder parentFolder;
}

