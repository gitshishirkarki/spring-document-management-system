package com.shishir.docmgmt.controller;

import com.shishir.docmgmt.dto.DocumentResponseDto;
import com.shishir.docmgmt.entity.Document;
import com.shishir.docmgmt.entity.DocumentRepository;
import com.shishir.docmgmt.entity.Folder;
import com.shishir.docmgmt.entity.FolderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentRepository documentRepository;
    private final FolderRepository folderRepository;

    @Autowired
    public DocumentController(DocumentRepository documentRepository, FolderRepository folderRepository) {
        this.documentRepository = documentRepository;
        this.folderRepository = folderRepository;
    }

    @PostMapping("/{folderId}")
    public ResponseEntity<DocumentResponseDto> createDocument(@PathVariable Long folderId,
                                                   @RequestParam("file") MultipartFile file) {
        // Check if the folder exists
        Optional<Folder> optionalFolder = folderRepository.findById(folderId);
        if (!optionalFolder.isPresent()) {
            // If the folder does not exist, return appropriate response
            return ResponseEntity.notFound().build();
        }

        Folder folder = optionalFolder.get();
        String folderPath = folder.getPath();

        try {
            String uuid = UUID.randomUUID().toString();
            // Save the uploaded file to a local directory
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String filename = uuid + extension;
            String filePath = folderPath + "/" + filename;
            File newFile = new File(filePath);
            Files.copy(file.getInputStream(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Create a document entity
            Document document = new Document();
            document.setName(file.getOriginalFilename());
            document.setPath(filePath);
            document.setUuid(uuid);
            document.setFolder(folder);

            // Generate the URL for downloading the document
            String downloadUrl = "/documents/download/" + document.getUuid();
            document.setUrl(downloadUrl);

            Document savedDocument = documentRepository.save(document);

            // Create the response DTO
            DocumentResponseDto responseDto = new DocumentResponseDto();
            BeanUtils.copyProperties(savedDocument, responseDto);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/download/{uuid}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable UUID uuid) {
        // Retrieve the document from the repository
        Document document = documentRepository.findByUuid(uuid.toString()).orElse(null);
        if (document == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            // Load the document file as a resource
            Path filePath = Paths.get(document.getPath());
            Resource resource = new UrlResource(filePath.toUri());

            // Set the appropriate headers for file download
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getName() + "\"");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
