package com.prolinktic.sgdea.infrastructura.gateway;


import com.prolinktic.sgdea.configuration.FeignClientConfiguration;
import com.prolinktic.sgdea.dtos.expediente.AlfrescoResponseDto;
import com.prolinktic.sgdea.dtos.radicado.AlfrescoResponse;
import com.prolinktic.sgdea.dtos.radicado.AlfrescoResponseDTO;
import com.prolinktic.sgdea.model.Alfesco.CreateNode;
import com.prolinktic.sgdea.model.Alfesco.NodesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import com.prolinktic.sgdea.dtos.radicado.EntradaAlfresco;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@FeignClient(value = "alfrescohost", url = "${alfresco.host}", configuration = FeignClientConfiguration.class)
public interface AlfrescoClient {
    @GetMapping(path = "/alfresco/versions/1/nodes/{id}")
    public String obtenerContenidoDeNodo(@PathVariable(name = "id") String id);


    //inicio de consumos de juan diego mora

//    @PostMapping(path = "/search/versions/1/search")



  //  alfresco.host=http://ec2-34-193-209-210.compute-1.amazonaws.com:8080/alfresco/api/-default-/public
//    alfresco.url=http://ec2-34-193-209-210.compute-1.amazonaws.com:8080/alfresco/api/-default-/public/alfresco/versions/1

    @PostMapping(path = "/search/versions/1/search")
    public Object buscarDocumento(@RequestBody Object peticion);

    @GetMapping(path = "/alfresco/versions/1/nodes/{id}/content?attachment=true")
    public String consultarDocumento(@PathVariable(name = "id") String id);
    //este endpoint de alfresco devuelve base 64 si se agrega al final /content?attachment=true

    //fin de consumos de juan diego mora


    @DeleteMapping(path = "/alfresco/versions/1/nodes/{id}")
    public Boolean eliminarPlantilla(@PathVariable(name = "id")String id);



//inicio jdme
    /*
    1- crear la carpeta
    2- crear el archivo principal
    3- crear los anexos (consumir varias veces con un for)
     */


    //@PostMapping(path = "/alfresco/versions/1/nodes/{id}/children", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   // public NodesResponse crearNodo(@PathVariable(name = "id") String id, @RequestBody String body, @RequestPart(name = "filedata") MultipartFile file);

    @PostMapping(path = "/alfresco/versions/1/nodes/{id}/children") //utilizar este para crear la carpeta
    public NodesResponse crearNodo(@PathVariable(name = "id") String id, @RequestBody CreateNode body);

    @GetMapping("/alfresco/versions/1/nodes/{id}/children")
    public AlfrescoResponseDTO getAllPlantilla(@PathVariable(name = "id") String id);


    //paso 2- crear archivo principal
    @PostMapping(path = "/alfresco/versions/1/nodes/{id}/children",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AlfrescoResponse radicadoEntrada(@RequestBody EntradaAlfresco entradaAlfresco, @RequestPart(name = "filedata") MultipartFile file, @PathVariable(name = "id") String id);


    //paso 3- crear los anexos
    @PostMapping(value = "/alfresco/versions/1/nodes/{id}/children", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object crearAnexos(
            @PathVariable (name = "id")String id,
            @RequestPart("filedata") MultipartFile filedata,
            @RequestPart("nodeType") String nodeType,
            @RequestPart("name") String name
    );
    @GetMapping(path = "/alfresco/versions/1/nodes/{id}")
    @ResponseBody
    public AlfrescoResponseDto obtenerContentType(@PathVariable(name = "id") String id);

    @PutMapping(path = "/alfresco/versions/1/nodes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public NodesResponse actualizarNodo(@PathVariable(name = "id") String id, @RequestBody CreateNode body);
    @GetMapping(path = "/alfresco/versions/1/nodes/{id}/content?attachment=true")
    @ResponseBody
    public byte[] consultarDocumentoByte(@PathVariable(name = "id") String id);


    @PostMapping(path = "/search/versions/1/search")
    public AlfrescoResponseDTO buscarNodeIdPorNombreCarpeta(@RequestBody Object peticion);
    @GetMapping("/alfresco/versions/1/nodes/{parentId}/children")
    public AlfrescoResponseDTO obtenerNodoPorNombre(@PathVariable(name = "parentId") String parentId,
                                                    @RequestParam(name = "name") String nombre);
}
