package com.shishir.docmgmt.service;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FolderDto {
    private Long folderId;
    private String name;
    private String path;
    private List<FolderDto> folders;
    private List<DocumentDto> documents;
}



