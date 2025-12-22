package com.unispring.uni_spring.repository;

import com.unispring.uni_spring.model.Segue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SegueRepository extends JpaRepository<Segue, Long> {
    
    Optional<Segue> findByStudenteMatricolaAndCorsoId(Long studenteMatricola, Long corsoId);
    
    List<Segue> findByStudenteMatricola(Long studenteMatricola);
    List<Segue> findByCorsoId(Long corsoId);
    
    @Query("SELECT s FROM Segue s WHERE s.voto IS NOT NULL")
    List<Segue> findAllWithVoto();
    
    @Query("SELECT s FROM Segue s WHERE s.voto >= :votoMinimo")
    List<Segue> findByVotoGreaterThanEqual(@Param("votoMinimo") Double votoMinimo);
    
    @Query("SELECT AVG(s.voto) FROM Segue s WHERE s.corso.id = :corsoId AND s.voto IS NOT NULL")
    Double calculateMediaVotoCorso(@Param("corsoId") Long corsoId);
    
    @Query("SELECT COUNT(s) FROM Segue s WHERE s.corso.id = :corsoId AND s.voto IS NOT NULL")
    long countVotiAssegnati(@Param("corsoId") Long corsoId);
    
    @Query("SELECT COUNT(s) FROM Segue s WHERE s.corso.id = :corsoId AND s.voto >= 18")
    long countPromossi(@Param("corsoId") Long corsoId);
}