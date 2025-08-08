package com.prolinktic.sgdea.dtos.radicado;

/*
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class ResponseAlfrescoEntrada {
    Entry EntryObject;


    // Getter Methods

    public Entry getEntry() {
        return EntryObject;
    }

    // Setter Methods

    public void setEntry( Entry entryObject ) {
        this.EntryObject = entryObject;
    }
}
public class Entry {
    private boolean isFile;
    CreatedByUser CreatedByUserObject;
    private String modifiedAt;
    private String nodeType;
    Content ContentObject;
    private String parentId;
    ArrayList<Object> aspectNames = new ArrayList<Object>();
    private String createdAt;
    private boolean isFolder;
    ModifiedByUser ModifiedByUserObject;
    private String name;
    private String id;
    Properties PropertiesObject;


    // Getter Methods

    public boolean getIsFile() {
        return isFile;
    }

    public CreatedByUser getCreatedByUser() {
        return CreatedByUserObject;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public String getNodeType() {
        return nodeType;
    }

    public Content getContent() {
        return ContentObject;
    }

    public String getParentId() {
        return parentId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public boolean getIsFolder() {
        return isFolder;
    }

    public ModifiedByUser getModifiedByUser() {
        return ModifiedByUserObject;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Properties getProperties() {
        return PropertiesObject;
    }

    // Setter Methods

    public void setIsFile( boolean isFile ) {
        this.isFile = isFile;
    }

    public void setCreatedByUser( CreatedByUser createdByUserObject ) {
        this.CreatedByUserObject = createdByUserObject;
    }

    public void setModifiedAt( String modifiedAt ) {
        this.modifiedAt = modifiedAt;
    }

    public void setNodeType( String nodeType ) {
        this.nodeType = nodeType;
    }

    public void setContent( Content contentObject ) {
        this.ContentObject = contentObject;
    }

    public void setParentId( String parentId ) {
        this.parentId = parentId;
    }

    public void setCreatedAt( String createdAt ) {
        this.createdAt = createdAt;
    }

    public void setIsFolder( boolean isFolder ) {
        this.isFolder = isFolder;
    }

    public void setModifiedByUser( ModifiedByUser modifiedByUserObject ) {
        this.ModifiedByUserObject = modifiedByUserObject;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public void setProperties( Properties propertiesObject ) {
        this.PropertiesObject = propertiesObject;
    }
}
public class Properties {
    @JsonProperty("proltk:descripcion_anexo")
    private String proltk_descripcion_anexo;

    @JsonProperty("proltk:anexos")
    private boolean proltk_anexos;

    @JsonProperty("proltk:numero_folios_anexos")
    private float proltk_numero_folios_anexos;

    @JsonProperty("proltk:tipo_tramite")
    private String proltk_tipo_tramite;

    @JsonProperty("proltk:asunto_radicado")
    private String proltk_asunto_radicado;

    @JsonProperty("proltk:numero_folios")
    private float proltk_numero_folios;

    @JsonProperty("proltk:tipo_comunicacion")
    private String proltk_tipo_comunicacion;


    // Getter Methods

    public String getProltk:descripcion_anexo() {
        return proltk:descripcion_anexo;
    }

    public boolean getProltk:anexos() {
        return proltk:anexos;
    }

    public float getProltk:numero_folios_anexos() {
        return proltk:numero_folios_anexos;
    }

    public String getProltk:tipo_tramite() {
        return proltk:tipo_tramite;
    }

    public String getProltk:asunto_radicado() {
        return proltk:asunto_radicado;
    }

    public float getProltk:numero_folios() {
        return proltk:numero_folios;
    }

    public String getProltk:tipo_comunicacion() {
        return proltk:tipo_comunicacion;
    }

    // Setter Methods

    public void setProltk:descripcion_anexo( String proltk:descripcion_anexo ) {
        this.proltk:descripcion_anexo = proltk:descripcion_anexo;
    }

    public void setProltk:anexos( boolean proltk:anexos ) {
        this.proltk:anexos = proltk:anexos;
    }

    public void setProltk:numero_folios_anexos( float proltk:numero_folios_anexos ) {
        this.proltk:numero_folios_anexos = proltk:numero_folios_anexos;
    }

    public void setProltk:tipo_tramite( String proltk:tipo_tramite ) {
        this.proltk:tipo_tramite = proltk:tipo_tramite;
    }

    public void setProltk:asunto_radicado( String proltk:asunto_radicado ) {
        this.proltk:asunto_radicado = proltk:asunto_radicado;
    }

    public void setProltk:numero_folios( float proltk:numero_folios ) {
        this.proltk:numero_folios = proltk:numero_folios;
    }

    public void setProltk:tipo_comunicacion( String proltk:tipo_comunicacion ) {
        this.proltk:tipo_comunicacion = proltk:tipo_comunicacion;
    }
}
public class ModifiedByUser {
    private String id;
    private String displayName;


    // Getter Methods

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    // Setter Methods

    public void setId( String id ) {
        this.id = id;
    }

    public void setDisplayName( String displayName ) {
        this.displayName = displayName;
    }
}
public class Content {
    private String mimeType;
    private String mimeTypeName;
    private float sizeInBytes;
    private String encoding;


    // Getter Methods

    public String getMimeType() {
        return mimeType;
    }

    public String getMimeTypeName() {
        return mimeTypeName;
    }

    public float getSizeInBytes() {
        return sizeInBytes;
    }

    public String getEncoding() {
        return encoding;
    }

    // Setter Methods

    public void setMimeType( String mimeType ) {
        this.mimeType = mimeType;
    }

    public void setMimeTypeName( String mimeTypeName ) {
        this.mimeTypeName = mimeTypeName;
    }

    public void setSizeInBytes( float sizeInBytes ) {
        this.sizeInBytes = sizeInBytes;
    }

    public void setEncoding( String encoding ) {
        this.encoding = encoding;
    }
}
public class CreatedByUser {
    private String id;
    private String displayName;


    // Getter Methods

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    // Setter Methods

    public void setId( String id ) {
        this.id = id;
    }

    public void setDisplayName( String displayName ) {
        this.displayName = displayName;
    }
}

 */