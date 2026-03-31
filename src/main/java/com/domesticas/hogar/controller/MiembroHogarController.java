package com.domesticas.hogar.controller;

import com.domesticas.hogar.service.MiembroHogarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/miembros-hogar")
@RequiredArgsConstructor
public class MiembroHogarController {

    private final MiembroHogarService miembroHogarService;

    @DeleteMapping("/{miembroId}")
    public ResponseEntity<Void> eliminarMiembro(@PathVariable Long miembroId) {
        miembroHogarService.eliminarMiembro(miembroId);
        return ResponseEntity.noContent().build();
    }
}