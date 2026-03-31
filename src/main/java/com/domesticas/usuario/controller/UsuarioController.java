package com.domesticas.usuario.controller;

import com.domesticas.usuario.dto.response.UsuarioResponse;
import com.domesticas.usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/perfil")
    public ResponseEntity<UsuarioResponse> obtenerPerfil(Authentication authentication) {
        String email = authentication.getName();
        UsuarioResponse response = usuarioService.obtenerPerfil(email);
        return ResponseEntity.ok(response);
    }
}
