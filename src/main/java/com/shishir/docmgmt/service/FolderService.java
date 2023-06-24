package com.shishir.docmgmt.service;

import com.shishir.docmgmt.dto.FolderResponseDto;
import com.shishir.docmgmt.entity.Document;
import com.shishir.docmgmt.entity.DocumentRepository;
import com.shishir.docmgmt.entity.Folder;
import com.shishir.docmgmt.entity.FolderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class FolderService {
    @Value("${shishir.docs.folder.main}")
    private String mainDirectory;

    private final FolderRepository folderRepository;
    private final DocumentRepository documentRepository;

    @Autowired
    public FolderService(FolderRepository folderRepository, DocumentRepository documentRepository) {
        this.folderRepository = folderRepository;
        this.documentRepository = documentRepository;
    }

    public FolderResponseDto createFolder(Folder folder) {
        FolderResponseDto folderResponseDto = new FolderResponseDto();

        Folder parentFolder = null;
        if (folder.getParentFolder() != null && folder.getParentFolder().getId() != null) {
            parentFolder = folderRepository.findById(folder.getParentFolder().getId()).orElse(null);
            if (parentFolder == null) {
                folderResponseDto.setError("Parent Folder does not exist.");
                return folderResponseDto;
            }
        }

        folder.setParentFolder(parentFolder);

        String folderPath;
        if (parentFolder != null) {
            folderPath = parentFolder.getPath() + "/" + folder.getName();
        } else {
            folderPath = mainDirectory + folder.getName();
        }
        folder.setPath(folderPath);

        Folder savedFolder = folderRepository.save(folder);

        // Create all necessary parent folders
        this.createParentFolders(folderPath);

        // Create a physical folder in the system
        File newFolder = new File(folderPath);

        if (newFolder.exists()) {
            // Folder already exists
            folderResponseDto.setError("Conflict! folder already exists.");
            return folderResponseDto;
        }

        if (newFolder.mkdir()) {
            //Generate response
            BeanUtils.copyProperties(savedFolder, folderResponseDto);
            return folderResponseDto;
        }

        return null;
    }

    private void createParentFolders(String folderPath) {
        File parentFolder = new File(folderPath).getParentFile();
        if (!parentFolder.exists()) {
            parentFolder.mkdirs();
        }
    }

    public List<FolderDto> getFolderHierarchy() {
        // Fetch the main folder (if it exists)
        List<Folder> mainFolders = folderRepository.findMainFolder();

        List<FolderDto> folderDtos = new ArrayList<>();
        mainFolders.forEach(mainFolder -> {
            if (mainFolder != null) {
                folderDtos.add(buildFolderDto(mainFolder));
            }
        });

        return folderDtos;
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

