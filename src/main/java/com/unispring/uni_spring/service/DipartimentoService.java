package com.unispring.uni_spring.service;

import com.unispring.uni_spring.model.Dipartimento;
import com.unispring.uni_spring.repository.DipartimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DipartimentoService {
    
    @Autowired
    private DipartimentoRepository dipartimentoRepository;
    
    // CRUD Operations
    public Dipartimento createDipartimento(Dipartimento dipartimento) {
        validateDipartimento(dipartimento);
        return dipartimentoRepository.save(dipartimento);
    }
    
    public Optional<Dipartimento> findById(Long id) {
        return dipartimentoRepository.findById(id);
    }
    
    public List<Dipartimento> findAll() {
        return dipartimentoRepository.findAll();
    }
    
    public Dipartimento updateDipartimento(Long id, Dipartimento dipartimentoDetails) {
        Dipartimento dipartimento = getDipartimentoOrThrow(id);
        updateDipartimentoFields(dipartimento, dipartimentoDetails);
        return dipartimentoRepository.save(dipartimento);
    }
    
    public void deleteDipartimento(Long id) {
        if (!dipartimentoRepository.existsById(id)) {
            throw new RuntimeException("Dipartimento non trovato con id: " + id);
        }
        dipartimentoRepository.deleteById(id);
    }
    
    // Ricerche
    public Optional<Dipartimento> findByCodice(String codice) {
        return dipartimentoRepository.findByCodice(codice);
    }
    
    public List<Dipartimento> search(String keyword) {
        return dipartimentoRepository.search(keyword);
    }
    
    // Statistiche
    public long countAll() {
        return dipartimentoRepository.count();
    }
    
    // Private Helper Methods
    private void validateDipartimento(Dipartimento dipartimento) {
        if (dipartimento.getCodice() == null || dipartimento.getCodice().isEmpty()) {
            throw new RuntimeException("Codice dipartimento è obbligatorio");
        }
        if (dipartimento.getNome() == null || dipartimento.getNome().isEmpty()) {
            throw new RuntimeException("Nome dipartimento è obbligatorio");
        }
    }
    
    private Dipartimento getDipartimentoOrThrow(Long id) {
        return dipartimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dipartimento non trovato con id: " + id));
    }
    
    private void updateDipartimentoFields(Dipartimento dipartimento, Dipartimento dipartimentoDetails) {
        if (dipartimentoDetails.getNome() != null) {
            dipartimento.setNome(dipartimentoDetails.getNome());
        }
        if (dipartimentoDetails.getDescrizione() != null) {
            dipartimento.setDescrizione(dipartimentoDetails.getDescrizione());
        }
    }
}