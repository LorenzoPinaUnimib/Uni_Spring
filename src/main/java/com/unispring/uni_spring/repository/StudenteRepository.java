package com.unispring.uni_spring.repository;

import com.unispring.uni_spring.model.Studente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudenteRepository extends JpaRepository<Studente, Long> {
    
    Optional<Studente> findByMatricola(String matricola);
    Optional<Studente> findByEmail(String email);
    
    List<Studente> findByAnnoImmatricolazione(Integer anno);
    List<Studente> findBySemestreCorrente(Integer semestre);
    
    @Query("SELECT s FROM Studente s WHERE s.mediaVoti >= :mediaMinima")
    List<Studente> findByMediaVotiGreaterThanEqual(@Param("mediaMinima") Double mediaMinima);
    
    @Query("SELECT s FROM Studente s WHERE s.creditiTotali >= :creditiMinimi")
    List<Studente> findByCreditiTotaliGreaterThanEqual(@Param("creditiMinimi") Integer creditiMinimi);
    
    @Query("SELECT s FROM Studente s WHERE s.nome LIKE %:nome% OR s.cognome LIKE %:nome%")
    List<Studente> findByNomeContaining(@Param("nome") String nome);
    
    @Query("SELECT s FROM Studente s JOIN s.corsiSeguiti cs JOIN cs.corso c WHERE c.id = :corsoId")
    List<Studente> findByCorsoId(@Param("corsoId") Long corsoId);
    
    @Query("SELECT COUNT(s) FROM Studente s")
    long countAll();
}