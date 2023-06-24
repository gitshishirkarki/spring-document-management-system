package com.shishir.docmgmt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentResponseDto {
    private Long id;
    private String uuid;
    private String name;
    private String title;
    private String content;
    private String path;
    private String url;
}
