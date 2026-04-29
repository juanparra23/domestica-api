package com.domesticas.hogar.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HogarResponse {
    private Long id;
    private String nombre;
    private String mensaje;
    private String descripcion;
}