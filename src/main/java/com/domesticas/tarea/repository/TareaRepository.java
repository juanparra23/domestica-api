package com.domesticas.tarea.repository;

import com.domesticas.tarea.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
}