package com.shishir.docmgmt.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Represents the association between documents and permissions.
 * Contains attributes document and permission.
 * Annotated with @Entity to mark it as a persistent entity in Hibernate.
 * The @Id annotation specifies the primary key.
 * The @ManyToOne annotation indicates a many-to-one relationship with the Document entity and Permission entity.
 * Includes appropriate constructors, getters, and setters.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DocumentPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id")
    private Permission permission;
}

