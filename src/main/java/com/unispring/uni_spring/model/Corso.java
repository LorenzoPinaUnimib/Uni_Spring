package com.unispring.uni_spring.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "corsi")
public class Corso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "codice", unique = true, nullable = false)
    private String codice;
    
    @Column(name = "nome", nullable = false)
    private String nome;
    
    @Column(name = "descrizione", length = 1000)
    private String descrizione;
    
    @Column(name = "crediti", nullable = false)
    private Integer crediti;
    
    @Column(name = "semestre", nullable = false)
    private Integer semestre;
    
    // Relazione N:1 con Dipartimento
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dipartimento_id")
    private Dipartimento dipartimento;
    
    // Relazione N:N con Studente tramite Segue
    @OneToMany(mappedBy = "corso", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Segue> studentiIscritti = new HashSet<>();
    
    // Relazione N:N con Docente tramite Insegna
    @OneToMany(mappedBy = "corso", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Insegna> docentiInsegnanti = new HashSet<>();
    
    // SELF-RELATION N:N per requisiti (un corso può avere molti prerequisiti)
    @ManyToMany
    @JoinTable(
        name = "requisiti",
        joinColumns = @JoinColumn(name = "corso_id"),
        inverseJoinColumns = @JoinColumn(name = "requisito_id")
    )
    private Set<Corso> prerequisiti = new HashSet<>();
    
    @ManyToMany(mappedBy = "prerequisiti")
    @JsonIgnore
    private Set<Corso> richiestoPer = new HashSet<>();
    
    public Corso() {}
    
    public Corso(String codice, String nome, String descrizione, 
                 Integer crediti, Integer semestre) {
        this.codice = codice;
        this.nome = nome;
        this.descrizione = descrizione;
        this.crediti = crediti;
        this.semestre = semestre;
    }
}