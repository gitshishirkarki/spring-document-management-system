package com.shishir.docmgmt.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByUuid(String uuid);

    @Query("SELECT d FROM Document d WHERE d.folder.id = :folderId")
    List<Document> findByFolderId(@Param("folderId") Long folderId);
}
