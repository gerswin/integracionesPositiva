import json
import os
import sys
import threading
from http.server import HTTPServer
from urllib import request

# Asegurar que el paquete pueda importarse cuando se ejecuta desde la raíz del repositorio
sys.path.insert(0, os.path.abspath(os.path.join(os.path.dirname(__file__), "..", "..")))

from python_buscar_documento_micro import main
from python_buscar_documento_micro.service import DocumentoService


class FakeClient:
    def get_node_children(self, node_id):
        return [{"entry": {"id": "child1"}}, {"entry": {"id": "child2"}}]

    def get_node(self, node_id):
        nombre = "pp-archivo" if node_id == "child1" else "ss-archivo"
        return {
            "entry": {
                "id": node_id,
                "name": nombre,
                "properties": {"prop1": "valor"},
            }
        }


def test_buscar_documento_endpoint(monkeypatch):
    fake_service = DocumentoService(FakeClient())
    monkeypatch.setattr(main, "service", fake_service)
    server = HTTPServer(("localhost", 0), main.BuscarDocumentoHandler)
    thread = threading.Thread(target=server.serve_forever)
    thread.daemon = True
    thread.start()
    try:
        port = server.server_port
        resp = request.urlopen(f"http://localhost:{port}/buscar_documento_radicado/123")
        assert resp.status == 200
        data = json.loads(resp.read().decode())
        assert data["idRadicado"] == "123"
        assert "JSESSIONID=9B9DAA8EFCCECFDBEB7E3E337871FE3A" in resp.headers.get("Set-Cookie")
        assert len(data["documentos"]) == 2
        assert data["documentos"][0]["descripcion"] == "Documento principal"
        assert data["documentos"][1]["descripcion"] == "Documento secundario"
    finally:
        server.shutdown()
        thread.join()
