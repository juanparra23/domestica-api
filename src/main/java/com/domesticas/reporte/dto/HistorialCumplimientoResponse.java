package com.domesticas.reporte.dto;

import lombok.Builder;
import lombok.Data;
import java.util.Map;

@Data
@Builder
public class HistorialCumplimientoResponse {

    private String usuario;

    private Long asignadas;

    private Long completadas;

    private Double cumplimiento;

    private Map<String, String> links;
}
