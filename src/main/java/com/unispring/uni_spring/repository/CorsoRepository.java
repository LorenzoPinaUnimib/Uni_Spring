package com.unispring.uni_spring.repository;

import com.unispring.uni_spring.model.Corso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CorsoRepository extends JpaRepository<Corso, Long> {
    
    Optional<Corso> findByCodice(String codice);
    
    List<Corso> findByDipartimentoId(Long dipartimentoId);
    List<Corso> findBySemestre(Integer semestre);
    List<Corso> findByCrediti(Integer crediti);
    
    @Query("SELECT c FROM Corso c WHERE c.nome LIKE %:keyword% OR c.descrizione LIKE %:keyword%")
    List<Corso> search(@Param("keyword") String keyword);
    
    @Query("SELECT c FROM Corso c JOIN c.studentiIscritti si JOIN si.studente s WHERE s.id = :studenteId")
    List<Corso> findByStudenteId(@Param("studenteId") Long studenteId);
    
    @Query("SELECT c FROM Corso c JOIN c.docentiInsegnanti di JOIN di.docente d WHERE d.id = :docenteId")
    List<Corso> findByDocenteId(@Param("docenteId") Long docenteId);
    
    @Query("SELECT c FROM Corso c JOIN c.prerequisiti p WHERE p.id = :corsoId")
    List<Corso> findCorsiCheRichiedono(@Param("corsoId") Long corsoId);
    
    @Query("SELECT p FROM Corso c JOIN c.prerequisiti p WHERE c.id = :corsoId")
    List<Corso> findPrerequisitiDiCorso(@Param("corsoId") Long corsoId);
    
    @Query("SELECT COUNT(DISTINCT s.studente) FROM Segue s WHERE s.corso.id = :corsoId")
    long countStudentiIscritti(@Param("corsoId") Long corsoId);
}