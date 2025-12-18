package com.unispring.uni_spring.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "insegna", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"docente_id", "corso_id"}))
public class Insegna {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "anno_accademico")
    private String annoAccademico;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docente_id", nullable = false)
    private Docente docente;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corso_id", nullable = false)
    private Corso corso;
    
    public Insegna() {}
    
    public Insegna(Docente docente, Corso corso, String annoAccademico) {
        this.docente = docente;
        this.corso = corso;
        this.annoAccademico = annoAccademico;
    }
}