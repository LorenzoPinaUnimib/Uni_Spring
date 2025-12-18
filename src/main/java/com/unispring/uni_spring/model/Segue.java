package com.unispring.uni_spring.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "segue", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"studente_id", "corso_id"}))
public class Segue {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "data_iscrizione", nullable = false)
    private LocalDate dataIscrizione;
    
    @Column(name = "voto")
    private Double voto;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studente_id", nullable = false)
    private Studente studente;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corso_id", nullable = false)
    private Corso corso;
    
    @PrePersist
    protected void onCreate() {
        dataIscrizione = LocalDate.now();
    }
    
    public Segue() {}
    
    public Segue(Studente studente, Corso corso) {
        this.studente = studente;
        this.corso = corso;
        this.dataIscrizione = LocalDate.now();
    }
}