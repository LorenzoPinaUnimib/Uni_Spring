package com.unispring.uni_spring.service;

import com.unispring.uni_spring.model.Segue;
import com.unispring.uni_spring.model.Studente;
import com.unispring.uni_spring.repository.SegueRepository;
import com.unispring.uni_spring.repository.StudenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudenteService {
    
    @Autowired
    private StudenteRepository studenteRepository;
    
    @Autowired
    private SegueRepository segueRepository;
    
    // CRUD Operations
    public Studente createStudente(Studente studente) {
        validateStudente(studente);
        return studenteRepository.save(studente);
    }
    
    public Optional<Studente> findById(Long id) {
        return studenteRepository.findById(id);
    }
    
    public Optional<Studente> findByMatricola(String matricola) {
        return studenteRepository.findByMatricola(matricola);
    }
    
    public List<Studente> findAll() {
        return studenteRepository.findAll();
    }
    
    public Studente updateStudente(Long id, Studente studenteDetails) {
        Studente studente = getStudenteOrThrow(id);
        updateStudenteFields(studente, studenteDetails);
        return studenteRepository.save(studente);
    }
    
    public void deleteStudente(Long id) {
        if (!studenteRepository.existsById(id)) {
            throw new RuntimeException("Studente non trovato con id: " + id);
        }
        studenteRepository.deleteById(id);
    }
    
    // Business Logic
    public boolean canIscriversiACorso(Long studenteId, Integer creditiCorso) {
        Studente studente = getStudenteOrThrow(studenteId);
        return studente.getCreditiTotali() + creditiCorso <= 180; // Limite laurea
    }
    
    public Studente updateMediaVoti(Long studenteId, Double nuovaMedia) {
        Studente studente = getStudenteOrThrow(studenteId);
        if (nuovaMedia < 0 || nuovaMedia > 30) {
            throw new RuntimeException("Media voti deve essere tra 0 e 30");
        }
        studente.setMediaVoti(nuovaMedia);
        return studenteRepository.save(studente);
    }
    
    public Studente addCrediti(Long studenteId, Integer crediti) {
        Studente studente = getStudenteOrThrow(studenteId);
        studente.setCreditiTotali(studente.getCreditiTotali() + crediti);
        return studenteRepository.save(studente);
    }
    
    public Studente promuoviSemestre(Long studenteId) {
        Studente studente = getStudenteOrThrow(studenteId);
        if (studente.getSemestreCorrente() < 12) { // Massimo 12 semestri
            studente.setSemestreCorrente(studente.getSemestreCorrente() + 1);
        }
        return studenteRepository.save(studente);
    }
    
    // Ricerche
    public List<Studente> findByNome(String nome) {
        return studenteRepository.findByNomeContaining(nome);
    }
    
    public List<Studente> findByAnnoImmatricolazione(Integer anno) {
        return studenteRepository.findByAnnoImmatricolazione(anno);
    }
    
    public List<Studente> findByMediaVotiGreaterThanEqual(Double media) {
        return studenteRepository.findByMediaVotiGreaterThanEqual(media);
    }
    
    public List<Studente> findByCreditiGreaterThanEqual(Integer crediti) {
        return studenteRepository.findByCreditiTotaliGreaterThanEqual(crediti);
    }
    
    public List<Studente> findByCorso(Long corsoId) {
        return studenteRepository.findByCorsoId(corsoId);
    }
    
    // Statistiche
    public long countAll() {
        return studenteRepository.countAll();
    }
    
    public Double getMediaVotiUniversita() {
        List<Studente> studenti = studenteRepository.findAll();
        return studenti.stream()
                .mapToDouble(Studente::getMediaVoti)
                .average()
                .orElse(0.0);
    }
    
    public List<Studente> getTopStudenti(int limit) {
        return studenteRepository.findAll().stream()
                .sorted((s1, s2) -> Double.compare(s2.getMediaVoti(), s1.getMediaVoti()))
                .limit(limit)
                .toList();
    }
    
    // Private Helper Methods
    private void validateStudente(Studente studente) {
        if (studente.getMatricola() == null || studente.getMatricola().isEmpty()) {
            throw new RuntimeException("Matricola è obbligatoria");
        }
        if (studente.getAnnoImmatricolazione() < 2000 || studente.getAnnoImmatricolazione() > 2100) {
            throw new RuntimeException("Anno immatricolazione non valido");
        }
    }
    
    private Studente getStudenteOrThrow(Long id) {
        return studenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Studente non trovato con id: " + id));
    }
    
    private void updateStudenteFields(Studente studente, Studente studenteDetails) {
        if (studenteDetails.getNome() != null) {
            studente.setNome(studenteDetails.getNome());
        }
        if (studenteDetails.getCognome() != null) {
            studente.setCognome(studenteDetails.getCognome());
        }
        if (studenteDetails.getSemestreCorrente() != null) {
            studente.setSemestreCorrente(studenteDetails.getSemestreCorrente());
        }
    }
    /**
     * Trova studenti che seguono corsi di un determinato dipartimento
     */
    public List<Studente> getStudentiByCorsiDipartimento(String nomeDipartimento) {
        return studenteRepository.findStudentiByCorsiDipartimento(nomeDipartimento);
    }
    
    /**
     * Trova studenti che seguono corsi di un dipartimento con filtri avanzati
     */
    public List<Studente> getStudentiByCorsiDipartimentoConFiltri(
            String nomeDipartimento, 
            String nomeStudente, 
            Integer minCrediti, 
            Double minMedia) {
        return studenteRepository.findStudentiByCorsiDipartimentoConFiltri(
            nomeDipartimento, 
            nomeStudente, 
            minCrediti, 
            minMedia
        );
    }
    
    /**
     * Conta studenti che seguono corsi di un dipartimento
     */
    public Long countStudentiByCorsiDipartimento(String nomeDipartimento) {
        return studenteRepository.countStudentiByCorsiDipartimento(nomeDipartimento);
    }
}