package com.domesticas.tarea.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CrearTareaRequest {

    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaLimite;
    private String prioridad;
    private Long hogarId;
}