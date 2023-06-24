package com.shishir.docmgmt.controller;

import com.shishir.docmgmt.dto.FolderResponseDto;
import com.shishir.docmgmt.entity.Folder;
import com.shishir.docmgmt.entity.FolderRepository;
import com.shishir.docmgmt.service.FolderDto;
import com.shishir.docmgmt.service.FolderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/folders")
public class FolderController {
    @Value("${shishir.docs.folder.main}")
    private String mainDirectory;

    private final FolderRepository folderRepository;
    private final FolderService folderService;

    @Autowired
    public FolderController(FolderRepository folderRepository, FolderService folderService) {
        this.folderRepository = folderRepository;
        this.folderService = folderService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Folder>> getAllFolders() {
        Iterable<Folder> folders = folderRepository.findAll();
        return ResponseEntity.ok(folders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Folder> getFolderById(@PathVariable Long id) {
        Folder folder = folderRepository.findById(id).orElse(null);
        if (folder != null) {
            return ResponseEntity.ok(folder);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<FolderResponseDto> createFolder(@RequestBody Folder folder) {
        // Save the folder in the repository
        Folder parentFolder = null;
        if (folder.getParentFolder() != null && folder.getParentFolder().getId() != null) {
            parentFolder = folderRepository.findById(folder.getParentFolder().getId()).orElse(null);
            if (parentFolder == null) {
                return ResponseEntity.notFound().build();
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
        createParentFolders(folderPath);

        // Create a physical folder in the system
        File newFolder = new File(folderPath);

        if (newFolder.exists()) {
            // Folder already exists
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        if (newFolder.mkdir()) {
            //Generate response
            FolderResponseDto folderResponseDto = new FolderResponseDto();
            BeanUtils.copyProperties(savedFolder, folderResponseDto);
            // Folder creation succeeded
            return ResponseEntity.status(HttpStatus.CREATED).body(folderResponseDto);
        } else {
            // Folder creation failed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void createParentFolders(String folderPath) {
        File parentFolder = new File(folderPath).getParentFile();
        if (!parentFolder.exists()) {
            parentFolder.mkdirs();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Folder> updateFolder(@PathVariable Long id, @RequestBody Folder updatedFolder) {
        Folder folder = folderRepository.findById(id).orElse(null);
        if (folder != null) {
            folder.setName(updatedFolder.getName());
            Folder updated = folderRepository.save(folder);
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFolder(@PathVariable Long id) {
        if (folderRepository.existsById(id)) {
            folderRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/hierarchy")
    public ResponseEntity<FolderDto> getFolderHierarchy() {
        FolderDto folderHierarchy = folderService.getFolderHierarchy();
        if (folderHierarchy != null) {
            return ResponseEntity.ok(folderHierarchy);
        }
        return ResponseEntity.notFound().build();
    }
}

