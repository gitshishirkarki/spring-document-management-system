package com.shishir.docmgmt.controller;

import com.shishir.docmgmt.dto.FolderResponseDto;
import com.shishir.docmgmt.entity.Folder;
import com.shishir.docmgmt.entity.FolderRepository;
import com.shishir.docmgmt.service.FolderDto;
import com.shishir.docmgmt.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folders")
public class FolderController {


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
        FolderResponseDto response = folderService.createFolder(folder);
        if (null != response) {
            if (response.getError() != null) {
                // Folder creation failed
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            } else {
                // Folder creation succeeded
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
        } else {
            // Folder creation failed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
    public ResponseEntity<List<FolderDto>> getFolderHierarchy() {
        List<FolderDto> folderHierarchy = folderService.getFolderHierarchy();
        if (folderHierarchy != null) {
            return ResponseEntity.ok(folderHierarchy);
        }
        return ResponseEntity.notFound().build();
    }
}

