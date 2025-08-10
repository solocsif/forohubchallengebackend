package com.javier.forohubchallengebackend.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "usuarios") // Opcional: define el nombre explícito
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre; // Este será el username

    @Column(unique = true, nullable = false)
    private String correoElectronico;

    @Column(nullable = false)
    private String contrasena;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "usuario_perfiles",
            joinColumns = @JoinColumn(name = "usuario_id")
    )
    @Column(name = "perfil")
    private List<String> perfiles;


}