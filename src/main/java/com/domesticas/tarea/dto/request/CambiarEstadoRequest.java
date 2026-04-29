package com.domesticas.tarea.dto.request;

import lombok.Data;

@Data
public class CambiarEstadoRequest {
    private String estado; // EN_PROCESO o COMPLETADA
}