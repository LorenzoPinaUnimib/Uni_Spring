package com.unispring.uni_spring.service;

import com.unispring.uni_spring.model.Corso;
import com.unispring.uni_spring.repository.CorsoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CorsoService {
    
    @Autowired
    private CorsoRepository corsoRepository;
    
    // CRUD Operations
    public Corso createCorso(Corso corso) {
        validateCorso(corso);
        return corsoRepository.save(corso);
    }
    
    public Optional<Corso> findById(Long id) {
        return corsoRepository.findById(id);
    }
    
    public List<Corso> findAll() {
        return corsoRepository.findAll();
    }
    
    public Corso updateCorso(Long id, Corso corsoDetails) {
        Corso corso = getCorsoOrThrow(id);
        updateCorsoFields(corso, corsoDetails);
        return corsoRepository.save(corso);
    }
    
    public void deleteCorso(Long id) {
        if (!corsoRepository.existsById(id)) {
            throw new RuntimeException("Corso non trovato con id: " + id);
        }
        corsoRepository.deleteById(id);
    }
    
    // Business Logic - Gestione Requisiti
    public Corso addPrerequisito(Long corsoId, Long prerequisitoId) {
        Corso corso = getCorsoOrThrow(corsoId);
        Corso prerequisito = getCorsoOrThrow(prerequisitoId);
        
        // Evita loop
        if (corso.getId().equals(prerequisitoId)) {
            throw new RuntimeException("Un corso non può essere prerequisito di se stesso");
        }
        
        // Controlla prerequisiti circolari
        if (hasCircularDependency(corso, prerequisito)) {
            throw new RuntimeException("Dipendenza circolare rilevata");
        }
        
        corso.getPrerequisiti().add(prerequisito);
        return corsoRepository.save(corso);
    }
    
    public Corso removePrerequisito(Long corsoId, Long prerequisitoId) {
        Corso corso = getCorsoOrThrow(corsoId);
        corso.getPrerequisiti().removeIf(p -> p.getId().equals(prerequisitoId));
        return corsoRepository.save(corso);
    }
    
    // Business Logic
    public boolean isCorsoPieno(Long corsoId) {
        Corso corso = getCorsoOrThrow(corsoId);
        // Esempio: massimo 100 studenti per corso
        return corso.getStudentiIscritti() != null && corso.getStudentiIscritti().size() >= 100;
    }
    
    public boolean hasPrerequisitiSoddisfatti(Long corsoId, Long studenteId) {
        // Implementazione semplificata
        // In realtà dovresti controllare se lo studente ha superato tutti i prerequisiti
        return true;
    }
    
    // Ricerche
    public List<Corso> findByDipartimento(Long dipartimentoId) {
        return corsoRepository.findByDipartimentoId(dipartimentoId);
    }
    
    public List<Corso> findBySemestre(Integer semestre) {
        return corsoRepository.findBySemestre(semestre);
    }
    
    public List<Corso> findByCrediti(Integer crediti) {
        return corsoRepository.findByCrediti(crediti);
    }
    
    public List<Corso> search(String keyword) {
        return corsoRepository.search(keyword);
    }
    
    public List<Corso> findByStudente(Long studenteId) {
        return corsoRepository.findByStudenteId(studenteId);
    }
    
    public List<Corso> findByDocente(Long docenteId) {
        return corsoRepository.findByDocenteId(docenteId);
    }
    
    public List<Corso> getPrerequisiti(Long corsoId) {
        return corsoRepository.findPrerequisitiDiCorso(corsoId);
    }
    
    public List<Corso> getCorsiCheRichiedono(Long corsoId) {
        return corsoRepository.findCorsiCheRichiedono(corsoId);
    }
    
    // Statistiche
    public long countAll() {
        return corsoRepository.count();
    }
    
    public long countStudentiIscritti(Long corsoId) {
        return corsoRepository.countStudentiIscritti(corsoId);
    }
    
    // Private Helper Methods
    private void validateCorso(Corso corso) {
        if (corso.getCodice() == null || corso.getCodice().isEmpty()) {
            throw new RuntimeException("Codice corso è obbligatorio");
        }
        if (corso.getNome() == null || corso.getNome().isEmpty()) {
            throw new RuntimeException("Nome corso è obbligatorio");
        }
        if (corso.getCrediti() == null || corso.getCrediti() < 1 || corso.getCrediti() > 12) {
            throw new RuntimeException("Crediti devono essere tra 1 e 12");
        }
        if (corso.getSemestre() == null || corso.getSemestre() < 1 || corso.getSemestre() > 2) {
            throw new RuntimeException("Semestre deve essere 1 o 2");
        }
    }
    
    private Corso getCorsoOrThrow(Long id) {
        return corsoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Corso non trovato con id: " + id));
    }
    
    private void updateCorsoFields(Corso corso, Corso corsoDetails) {
        if (corsoDetails.getNome() != null) {
            corso.setNome(corsoDetails.getNome());
        }
        if (corsoDetails.getDescrizione() != null) {
            corso.setDescrizione(corsoDetails.getDescrizione());
        }
        if (corsoDetails.getCrediti() != null) {
            corso.setCrediti(corsoDetails.getCrediti());
        }
        if (corsoDetails.getSemestre() != null) {
            corso.setSemestre(corsoDetails.getSemestre());
        }
    }
    
    private boolean hasCircularDependency(Corso corso, Corso prerequisito) {
        // DFS per rilevare dipendenze circolari
        return checkDependency(prerequisito, corso.getId(), new java.util.HashSet<>());
    }
    
    private boolean checkDependency(Corso current, Long targetId, java.util.Set<Long> visited) {
        if (visited.contains(current.getId())) {
            return false;
        }
        visited.add(current.getId());
        
        if (current.getId().equals(targetId)) {
            return true;
        }
        
        for (Corso prereq : current.getPrerequisiti()) {
            if (checkDependency(prereq, targetId, visited)) {
                return true;
            }
        }
        
        return false;
    }
}