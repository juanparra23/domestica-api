package com.domesticas.hogar.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class HogarResponse {

    private Long id;
    private String nombre;
    private String descripcion;
    private String mensaje;
    private Map<String, String> links;
}