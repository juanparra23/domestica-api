package com.domesticas.usuario.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GrupoPerfilResponse {

    private Long hogarId;
    private String hogar;
    private String rol;
    private Boolean esAdministrador;
}