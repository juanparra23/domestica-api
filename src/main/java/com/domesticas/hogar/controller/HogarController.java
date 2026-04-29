package com.domesticas.hogar.controller;

import com.domesticas.hogar.dto.request.ActualizarHogarRequest;
import com.domesticas.hogar.dto.request.CrearHogarRequest;
import com.domesticas.hogar.dto.response.DetalleHogarResponse;
import com.domesticas.hogar.dto.response.HogarResponse;
import com.domesticas.hogar.service.HogarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/hogares")
@RequiredArgsConstructor
public class HogarController {

    private final HogarService hogarService;

    @PostMapping
    public ResponseEntity<HogarResponse> crearHogar(
            Authentication authentication,
            @RequestBody CrearHogarRequest request
    ) {
        String email = authentication.getName();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hogarService.crearHogar(email, request));
    }
    
    @GetMapping("/{hogarId}")
    public ResponseEntity<DetalleHogarResponse> obtenerDetalleHogar(
        @PathVariable Long hogarId,
        Authentication authentication
    ) {
        String email = authentication.getName();
        return ResponseEntity.ok(
            hogarService.obtenerDetalleHogar(hogarId,email));
    }

    @PutMapping("/{hogarId}")
    public ResponseEntity<HogarResponse> actualizarHogar(
            @PathVariable Long hogarId,
            Authentication authentication,
            @RequestBody ActualizarHogarRequest request
    ) {
        String email = authentication.getName();
        return ResponseEntity.ok(
                hogarService.actualizarHogar(hogarId, email, request)
        );
    }

    @DeleteMapping("/{hogarId}")
    public ResponseEntity<Void> eliminarHogar(
            @PathVariable Long hogarId,
            Authentication authentication
    ) {
        String email = authentication.getName();
        hogarService.eliminarHogar(hogarId, email);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{hogarId}/abandonar")
public ResponseEntity<String> abandonarGrupo(
        @PathVariable Long hogarId,
        Authentication authentication
) {
    String email = authentication.getName();
    hogarService.abandonarGrupo(hogarId, email);
    return ResponseEntity.ok("Has abandonado el grupo correctamente");
}
}