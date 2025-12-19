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
     @Query("SELECT DISTINCT s FROM Studente s " +
           "JOIN s.corsiSeguiti cs " +
           "JOIN cs.corso c " +
           "JOIN c.dipartimento d " +
           "WHERE d.nome = :nomeDipartimento")
    List<Studente> findStudentiByCorsiDipartimento(@Param("nomeDipartimento") String nomeDipartimento);
    
    /**
     * Trova studenti che seguono corsi di un dipartimento con filtri aggiuntivi
     */
    @Query("SELECT DISTINCT s FROM Studente s " +
           "JOIN s.corsiSeguiti cs " +
           "JOIN cs.corso c " +
           "JOIN c.dipartimento d " +
           "WHERE d.nome = :nomeDipartimento " +
           "AND (:nomeStudente IS NULL OR (s.nome LIKE %:nomeStudente% OR s.cognome LIKE %:nomeStudente%)) " +
           "AND (:minCrediti IS NULL OR s.creditiTotali >= :minCrediti) " +
           "AND (:minMedia IS NULL OR s.mediaVoti >= :minMedia)")
    List<Studente> findStudentiByCorsiDipartimentoConFiltri(
            @Param("nomeDipartimento") String nomeDipartimento,
            @Param("nomeStudente") String nomeStudente,
            @Param("minCrediti") Integer minCrediti,
            @Param("minMedia") Double minMedia);
    
    /**
     * Conta studenti che seguono corsi di un dipartimento
     */
    @Query("SELECT COUNT(DISTINCT s) FROM Studente s " +
           "JOIN s.corsiSeguiti cs " +
           "JOIN cs.corso c " +
           "JOIN c.dipartimento d " +
           "WHERE d.nome = :nomeDipartimento")
    Long countStudentiByCorsiDipartimento(@Param("nomeDipartimento") String nomeDipartimento);
}