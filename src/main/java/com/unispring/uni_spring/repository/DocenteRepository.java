package com.unispring.uni_spring.repository;

import com.unispring.uni_spring.model.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {
    
    Optional<Docente> findByMatricola(Long matricola);
    Optional<Docente> findByEmail(String email);
    
    List<Docente> findByGradoAccademico(Docente.GradoAccademico grado);
    
    @Query("SELECT d FROM Docente d WHERE d.nome LIKE %:nome% OR d.cognome LIKE %:nome%")
    List<Docente> findByNomeContaining(@Param("nome") String nome);
    
    @Query("SELECT d FROM Docente d JOIN d.corsiInsegnati ci JOIN ci.corso c WHERE c.id = :corsoId")
    List<Docente> findByCorsoId(@Param("corsoId") Long corsoId);
    
    @Query("SELECT d FROM Docente d WHERE d.specializzazione LIKE %:specializzazione%")
    List<Docente> findBySpecializzazione(@Param("specializzazione") String specializzazione);
}