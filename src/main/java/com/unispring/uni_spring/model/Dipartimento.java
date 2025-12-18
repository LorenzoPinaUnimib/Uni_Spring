package com.unispring.uni_spring.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "dipartimenti")
public class Dipartimento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "codice", unique = true, nullable = false)
    private String codice;
    
    @Column(name = "nome", nullable = false)
    private String nome;
    
    @Column(name = "descrizione", length = 1000)
    private String descrizione;
    
    // Relazione 1:N con Corso
    @OneToMany(mappedBy = "dipartimento", cascade = CascadeType.ALL)
    private Set<Corso> corsi = new HashSet<>();
}