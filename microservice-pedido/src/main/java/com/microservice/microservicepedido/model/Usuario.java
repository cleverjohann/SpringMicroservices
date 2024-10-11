package com.microservice.microservicepedido.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuarios_id_gen")
    @SequenceGenerator(name = "usuarios_id_gen", sequenceName = "usuarios_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "nombres", nullable = false, length = 50)
    private String nombres;

    @Size(max = 50)
    @NotNull
    @Column(name = "apellidop", nullable = false, length = 50)
    private String apellidop;

    @Size(max = 50)
    @NotNull
    @Column(name = "apellidom", nullable = false, length = 50)
    private String apellidom;

    @Size(max = 50)
    @NotNull
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Size(max = 50)
    @NotNull
    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @ColumnDefault("false")
    @Column(name = "cuenta_verificada")
    private Boolean cuentaVerificada;

}