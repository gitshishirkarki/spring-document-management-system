package com.shishir.docmgmt.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentDto {
    private Long documentId;
    private String name;
    private String url;
}
