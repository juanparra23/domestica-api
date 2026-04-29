package com.domesticas.tarea.controller;

import com.domesticas.tarea.dto.request.CambiarEstadoRequest;
import com.domesticas.tarea.dto.request.CrearTareaRequest;
import com.domesticas.tarea.service.TareaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tareas")
@RequiredArgsConstructor
public class TareaController {

    private final TareaService tareaService;

    @PostMapping
    public ResponseEntity<String> crearTarea(
            Authentication authentication,
            @RequestBody CrearTareaRequest request
    ) {
        String email = authentication.getName();
        tareaService.crearTarea(email, request);
        return ResponseEntity.ok("Tarea creada correctamente");
    }

    @PutMapping("/{tareaId}/estado")
public ResponseEntity<String> cambiarEstado(
        @PathVariable Long tareaId,
        Authentication authentication,
        @RequestBody CambiarEstadoRequest request
) {
    String email = authentication.getName();

    tareaService.cambiarEstado(
            tareaId,
            email,
            request.getEstado()
    );

    return ResponseEntity.ok("Estado actualizado correctamente");
}
}