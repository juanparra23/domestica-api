package com.domesticas.tarea.model;

import com.domesticas.hogar.model.Hogar;
import com.domesticas.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tareas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String descripcion;

    private LocalDate fechaInicio;

    private LocalDate fechaLimite;

    private String prioridad; // ALTA, MEDIA, BAJA

    private String estado; // PENDIENTE

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    

    @ManyToOne
    @JoinColumn(name = "hogar_id")
    private Hogar hogar;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;
}