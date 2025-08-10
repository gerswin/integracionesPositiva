from dataclasses import dataclass, asdict
from typing import Any, Dict, List


@dataclass
class Documento:
    id: str
    nombre: str
    descripcion: str
    propiedades: Dict[str, Any]

    def to_dict(self) -> Dict[str, Any]:
        return asdict(self)


@dataclass
class ResponseBuscarDocumento:
    idRadicado: str
    estado: int
    mensaje: str
    documentos: List[Documento]

    def to_dict(self) -> Dict[str, Any]:
        return {
            "idRadicado": self.idRadicado,
            "estado": self.estado,
            "mensaje": self.mensaje,
            "documentos": [d.to_dict() for d in self.documentos],
        }
