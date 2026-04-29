package com.domesticas.hogar.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MiembroGrupoResponse {
    private Long usuarioId;
    private String nombre;
    private String email;
    private String rol;
    private Boolean esAdministrador;
}