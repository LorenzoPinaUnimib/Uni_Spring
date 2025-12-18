package com.unispring.uni_spring.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "persone")
public abstract class Persona {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nome", nullable = false)
    private String nome;
    
    @Column(name = "cognome", nullable = false)
    private String cognome;
    
    @Column(name = "data_nascita", nullable = false)
    private LocalDate dataNascita;
    
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    
    @Column(name = "telefono", nullable = false)
    private String telefono;
    
    @Column(name = "data_registrazione")
    private LocalDate dataRegistrazione;
    
    @PrePersist
    protected void onCreate() {
        dataRegistrazione = LocalDate.now();
    }
    
    public String getNomeCompleto() {
        return nome + " " + cognome;
    }
}