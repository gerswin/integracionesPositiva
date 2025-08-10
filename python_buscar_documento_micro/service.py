import json
from typing import Any, Dict, List
from urllib import request

from .models import Documento, ResponseBuscarDocumento


class AlfrescoClient:
    """Cliente minimalista para comunicarse con el servicio de Alfresco."""

    def __init__(self, base_url: str, credentials: str):
        self.base_url = base_url.rstrip("/")
        self.credentials = credentials

    def _headers(self) -> Dict[str, str]:
        return {"Authorization": f"Basic {self.credentials}"}

    def _get(self, url: str) -> Dict[str, Any]:
        req = request.Request(url, headers=self._headers())
        with request.urlopen(req) as resp:
            data = resp.read()
        return json.loads(data.decode("utf-8"))

    def get_node_children(self, node_id: str) -> List[Dict[str, Any]]:
        url = f"{self.base_url}/nodes/{node_id}/children"
        data = self._get(url)
        return data["list"]["entries"]

    def get_node(self, node_id: str) -> Dict[str, Any]:
        url = f"{self.base_url}/nodes/{node_id}"
        return self._get(url)


class DocumentoService:
    def __init__(self, client: AlfrescoClient):
        self.client = client

    def buscar_documento(self, id_documento: str) -> ResponseBuscarDocumento:
        entries = self.client.get_node_children(id_documento)
        documentos: List[Documento] = []
        for node in entries:
            node_id = node["entry"]["id"]
            archivo = self.client.get_node(node_id)
            entry = archivo["entry"]
            nombre = entry["name"]
            descripcion = "Documento principal" if nombre.startswith("pp-") else "Documento secundario"
            propiedades = entry.get("properties", {})
            documentos.append(
                Documento(
                    id=entry["id"],
                    nombre=nombre,
                    descripcion=descripcion,
                    propiedades=propiedades,
                )
            )
        return ResponseBuscarDocumento(
            idRadicado=id_documento,
            estado=200,
            mensaje="OK",
            documentos=documentos,
        )
