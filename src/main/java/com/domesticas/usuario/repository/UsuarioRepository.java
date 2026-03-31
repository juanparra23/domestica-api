package com.domesticas.usuario.repository;

import com.domesticas.usuario.model.Usuario;
import com.domesticas.usuario.repository.UsuarioRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);
}