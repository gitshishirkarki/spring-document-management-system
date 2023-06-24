package com.shishir.docmgmt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FolderResponseDto {
    private Long id;

    private String name;

    private String path;

    private String error;
}
