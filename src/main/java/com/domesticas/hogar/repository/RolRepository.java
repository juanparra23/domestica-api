package com.domesticas.hogar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domesticas.hogar.model.Rol;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findByNombre(String nombre);
}