package com.unispring.uni_spring.repository;

import com.unispring.uni_spring.model.Insegna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InsegnaRepository extends JpaRepository<Insegna, Long> {
    
    Optional<Insegna> findByDocenteMatricolaAndCorsoId(Long docenteMatricola, Long corsoId);
    
    List<Insegna> findByDocenteMatricola(Long docenteMatricola);
    List<Insegna> findByCorsoId(Long corsoId);
    
    @Query("SELECT i FROM Insegna i WHERE i.annoAccademico = :annoAccademico")
    List<Insegna> findByAnnoAccademico(@Param("annoAccademico") String annoAccademico);
}