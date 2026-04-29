package com.domesticas.hogar.repository;

import com.domesticas.hogar.model.MiembroHogar;
import com.domesticas.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MiembroHogarRepository extends JpaRepository<MiembroHogar, Long> {

    List<MiembroHogar> findByUsuario(Usuario usuario);

    Optional<MiembroHogar> findByUsuarioIdAndHogarId(Long usuarioId, Long hogarId);

    List<MiembroHogar> findByHogarId(Long hogarId);

    Optional<MiembroHogar> findByHogarIdAndEsAdministradorTrue(Long hogarId);

    Optional<MiembroHogar> findByHogarIdAndUsuarioEmail(Long hogarId, String email);

   

}