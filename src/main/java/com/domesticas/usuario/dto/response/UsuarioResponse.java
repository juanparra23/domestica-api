package com.domesticas.usuario.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UsuarioResponse {

    private Long id;
    private String nombre;
    private String email;
    private List<GrupoPerfilResponse> grupos;
    private String mensaje;
}