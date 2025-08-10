import json
import logging
import os
from http.server import BaseHTTPRequestHandler, HTTPServer
from urllib.parse import urlparse

from .models import ResponseBuscarDocumento
from .service import AlfrescoClient, DocumentoService

logging.basicConfig(level=logging.INFO)

ALFRESCO_URL = os.getenv("ALFRESCO_URL", "http://alfresco.local")
ALFRESCO_CREDENTIALS = os.getenv("ALFRESCO_CREDENTIALS", "")
service = DocumentoService(AlfrescoClient(ALFRESCO_URL, ALFRESCO_CREDENTIALS))


class BuscarDocumentoHandler(BaseHTTPRequestHandler):
    def do_GET(self):
        parsed = urlparse(self.path)
        if parsed.path.startswith("/buscar_documento_radicado/"):
            id_doc = parsed.path.split("/")[-1]
            logging.info("Inicio buscar documento por idRadicado %s", id_doc)
            result: ResponseBuscarDocumento = service.buscar_documento(id_doc)
            logging.info("Respuesta buscar documento por idRadicado Respuesta: %s", result)
            body = json.dumps(result.to_dict()).encode("utf-8")
            self.send_response(200)
            self.send_header("Content-Type", "application/json")
            self.send_header(
                "Set-Cookie",
                "JSESSIONID=9B9DAA8EFCCECFDBEB7E3E337871FE3A; HttpOnly; Secure",
            )
            self.send_header("Content-Length", str(len(body)))
            self.end_headers()
            self.wfile.write(body)
        else:
            self.send_error(404, "Not Found")


def run(host: str = "0.0.0.0", port: int = 8000):
    server = HTTPServer((host, port), BuscarDocumentoHandler)
    logging.info("Servidor iniciado en %s:%s", host, port)
    server.serve_forever()


if __name__ == "__main__":
    run()
