package com.unispring.uni_spring.service;

import com.unispring.uni_spring.model.Insegna;
import com.unispring.uni_spring.model.Docente;
import com.unispring.uni_spring.model.Corso;
import com.unispring.uni_spring.repository.InsegnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InsegnaService {
    
    @Autowired
    private InsegnaRepository insegnaRepository;
    
    // CRUD Operations
    public Insegna assegnaCorsoADocente(Docente docente, Corso corso, String annoAccademico) {
        validateAssegnazione(docente, corso);
        
        Insegna insegna = new Insegna();
        insegna.setDocente(docente);
        insegna.setCorso(corso);
        insegna.setAnnoAccademico(annoAccademico);
        
        return insegnaRepository.save(insegna);
    }
    
    public Optional<Insegna> findById(Long id) {
        return insegnaRepository.findById(id);
    }
    
    public List<Insegna> findByDocenteMatricola(Long docenteId) {
        return insegnaRepository.findByDocenteMatricola(docenteId);
    }
    
    public List<Insegna> findByCorsoId(Long corsoId) {
        return insegnaRepository.findByCorsoId(corsoId);
    }
    
    public Insegna updateAnnoAccademico(Long insegnaId, String nuovoAnno) {
        Insegna insegna = getInsegnaOrThrow(insegnaId);
        insegna.setAnnoAccademico(nuovoAnno);
        return insegnaRepository.save(insegna);
    }
    
    public void deleteInsegna(Long id) {
        if (!insegnaRepository.existsById(id)) {
            throw new RuntimeException("Assegnazione non trovata con id: " + id);
        }
        insegnaRepository.deleteById(id);
    }
    
    // Ricerche
    public List<Insegna> findByAnnoAccademico(String annoAccademico) {
        return insegnaRepository.findByAnnoAccademico(annoAccademico);
    }
    
    // Statistiche
    public long countAll() {
        return insegnaRepository.count();
    }
    
    public int countCorsiDocente(Long docenteMatricola) {
        return insegnaRepository.findByDocenteMatricola(docenteMatricola).size();
    }
    
    public int countDocentiCorso(Long corsoId) {
        return insegnaRepository.findByCorsoId(corsoId).size();
    }
    
    // Private Helper Methods
    private void validateAssegnazione(Docente docente, Corso corso) {
        // Controlla se già assegnato
        Optional<Insegna> existing = insegnaRepository
                .findByDocenteMatricolaAndCorsoId(docente.getMatricola(), corso.getId());
        
        if (existing.isPresent()) {
            throw new RuntimeException("Docente già assegnato a questo corso");
        }
    }
    
    private Insegna getInsegnaOrThrow(Long id) {
        return insegnaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assegnazione non trovata con id: " + id));
    }
}