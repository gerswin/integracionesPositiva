package com.prolinktic.sgdea.model.Alfesco;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NodesEntry {

    private String id;
    private String name;
    private String nodeType;
    private boolean isFolder;
    private boolean isFile;

}
