package com.unispring.uni_spring.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "docenti")
@PrimaryKeyJoinColumn(name = "persona_matricola")
public class Docente extends Persona {
    
    public enum GradoAccademico {
        RICERCATORE, PROFESSORE_ASSOCIATO, PROFESSORE_ORDINARIO, EMERITO
    }
    
    @Enumerated(EnumType.STRING)
    @Column(name = "grado_accademico", nullable = false)
    private GradoAccademico gradoAccademico;
    
    @Column(name = "stipendio", nullable = false)
    private BigDecimal stipendio;
    
    @Column(name = "data_assunzione", nullable = false)
    private LocalDate dataAssunzione;
    
    @Column(name = "ufficio")
    private String ufficio;
    
    @Column(name = "specializzazione")
    private String specializzazione;
    
    // Relazione N:N con Corso tramite Insegna
    @OneToMany(mappedBy = "docente", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Insegna> corsiInsegnati = new HashSet<>();
}