package com.domesticas.tarea.service;

import com.domesticas.exception.BadRequestException;
import com.domesticas.hogar.model.Hogar;
import com.domesticas.hogar.repository.HogarRepository;
import com.domesticas.tarea.dto.request.CrearTareaRequest;
import com.domesticas.tarea.model.Tarea;
import com.domesticas.tarea.repository.TareaRepository;
import com.domesticas.usuario.model.Usuario;
import com.domesticas.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TareaService {

    private final TareaRepository tareaRepository;
    private final UsuarioRepository usuarioRepository;
    private final HogarRepository hogarRepository;

    public void crearTarea(String email, CrearTareaRequest request) {

        if (request.getNombre() == null || request.getNombre().isBlank()) {
            throw new BadRequestException("El nombre es obligatorio");
        }

        if (request.getFechaLimite() == null) {
            throw new BadRequestException("La fecha límite es obligatoria");
        }

        if (request.getPrioridad() == null || request.getPrioridad().isBlank()) {
            throw new BadRequestException("La prioridad es obligatoria");
        }

        if (request.getFechaInicio() != null &&
            request.getFechaLimite().isBefore(request.getFechaInicio())) {
            throw new BadRequestException("La fecha límite no puede ser anterior a la fecha de inicio");
        }

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));

        Hogar hogar = hogarRepository.findById(request.getHogarId())
                .orElseThrow(() -> new BadRequestException("Grupo no encontrado"));

        Tarea tarea = Tarea.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .fechaInicio(request.getFechaInicio())
                .fechaLimite(request.getFechaLimite())
                .prioridad(request.getPrioridad())
                .estado("PENDIENTE")
                .usuario(usuario)
                .hogar(hogar)
                .build();

        tareaRepository.save(tarea);
    }

    public void cambiarEstado(Long tareaId, String email, String nuevoEstado) {

    Tarea tarea = tareaRepository.findById(tareaId)
            .orElseThrow(() -> new BadRequestException("Tarea no encontrada"));

    // validar que es el dueño
    if (!tarea.getUsuario().getEmail().equals(email)) {
        throw new BadRequestException("Solo el usuario asignado puede cambiar el estado");
    }

    String estadoActual = tarea.getEstado();

    // validar flujo
    if ("PENDIENTE".equals(estadoActual) && "EN_PROCESO".equals(nuevoEstado)) {
        tarea.setEstado("EN_PROCESO");

    } else if ("EN_PROCESO".equals(estadoActual) && "COMPLETADA".equals(nuevoEstado)) {
        tarea.setEstado("COMPLETADA");
        tarea.setFechaFin(LocalDateTime.now());

    } else {
        throw new BadRequestException("Transición de estado inválida");
    }

    tareaRepository.save(tarea);
}


}