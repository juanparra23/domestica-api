package com.domesticas.hogar.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.domesticas.usuario.model.Usuario;

@Entity
@Table(
    name = "miembros_hogar",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"usuario_id", "hogar_id"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MiembroHogar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "hogar_id", nullable = false)
    private Hogar hogar;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    @Column(name = "es_administrador")
    private Boolean esAdministrador;

    @Column(name = "fecha_union", nullable = false)
    private LocalDateTime fechaUnion;

    @PrePersist
    public void prePersist() {
        this.fechaUnion = LocalDateTime.now();
        if (this.esAdministrador == null) {
            this.esAdministrador = false;
        }
    }
}