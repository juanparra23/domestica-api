package com.domesticas.hogar.model;

import com.domesticas.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "solicitudes_ingreso")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudIngreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "hogar_id", nullable = false)
    private Hogar hogar;

    @Column(nullable = false, length = 20)
    private String estado;
}