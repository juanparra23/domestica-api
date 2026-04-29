package com.domesticas.hogar.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.domesticas.hogar.model.Hogar;

public interface HogarRepository extends JpaRepository<Hogar, Long> {
    boolean existsByCodigoAcceso(String codigoAcceso);

    Optional<Hogar> findByCodigoAcceso(String codigoAcceso);
}