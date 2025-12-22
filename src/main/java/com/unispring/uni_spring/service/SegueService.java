package com.unispring.uni_spring.service;

import com.unispring.uni_spring.model.Segue;
import com.unispring.uni_spring.model.Studente;
import com.unispring.uni_spring.model.Corso;
import com.unispring.uni_spring.repository.SegueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SegueService {
    
    @Autowired
    private SegueRepository segueRepository;
    
    // CRUD Operations
    public Segue iscriviStudente(Studente studente, Corso corso) {
        validateIscrizione(studente, corso);
        
        Segue segue = new Segue();
        segue.setStudente(studente);
        segue.setCorso(corso);
        segue.setDataIscrizione(LocalDate.now());
        
        return segueRepository.save(segue);
    }
    
    public Optional<Segue> findById(Long id) {
        return segueRepository.findById(id);
    }
    
    public List<Segue> findByStudenteMatricola(Long studenteMatricola) {
        return segueRepository.findByStudenteMatricola(studenteMatricola);
    }
    
    public List<Segue> findByCorsoId(Long corsoId) {
        return segueRepository.findByCorsoId(corsoId);
    }
    
    public Segue assegnaVoto(Long segueId, Double voto) {
        Segue segue = getSegueOrThrow(segueId);
        validateVoto(voto);
        segue.setVoto(voto);
        return segueRepository.save(segue);
    }
    
    public void deleteSegue(Long id) {
        if (!segueRepository.existsById(id)) {
            throw new RuntimeException("Iscrizione non trovata con id: " + id);
        }
        segueRepository.deleteById(id);
    }
    
    // Business Logic
    public boolean hasSuperatoCorso(Long studenteMatricola, Long corsoId) {
        Optional<Segue> segue = segueRepository.findByStudenteMatricolaAndCorsoId(studenteMatricola, corsoId);
        return segue.isPresent() && 
               segue.get().getVoto() != null && 
               segue.get().getVoto() >= 18.0;
    }
    
    public boolean puoSostenereEsame(Long segueId) {
        Segue segue = getSegueOrThrow(segueId);
        // Esempio: almeno 70% di presenza richiesta
        return true;
    }
    
    // Ricerche
    public List<Segue> findAllWithVoto() {
        return segueRepository.findAllWithVoto();
    }
    
    public List<Segue> findByVotoGreaterThanEqual(Double votoMinimo) {
        return segueRepository.findByVotoGreaterThanEqual(votoMinimo);
    }
    
    // Statistiche
    public long countAll() {
        return segueRepository.count();
    }
    
    public Double calculateMediaVotoCorso(Long corsoId) {
        Double media = segueRepository.calculateMediaVotoCorso(corsoId);
        return media != null ? media : 0.0;
    }
    
    public Double calculateMediaVotoStudente(Long studenteMatricola) {
        List<Segue> iscrizioni = segueRepository.findByStudenteMatricola(studenteMatricola);
        
        return iscrizioni.stream()
                .filter(s -> s.getVoto() != null)
                .mapToDouble(Segue::getVoto)
                .average()
                .orElse(0.0);
    }
    
    public long countPromossi(Long corsoId) {
        return segueRepository.countPromossi(corsoId);
    }
    
    public long countVotiAssegnati(Long corsoId) {
        return segueRepository.countVotiAssegnati(corsoId);
    }
    
    // Private Helper Methods
    private void validateIscrizione(Studente studente, Corso corso) {
        // Controlla se già iscritto
        Optional<Segue> existing = segueRepository
                .findByStudenteMatricolaAndCorsoId(studente.getMatricola(), corso.getId());
        
        if (existing.isPresent()) {
            throw new RuntimeException("Studente già iscritto a questo corso");
        }
    }
    
    private void validateVoto(Double voto) {
        if (voto < 0 || voto > 30) {
            throw new RuntimeException("Voto deve essere tra 0 e 30");
        }
    }
    
    private Segue getSegueOrThrow(Long id) {
        return segueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Iscrizione non trovata con id: " + id));
    }
}