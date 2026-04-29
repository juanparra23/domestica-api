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
import com.domesticas.usuario.dto.request.ActualizarPerfilRequest;



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
    public UsuarioResponse actualizarPerfil(String emailActual, ActualizarPerfilRequest request) {

    Usuario usuario = usuarioRepository.findByEmail(emailActual)
            .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));

   
    if (request.getNombre() == null || request.getNombre().isBlank()) {
        throw new BadRequestException("El nombre es obligatorio");
    }

    
    if (request.getEmail() == null || request.getEmail().isBlank()) {
        throw new BadRequestException("El correo es obligatorio");
    }

    
    if (!usuario.getEmail().equals(request.getEmail())) {

        boolean existe = usuarioRepository.findByEmail(request.getEmail()).isPresent();

        if (existe) {
            throw new BadRequestException("El correo ya está en uso");
        }
    }

    
    usuario.setNombre(request.getNombre());
    usuario.setEmail(request.getEmail());

    Usuario actualizado = usuarioRepository.save(usuario);

    return UsuarioResponse.builder()
            .id(actualizado.getId())
            .nombre(actualizado.getNombre())
            .email(actualizado.getEmail())
            .mensaje("Perfil actualizado correctamente")
            .build();
}
}