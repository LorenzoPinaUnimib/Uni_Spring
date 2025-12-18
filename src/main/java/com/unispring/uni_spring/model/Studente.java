package com.unispring.uni_spring.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "studenti")
@PrimaryKeyJoinColumn(name = "persona_id")
public class Studente extends Persona {
    
    @Column(name = "matricola", unique = true, nullable = false)
    private String matricola;
    
    @Column(name = "anno_immatricolazione", nullable = false)
    private Integer annoImmatricolazione;
    
    @Column(name = "semestre_corrente")
    private Integer semestreCorrente = 1;
    
    @Column(name = "crediti_totali")
    private Integer creditiTotali = 0;
    
    @Column(name = "media_voti")
    private Double mediaVoti = 0.0;
    
    // Relazione N:N con Corso tramite Segue
    @OneToMany(mappedBy = "studente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Segue> corsiSeguiti = new HashSet<>();
}