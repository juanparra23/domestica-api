package com.domesticas.hogar.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class DetalleHogarResponse {

    private Long id;
    private String nombre;
    private String descripcion;
    private String codigoAcceso;
    private List<MiembroGrupoResponse> miembros;
    private String mensaje;
    private Map<String, String> links;
}