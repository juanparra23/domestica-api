package com.domesticas.hogar.service;

import com.domesticas.exception.BadRequestException;
import com.domesticas.hogar.model.MiembroHogar;
import com.domesticas.hogar.repository.MiembroHogarRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MiembroHogarService {

    private final MiembroHogarRepository miembroHogarRepository;

    @Transactional
    public void eliminarMiembro(Long miembroId) {
        MiembroHogar miembro = miembroHogarRepository.findById(miembroId)
                .orElseThrow(() -> new BadRequestException("Miembro no encontrado"));

        Long hogarId = miembro.getHogar().getId();
        boolean eraAdmin = Boolean.TRUE.equals(miembro.getEsAdministrador());

        miembroHogarRepository.delete(miembro);

        if (eraAdmin) {
            reasignarAdministrador(hogarId);
        }
    }

    private void reasignarAdministrador(Long hogarId) {
        List<MiembroHogar> miembros = miembroHogarRepository.findByHogarId(hogarId);

        if (miembros.isEmpty()) {
            return;
        }

        MiembroHogar nuevoAdmin = buscarPorPrioridad(miembros);

        if (nuevoAdmin != null) {
            nuevoAdmin.setEsAdministrador(true);
            miembroHogarRepository.save(nuevoAdmin);
        }
    }

    private MiembroHogar buscarPorPrioridad(List<MiembroHogar> miembros) {
        for (MiembroHogar m : miembros) {
            if ("Madre".equalsIgnoreCase(m.getRol().getNombre())) {
                return m;
            }
        }

        for (MiembroHogar m : miembros) {
            if ("Tutor".equalsIgnoreCase(m.getRol().getNombre())) {
                return m;
            }
        }

        for (MiembroHogar m : miembros) {
            if ("Hijo".equalsIgnoreCase(m.getRol().getNombre())) {
                return m;
            }
        }

        return miembros.get(0);
    }
}