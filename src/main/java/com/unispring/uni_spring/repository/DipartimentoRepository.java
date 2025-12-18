package com.unispring.uni_spring.repository;

import com.unispring.uni_spring.model.Dipartimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DipartimentoRepository extends JpaRepository<Dipartimento, Long> {
    
    Optional<Dipartimento> findByCodice(String codice);
    Optional<Dipartimento> findByNome(String nome);
    
    @Query("SELECT d FROM Dipartimento d WHERE d.nome LIKE %:keyword% OR d.descrizione LIKE %:keyword%")
    List<Dipartimento> search(@Param("keyword") String keyword);
}