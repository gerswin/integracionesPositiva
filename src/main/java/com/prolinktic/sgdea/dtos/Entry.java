package com.prolinktic.sgdea.dtos;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Entry {
    private String createdAt;
    private boolean isFolder;
    private boolean isFile;
    private String modifiedAt;
    private String name;
    private String id;
    private String nodeType;
    private String parentId;
    private Map<String, Object> properties;
}
