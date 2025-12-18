package com.unispring.uni_spring.service;

import com.unispring.uni_spring.model.Docente;
import com.unispring.uni_spring.repository.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DocenteService {
    
    @Autowired
    private DocenteRepository docenteRepository;
    
    // CRUD Operations
    public Docente createDocente(Docente docente) {
        validateDocente(docente);
        return docenteRepository.save(docente);
    }
    
    public Optional<Docente> findById(Long id) {
        return docenteRepository.findById(id);
    }
    
    public List<Docente> findAll() {
        return docenteRepository.findAll();
    }
    
    public Docente updateDocente(Long id, Docente docenteDetails) {
        Docente docente = getDocenteOrThrow(id);
        updateDocenteFields(docente, docenteDetails);
        return docenteRepository.save(docente);
    }
    
    public void deleteDocente(Long id) {
        if (!docenteRepository.existsById(id)) {
            throw new RuntimeException("Docente non trovato con id: " + id);
        }
        docenteRepository.deleteById(id);
    }
    
    // Business Logic
    public Docente updateStipendio(Long docenteId, BigDecimal nuovoStipendio) {
        Docente docente = getDocenteOrThrow(docenteId);
        if (nuovoStipendio.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Stipendio deve essere positivo");
        }
        docente.setStipendio(nuovoStipendio);
        return docenteRepository.save(docente);
    }
    
    public Docente promuoviDocente(Long docenteId, Docente.GradoAccademico nuovoGrado) {
        Docente docente = getDocenteOrThrow(docenteId);
        docente.setGradoAccademico(nuovoGrado);
        return docenteRepository.save(docente);
    }
    
    public boolean canInsegnareCorso(Long docenteId, String materiaCorso) {
        Docente docente = getDocenteOrThrow(docenteId);
        return docente.getSpecializzazione() != null && 
               docente.getSpecializzazione().toLowerCase().contains(materiaCorso.toLowerCase());
    }
    
    // Ricerche
    public List<Docente> findByNome(String nome) {
        return docenteRepository.findByNomeContaining(nome);
    }
    
    public List<Docente> findByGradoAccademico(Docente.GradoAccademico grado) {
        return docenteRepository.findByGradoAccademico(grado);
    }
    
    public List<Docente> findBySpecializzazione(String specializzazione) {
        return docenteRepository.findBySpecializzazione(specializzazione);
    }
    
    public List<Docente> findByCorso(Long corsoId) {
        return docenteRepository.findByCorsoId(corsoId);
    }
    
    // Statistiche
    public long countAll() {
        return docenteRepository.count();
    }
    
    public BigDecimal calculateCostoTotaleStipendi() {
        return docenteRepository.findAll().stream()
                .map(Docente::getStipendio)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    // Private Helper Methods
    private void validateDocente(Docente docente) {
        if (docente.getCodiceDocente() == null || docente.getCodiceDocente().isEmpty()) {
            throw new RuntimeException("Codice docente è obbligatorio");
        }
        if (docente.getStipendio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Stipendio deve essere positivo");
        }
    }
    
    private Docente getDocenteOrThrow(Long id) {
        return docenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente non trovato con id: " + id));
    }
    
    private void updateDocenteFields(Docente docente, Docente docenteDetails) {
        if (docenteDetails.getNome() != null) {
            docente.setNome(docenteDetails.getNome());
        }
        if (docenteDetails.getCognome() != null) {
            docente.setCognome(docenteDetails.getCognome());
        }
        if (docenteDetails.getTelefono() != null) {
            docente.setTelefono(docenteDetails.getTelefono());
        }
        if (docenteDetails.getStipendio() != null) {
            docente.setStipendio(docenteDetails.getStipendio());
        }
        if (docenteDetails.getUfficio() != null) {
            docente.setUfficio(docenteDetails.getUfficio());
        }
        if (docenteDetails.getSpecializzazione() != null) {
            docente.setSpecializzazione(docenteDetails.getSpecializzazione());
        }
    }
}