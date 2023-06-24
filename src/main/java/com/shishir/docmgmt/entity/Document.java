package com.shishir.docmgmt.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Represents a document in the system.
 * Contains attributes such as title, description, and fileUrl.
 * Annotated with @Entity to mark it as a persistent entity in Hibernate.
 * The @Id annotation specifies the primary key.
 * Includes appropriate constructors, getters, and setters.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private String name;
    private String title;
    private String content;
    private String path;
    private String url;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    private Folder folder;
}

