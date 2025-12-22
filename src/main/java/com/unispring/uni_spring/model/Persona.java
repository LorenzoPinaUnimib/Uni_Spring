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
    @Column(name = "matricola", nullable = false, unique = true)
    private Long matricola;
    
    @Column(name = "nome", nullable = false)
    private String nome;
    
    @Column(name = "cognome", nullable = false)
    private String cognome;
    
    @Column(name = "data_nascita", nullable = false)
    private LocalDate dataNascita;
    
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    
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