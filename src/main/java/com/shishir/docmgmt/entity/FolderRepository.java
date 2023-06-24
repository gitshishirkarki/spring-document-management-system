package com.shishir.docmgmt.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
    @Query("SELECT f FROM Folder f WHERE f.parentFolder IS NULL")
    Folder findMainFolder();

    @Query("SELECT f FROM Folder f WHERE f.parentFolder.id=?1")
    List<Folder> findSubFolders(Long parentFolderId);
}

