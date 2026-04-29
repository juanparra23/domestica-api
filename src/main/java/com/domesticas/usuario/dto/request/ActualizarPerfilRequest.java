package com.domesticas.usuario.dto.request;

import lombok.Data;

@Data
public class ActualizarPerfilRequest {
    private String nombre;
    private String email;
}