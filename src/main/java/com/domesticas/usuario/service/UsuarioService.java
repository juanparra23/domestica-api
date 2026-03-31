package com.domesticas.usuario.service;

import com.domesticas.usuario.dto.response.GrupoPerfilResponse;
import com.domesticas.usuario.dto.response.UsuarioResponse;
import com.domesticas.exception.BadRequestException;
import com.domesticas.hogar.model.MiembroHogar;
import com.domesticas.hogar.repository.MiembroHogarRepository;
import com.domesticas.usuario.model.Usuario;
import com.domesticas.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final MiembroHogarRepository miembroHogarRepository;

    public UsuarioResponse obtenerPerfil(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));

        List<MiembroHogar> membresias = miembroHogarRepository.findByUsuario(usuario);

        List<GrupoPerfilResponse> grupos = membresias.stream()
                .map(m -> GrupoPerfilResponse.builder()
                        .hogarId(m.getHogar().getId())
                        .hogar(m.getHogar().getNombre())
                        .rol(m.getRol().getNombre())
                        .esAdministrador(m.getEsAdministrador())
                        .build())
                .toList();

        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .grupos(grupos)
                .mensaje("Perfil obtenido correctamente")
                .build();
    }
}