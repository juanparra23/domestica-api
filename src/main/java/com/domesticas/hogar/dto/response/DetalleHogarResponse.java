package com.domesticas.hogar.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.List;


@Data
@Builder
public class DetalleHogarResponse {
    private Long id;
    private String nombre;
    private List<MiembroGrupoResponse> miembros;
    private String mensaje;
    private String codigoAcceso;
    private String descripcion;
}