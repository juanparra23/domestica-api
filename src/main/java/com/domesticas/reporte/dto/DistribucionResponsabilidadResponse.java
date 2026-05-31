
package com.domesticas.reporte.dto;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DistribucionResponsabilidadResponse {

    private String usuario;

    private Long totalTareas;

    private Double porcentaje;

    private Map<String, String> links;
}