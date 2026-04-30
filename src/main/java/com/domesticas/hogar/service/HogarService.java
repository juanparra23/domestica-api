
package com.domesticas.hogar.service;

import com.domesticas.exception.BadRequestException;
import com.domesticas.hogar.dto.request.ActualizarHogarRequest;
import com.domesticas.hogar.dto.request.CrearHogarRequest;
import com.domesticas.hogar.dto.response.HogarResponse;
import com.domesticas.hogar.model.Hogar;
import com.domesticas.hogar.model.MiembroHogar;
import com.domesticas.hogar.model.Rol;
import com.domesticas.hogar.repository.HogarRepository;
import com.domesticas.hogar.repository.MiembroHogarRepository;
import com.domesticas.hogar.repository.RolRepository;
import com.domesticas.usuario.model.Usuario;
import com.domesticas.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.domesticas.hogar.dto.response.MiembroGrupoResponse;
import com.domesticas.hogar.dto.response.DetalleHogarResponse;
import java.util.UUID;
import java.util.Map;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HogarService {

    private final HogarRepository hogarRepository;
    private final UsuarioRepository usuarioRepository;
    private final MiembroHogarRepository miembroHogarRepository;
    private final RolRepository rolRepository;


        //genero el codigo de acceso el random genera el identificador unico aleatorio, el toString lo convierte a texto 
        //el substring toma los 6 primeros carateres del texto. y se evitan los codigos duplicados
        private String generarCodigo() {
        String codigo;
        do {
                codigo = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        } while (hogarRepository.existsByCodigoAcceso(codigo));

        return codigo;
        }




        public HogarResponse crearHogar(String email, CrearHogarRequest request) {

                if (request.getNombre() == null || request.getNombre().isBlank()) {
                throw new BadRequestException("El nombre del grupo es obligatorio");
                }

                Usuario usuario = usuarioRepository.findByEmail(email)
                        .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));

                Rol rolPadre = rolRepository.findByNombre("Padre")
                        .orElseThrow(() -> new BadRequestException("Rol Padre no encontrado"));

                Hogar hogar = Hogar.builder()
                .nombre(request.getNombre())
                .codigoAcceso(generarCodigo())
                .build();
                Hogar hogarGuardado = hogarRepository.save(hogar);

                MiembroHogar miembro = MiembroHogar.builder()
                        .usuario(usuario)
                        .hogar(hogarGuardado)
                        .rol(rolPadre)
                        .esAdministrador(true)
                        .build();

                miembroHogarRepository.save(miembro);

                return HogarResponse.builder()
                        .id(hogarGuardado.getId())
                        .nombre(hogarGuardado.getNombre())
                        .mensaje("Grupo creado correctamente")
                        .build();
        }


        // Obtener informacion del hogar
        public DetalleHogarResponse obtenerDetalleHogar(Long hogarId, String email) {

        Hogar hogar = hogarRepository.findById(hogarId)
                .orElseThrow(() -> new BadRequestException("Grupo no encontrado"));

        List<MiembroHogar> miembros = miembroHogarRepository.findByHogarId(hogarId);

        boolean esAdmin = miembros.stream()
                .anyMatch(m ->
                        m.getUsuario().getEmail().equals(email)
                                && Boolean.TRUE.equals(m.getEsAdministrador())
                );

        List<MiembroGrupoResponse> miembrosResponse = miembros.stream()
                .map(m -> MiembroGrupoResponse.builder()
                        .usuarioId(m.getUsuario().getId())
                        .nombre(m.getUsuario().getNombre())
                        .email(m.getUsuario().getEmail())
                        .rol(m.getRol().getNombre())
                        .esAdministrador(m.getEsAdministrador())
                        .build())
                .toList();

        return DetalleHogarResponse.builder()
                .id(hogar.getId())
                .nombre(hogar.getNombre())
                .descripcion(hogar.getDescripcion())
                .miembros(miembrosResponse)
                .codigoAcceso(esAdmin ? hogar.getCodigoAcceso() : null)
                .mensaje("Información del grupo obtenida correctamente")
                .links(Map.of(
                        "self", "/api/v1/hogares/" + hogar.getId(),
                        "editar", "/api/v1/hogares/" + hogar.getId(),
                        "eliminar", "/api/v1/hogares/" + hogar.getId(),
                        "abandonar", "/api/v1/hogares/" + hogar.getId() + "/abandonar",
                        "crear-tarea", "/api/v1/tareas"
                ))
                .build();
        }
    

        public HogarResponse actualizarHogar(Long hogarId, String email, ActualizarHogarRequest request) {

        if (request.getNombre() == null || request.getNombre().isBlank()) {
                throw new BadRequestException("El nombre del grupo es obligatorio");
        }

        Hogar hogar = hogarRepository.findById(hogarId)
                .orElseThrow(() -> new BadRequestException("Grupo no encontrado"));

        MiembroHogar miembro = miembroHogarRepository
                .findByHogarIdAndUsuarioEmail(hogarId, email)
                .orElseThrow(() -> new BadRequestException("No perteneces a este grupo"));

        if (!Boolean.TRUE.equals(miembro.getEsAdministrador())) {
                throw new BadRequestException("Solo el administrador puede editar el grupo");
        }

        hogar.setNombre(request.getNombre());
        hogar.setDescripcion(request.getDescripcion());

     
        Hogar actualizado = hogarRepository.save(hogar);

                return HogarResponse.builder()
                .id(actualizado.getId())
                .nombre(actualizado.getNombre())
                .descripcion(actualizado.getDescripcion())
                .mensaje("Grupo actualizado correctamente")
                .links(Map.of(
                        "self", "/api/v1/hogares/" + actualizado.getId(),
                        "editar", "/api/v1/hogares/" + actualizado.getId(),
                        "eliminar", "/api/v1/hogares/" + actualizado.getId()
                ))
                .build();
        }

                public void eliminarHogar(Long hogarId, String email) {

        Hogar hogar = hogarRepository.findById(hogarId)
                .orElseThrow(() -> new BadRequestException("Grupo no encontrado"));

        MiembroHogar miembro = miembroHogarRepository
                .findByHogarIdAndUsuarioEmail(hogarId, email)
                .orElseThrow(() -> new BadRequestException("No perteneces a este grupo"));

        if (!Boolean.TRUE.equals(miembro.getEsAdministrador())) {
                throw new BadRequestException("Solo el administrador puede eliminar el grupo");
        }

        List<MiembroHogar> miembros = miembroHogarRepository.findByHogarId(hogarId);
        miembroHogarRepository.deleteAll(miembros);

        hogarRepository.delete(hogar);
        }


        @Transactional
public void abandonarGrupo(Long hogarId, String email) {

    Hogar hogar = hogarRepository.findById(hogarId)
            .orElseThrow(() -> new BadRequestException("Grupo no encontrado"));

    MiembroHogar miembro = miembroHogarRepository
            .findByHogarIdAndUsuarioEmail(hogarId, email)
            .orElseThrow(() -> new BadRequestException("No perteneces a este grupo"));

    boolean eraAdmin = Boolean.TRUE.equals(miembro.getEsAdministrador());

    miembroHogarRepository.delete(miembro);

    List<MiembroHogar> miembrosRestantes = miembroHogarRepository.findByHogarId(hogarId);

    if (miembrosRestantes.isEmpty()) {
        hogarRepository.delete(hogar);
        return;
    }

    if (eraAdmin) {
        MiembroHogar nuevoAdmin = miembrosRestantes.get(0);
        nuevoAdmin.setEsAdministrador(true);
        miembroHogarRepository.save(nuevoAdmin);
    }
}

}