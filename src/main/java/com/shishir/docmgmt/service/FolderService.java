package com.shishir.docmgmt.service;

import com.shishir.docmgmt.entity.Document;
import com.shishir.docmgmt.entity.DocumentRepository;
import com.shishir.docmgmt.entity.Folder;
import com.shishir.docmgmt.entity.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FolderService {

    private final FolderRepository folderRepository;
    private final DocumentRepository documentRepository;

    @Autowired
    public FolderService(FolderRepository folderRepository, DocumentRepository documentRepository) {
        this.folderRepository = folderRepository;
        this.documentRepository = documentRepository;
    }

    public FolderDto getFolderHierarchy() {
        // Fetch the main folder (if it exists)
        Folder mainFolder = folderRepository.findMainFolder();

        if (mainFolder != null) {
            return buildFolderDto(mainFolder);
        }

        return null;
    }

    private FolderDto buildFolderDto(Folder folder) {
        FolderDto folderDto = new FolderDto();
        folderDto.setFolderId(folder.getId());
        folderDto.setName(folder.getName());
        folderDto.setPath(folder.getPath());
        folderDto.setFolders(new ArrayList<>());
        folderDto.setDocuments(new ArrayList<>());

        // Fetch the subFolders
        List<Folder> subFolders = folderRepository.findSubFolders(folder.getId());
        if (!subFolders.isEmpty()) {
            subFolders.forEach(subfolder -> {
                FolderDto subfolderDto = buildFolderDto(subfolder);
                folderDto.getFolders().add(subfolderDto);
            });
        }

        // Fetch the documents
        List<Document> documents = documentRepository.findByFolderId(folder.getId());
        if (!documents.isEmpty()) {
            for (Document document : documents) {
                DocumentDto documentDto = new DocumentDto();
                documentDto.setDocumentId(document.getId());
                documentDto.setName(document.getName());
                documentDto.setUrl(document.getUrl());
                folderDto.getDocuments().add(documentDto);
            }
        }

        return folderDto;
    }
}

