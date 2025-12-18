package com.unispring.uni_spring.controller;

import com.unispring.uni_spring.model.*;
import com.unispring.uni_spring.service.UniversityFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/university")
@CrossOrigin(origins = "*")
public class UniversityController {

    @Autowired
    private UniversityFacade universityFacade;

    // ========== STUDENTI ==========

    @PostMapping("/studenti")
    public ResponseEntity<Studente> createStudente(@RequestBody StudenteRequest request) {
        try {
            Studente studente = universityFacade.createStudente(
                request.getNome(),
                request.getCognome(),
                request.getDataNascita(),
                request.getEmail(),
                request.getTelefono(),
                request.getMatricola(),
                request.getAnnoImmatricolazione()
            );
            return new ResponseEntity<>(studente, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/studenti/{id}")
    public ResponseEntity<Studente> getStudenteById(@PathVariable Long id) {
        return universityFacade.getStudenteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/studenti/matricola/{matricola}")
    public ResponseEntity<Studente> getStudenteByMatricola(@PathVariable String matricola) {
        return universityFacade.getStudenteByMatricola(matricola)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/studenti")
    public ResponseEntity<List<Studente>> getAllStudenti() {
        List<Studente> studenti = universityFacade.getAllStudenti();
        return ResponseEntity.ok(studenti);
    }

    @PutMapping("/studenti/{id}")
    public ResponseEntity<Studente> updateStudente(@PathVariable Long id, 
                                                  @RequestBody StudenteUpdateRequest request) {
        try {
            Studente updated = universityFacade.updateStudente(
                id,
                request.getNome(),
                request.getCognome(),
                request.getTelefono(),
                request.getSemestreCorrente()
            );
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/studenti/{id}")
    public ResponseEntity<Void> deleteStudente(@PathVariable Long id) {
        if (!universityFacade.studenteEsiste(id)) {
            return ResponseEntity.notFound().build();
        }
        universityFacade.deleteStudente(id);
        return ResponseEntity.noContent().build();
    }

    // ========== DOCENTI ==========

    @PostMapping("/docenti")
    public ResponseEntity<Docente> createDocente(@RequestBody DocenteRequest request) {
        try {
            Docente docente = universityFacade.createDocente(
                request.getNome(),
                request.getCognome(),
                request.getDataNascita(),
                request.getEmail(),
                request.getTelefono(),
                request.getCodiceDocente(),
                request.getGradoAccademico(),
                request.getStipendio(),
                request.getDataAssunzione(),
                request.getUfficio(),
                request.getSpecializzazione()
            );
            return new ResponseEntity<>(docente, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/docenti/{id}")
    public ResponseEntity<Docente> getDocenteById(@PathVariable Long id) {
        return universityFacade.getDocenteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/docenti")
    public ResponseEntity<List<Docente>> getAllDocenti() {
        List<Docente> docenti = universityFacade.getAllDocenti();
        return ResponseEntity.ok(docenti);
    }

    @PutMapping("/docenti/{id}")
    public ResponseEntity<Docente> updateDocente(@PathVariable Long id,
                                                @RequestBody DocenteUpdateRequest request) {
        try {
            Docente updated = universityFacade.updateDocente(
                id,
                request.getNome(),
                request.getCognome(),
                request.getTelefono(),
                request.getStipendio(),
                request.getUfficio(),
                request.getSpecializzazione()
            );
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/docenti/{id}")
    public ResponseEntity<Void> deleteDocente(@PathVariable Long id) {
        if (!universityFacade.docenteEsiste(id)) {
            return ResponseEntity.notFound().build();
        }
        universityFacade.deleteDocente(id);
        return ResponseEntity.noContent().build();
    }

    // ========== DIPARTIMENTI ==========

    @PostMapping("/dipartimenti")
    public ResponseEntity<Dipartimento> createDipartimento(@RequestBody DipartimentoRequest request) {
        try {
            Dipartimento dipartimento = universityFacade.createDipartimento(
                request.getCodice(),
                request.getNome(),
                request.getDescrizione()
            );
            return new ResponseEntity<>(dipartimento, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/dipartimenti/{id}")
    public ResponseEntity<Dipartimento> getDipartimentoById(@PathVariable Long id) {
        return universityFacade.getDipartimentoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/dipartimenti")
    public ResponseEntity<List<Dipartimento>> getAllDipartimenti() {
        List<Dipartimento> dipartimenti = universityFacade.getAllDipartimenti();
        return ResponseEntity.ok(dipartimenti);
    }

    @PutMapping("/dipartimenti/{id}")
    public ResponseEntity<Dipartimento> updateDipartimento(@PathVariable Long id,
                                                          @RequestBody DipartimentoUpdateRequest request) {
        try {
            Dipartimento updated = universityFacade.updateDipartimento(
                id,
                request.getNome(),
                request.getDescrizione()
            );
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/dipartimenti/{id}")
    public ResponseEntity<Void> deleteDipartimento(@PathVariable Long id) {
        if (!universityFacade.dipartimentoEsiste(id)) {
            return ResponseEntity.notFound().build();
        }
        universityFacade.deleteDipartimento(id);
        return ResponseEntity.noContent().build();
    }

    // ========== CORSI ==========

    @PostMapping("/corsi")
    public ResponseEntity<Corso> createCorso(@RequestBody CorsoRequest request) {
        try {
            Corso corso = universityFacade.createCorso(
                request.getCodice(),
                request.getNome(),
                request.getDescrizione(),
                request.getCrediti(),
                request.getSemestre(),
                request.getDipartimentoId()
            );
            return new ResponseEntity<>(corso, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/corsi/{id}")
    public ResponseEntity<Corso> getCorsoById(@PathVariable Long id) {
        return universityFacade.getCorsoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/corsi")
    public ResponseEntity<List<Corso>> getAllCorsi() {
        List<Corso> corsi = universityFacade.getAllCorsi();
        return ResponseEntity.ok(corsi);
    }

    @PutMapping("/corsi/{id}")
    public ResponseEntity<Corso> updateCorso(@PathVariable Long id,
                                            @RequestBody CorsoUpdateRequest request) {
        try {
            Corso updated = universityFacade.updateCorso(
                id,
                request.getNome(),
                request.getDescrizione(),
                request.getCrediti(),
                request.getSemestre()
            );
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/corsi/{id}")
    public ResponseEntity<Void> deleteCorso(@PathVariable Long id) {
        if (!universityFacade.corsoEsiste(id)) {
            return ResponseEntity.notFound().build();
        }
        universityFacade.deleteCorso(id);
        return ResponseEntity.noContent().build();
    }

    // ========== ISCRIZIONI (SEGUE) ==========

    @PostMapping("/iscrizioni")
    public ResponseEntity<Segue> iscriviStudenteACorso(@RequestBody IscrizioneRequest request) {
        try {
            Segue iscrizione = universityFacade.iscriviStudenteACorso(
                request.getStudenteId(),
                request.getCorsoId()
            );
            return new ResponseEntity<>(iscrizione, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/iscrizioni/studente/{studenteId}")
    public ResponseEntity<List<Segue>> getIscrizioniStudente(@PathVariable Long studenteId) {
        List<Segue> iscrizioni = universityFacade.getIscrizioniStudente(studenteId);
        return ResponseEntity.ok(iscrizioni);
    }

    @GetMapping("/iscrizioni/corso/{corsoId}")
    public ResponseEntity<List<Segue>> getIscrizioniCorso(@PathVariable Long corsoId) {
        List<Segue> iscrizioni = universityFacade.getIscrizioniCorso(corsoId);
        return ResponseEntity.ok(iscrizioni);
    }

    @PutMapping("/iscrizioni/{iscrizioneId}/voto")
    public ResponseEntity<Segue> assegnaVoto(@PathVariable Long iscrizioneId,
                                            @RequestBody VotoRequest request) {
        try {
            Segue iscrizione = universityFacade.assegnaVoto(iscrizioneId, request.getVoto());
            return ResponseEntity.ok(iscrizione);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/iscrizioni/{iscrizioneId}")
    public ResponseEntity<Void> annullaIscrizione(@PathVariable Long iscrizioneId) {
        if (!universityFacade.iscrizioneEsiste(iscrizioneId)) {
            return ResponseEntity.notFound().build();
        }
        universityFacade.annullaIscrizione(iscrizioneId);
        return ResponseEntity.noContent().build();
    }

    // ========== ASSEGNAZIONI (INSEGNA) ==========

    @PostMapping("/assegnazioni")
    public ResponseEntity<Insegna> assegnaCorsoADocente(@RequestBody AssegnazioneRequest request) {
        try {
            Insegna assegnazione = universityFacade.assegnaCorsoADocente(
                request.getDocenteId(),
                request.getCorsoId(),
                request.getAnnoAccademico()
            );
            return new ResponseEntity<>(assegnazione, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/assegnazioni/docente/{docenteId}")
    public ResponseEntity<List<Insegna>> getCorsiInsegnatiDaDocente(@PathVariable Long docenteId) {
        List<Insegna> assegnazioni = universityFacade.getCorsiInsegnatiDaDocente(docenteId);
        return ResponseEntity.ok(assegnazioni);
    }

    @GetMapping("/assegnazioni/corso/{corsoId}")
    public ResponseEntity<List<Insegna>> getDocentiCheInsegnanoCorso(@PathVariable Long corsoId) {
        List<Insegna> assegnazioni = universityFacade.getDocentiCheInsegnanoCorso(corsoId);
        return ResponseEntity.ok(assegnazioni);
    }

    @DeleteMapping("/assegnazioni/{assegnazioneId}")
    public ResponseEntity<Void> rimuoviAssegnazione(@PathVariable Long assegnazioneId) {
        if (!universityFacade.assegnazioneEsiste(assegnazioneId)) {
            return ResponseEntity.notFound().build();
        }
        universityFacade.rimuoviAssegnazione(assegnazioneId);
        return ResponseEntity.noContent().build();
    }

    // ========== GESTIONE REQUISITI CORSI ==========

    @PostMapping("/corsi/{corsoId}/prerequisiti/{prerequisitoId}")
    public ResponseEntity<Corso> aggiungiPrerequisito(@PathVariable Long corsoId,
                                                     @PathVariable Long prerequisitoId) {
        try {
            Corso corso = universityFacade.aggiungiPrerequisito(corsoId, prerequisitoId);
            return ResponseEntity.ok(corso);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/corsi/{corsoId}/prerequisiti/{prerequisitoId}")
    public ResponseEntity<Corso> rimuoviPrerequisito(@PathVariable Long corsoId,
                                                    @PathVariable Long prerequisitoId) {
        try {
            Corso corso = universityFacade.rimuoviPrerequisito(corsoId, prerequisitoId);
            return ResponseEntity.ok(corso);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/corsi/{corsoId}/prerequisiti")
    public ResponseEntity<List<Corso>> getPrerequisitiCorso(@PathVariable Long corsoId) {
        List<Corso> prerequisiti = universityFacade.getPrerequisitiCorso(corsoId);
        return ResponseEntity.ok(prerequisiti);
    }

    @GetMapping("/corsi/{corsoId}/richiesti-da")
    public ResponseEntity<List<Corso>> getCorsiCheRichiedonoCorso(@PathVariable Long corsoId) {
        List<Corso> corsi = universityFacade.getCorsiCheRichiedonoCorso(corsoId);
        return ResponseEntity.ok(corsi);
    }

    // ========== RICERCHE AVANZATE ==========

    @GetMapping("/ricerca/studenti")
    public ResponseEntity<List<Studente>> searchStudenti(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Integer minCrediti,
            @RequestParam(required = false) Double minMedia) {
        List<Studente> risultati = universityFacade.searchStudenti(nome, minCrediti, minMedia);
        return ResponseEntity.ok(risultati);
    }

    @GetMapping("/ricerca/docenti")
    public ResponseEntity<List<Docente>> searchDocenti(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Docente.GradoAccademico grado,
            @RequestParam(required = false) String specializzazione) {
        List<Docente> risultati = universityFacade.searchDocenti(nome, grado, specializzazione);
        return ResponseEntity.ok(risultati);
    }

    @GetMapping("/ricerca/corsi")
    public ResponseEntity<List<Corso>> searchCorsi(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer semestre,
            @RequestParam(required = false) Integer minCrediti,
            @RequestParam(required = false) Long dipartimentoId) {
        List<Corso> risultati = universityFacade.searchCorsi(keyword, semestre, minCrediti, dipartimentoId);
        return ResponseEntity.ok(risultati);
    }

    // ========== OPERAZIONI COMPLESSE ==========

    @PutMapping("/corsi/{corsoId}/trasferisci/{nuovoDipartimentoId}")
    public ResponseEntity<String> trasferisciCorsoADipartimento(
            @PathVariable Long corsoId,
            @PathVariable Long nuovoDipartimentoId) {
        try {
            String risultato = universityFacade.trasferisciCorsoADipartimento(corsoId, nuovoDipartimentoId);
            return ResponseEntity.ok(risultato);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/docenti/{docenteId}/promuovi")
    public ResponseEntity<String> promuoviDocente(
            @PathVariable Long docenteId,
            @RequestParam Docente.GradoAccademico nuovoGrado) {
        try {
            String risultato = universityFacade.promuoviDocente(docenteId, nuovoGrado);
            return ResponseEntity.ok(risultato);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/studenti/{studenteId}/promuovi")
    public ResponseEntity<String> promuoviStudente(@PathVariable Long studenteId) {
        try {
            String risultato = universityFacade.promuoviStudente(studenteId);
            return ResponseEntity.ok(risultato);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ========== STATISTICHE E REPORT ==========

    @GetMapping("/statistiche/universita")
    public ResponseEntity<Map<String, Object>> getStatisticheUniversita() {
        Map<String, Object> statistiche = universityFacade.getStatisticheUniversita();
        return ResponseEntity.ok(statistiche);
    }

    @GetMapping("/statistiche/corso/{corsoId}")
    public ResponseEntity<Map<String, Object>> getStatisticheCorso(@PathVariable Long corsoId) {
        try {
            Map<String, Object> statistiche = universityFacade.getStatisticheCorso(corsoId);
            return ResponseEntity.ok(statistiche);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/statistiche/studente/{studenteId}")
    public ResponseEntity<Map<String, Object>> getStatisticheStudente(@PathVariable Long studenteId) {
        try {
            Map<String, Object> statistiche = universityFacade.getStatisticheStudente(studenteId);
            return ResponseEntity.ok(statistiche);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/statistiche/dipartimento/{dipartimentoId}")
    public ResponseEntity<Map<String, Object>> getStatisticheDipartimento(@PathVariable Long dipartimentoId) {
        try {
            Map<String, Object> statistiche = universityFacade.getStatisticheDipartimento(dipartimentoId);
            return ResponseEntity.ok(statistiche);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/top-studenti")
    public ResponseEntity<List<Studente>> getTopStudenti(
            @RequestParam(defaultValue = "10") int limit) {
        List<Studente> topStudenti = universityFacade.getTopStudenti(limit);
        return ResponseEntity.ok(topStudenti);
    }

    @GetMapping("/analisi/crediti")
    public ResponseEntity<Map<String, Object>> analizzaDistribuzioneCrediti() {
        Map<String, Object> analisi = universityFacade.analizzaDistribuzioneCrediti();
        return ResponseEntity.ok(analisi);
    }

    @GetMapping("/analisi/voti/{corsoId}")
    public ResponseEntity<Map<String, Object>> analizzaDistribuzioneVoti(@PathVariable Long corsoId) {
        try {
            Map<String, Object> analisi = universityFacade.analizzaDistribuzioneVoti(corsoId);
            return ResponseEntity.ok(analisi);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ========== BATCH OPERATIONS ==========

    @PostMapping("/batch/iscrizioni")
    public ResponseEntity<Map<String, Object>> batchIscriviStudenti(
            @RequestBody List<Map<String, Long>> richieste) {
        Map<String, Object> risultato = universityFacade.batchIscriviStudenti(richieste);
        return ResponseEntity.ok(risultato);
    }

    @PostMapping("/batch/voti")
    public ResponseEntity<Map<String, Object>> batchAssegnaVoti(
            @RequestBody List<Map<String, Object>> assegnazioniVoti) {
        Map<String, Object> risultato = universityFacade.batchAssegnaVoti(assegnazioniVoti);
        return ResponseEntity.ok(risultato);
    }

    // ========== REQUEST CLASSES ==========

    // Classi interne per i DTO di richiesta
    @lombok.Data
    public static class StudenteRequest {
        private String nome;
        private String cognome;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate dataNascita;
        private String email;
        private String telefono;
        private String matricola;
        private Integer annoImmatricolazione;
    }

    @lombok.Data
    public static class StudenteUpdateRequest {
        private String nome;
        private String cognome;
        private String telefono;
        private Integer semestreCorrente;
    }

    @lombok.Data
    public static class DocenteRequest {
        private String nome;
        private String cognome;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate dataNascita;
        private String email;
        private String telefono;
        private String codiceDocente;
        private Docente.GradoAccademico gradoAccademico;
        private BigDecimal stipendio;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate dataAssunzione;
        private String ufficio;
        private String specializzazione;
    }

    @lombok.Data
    public static class DocenteUpdateRequest {
        private String nome;
        private String cognome;
        private String telefono;
        private BigDecimal stipendio;
        private String ufficio;
        private String specializzazione;
    }

    @lombok.Data
    public static class DipartimentoRequest {
        private String codice;
        private String nome;
        private String descrizione;
    }

    @lombok.Data
    public static class DipartimentoUpdateRequest {
        private String nome;
        private String descrizione;
    }

    @lombok.Data
    public static class CorsoRequest {
        private String codice;
        private String nome;
        private String descrizione;
        private Integer crediti;
        private Integer semestre;
        private Long dipartimentoId;
    }

    @lombok.Data
    public static class CorsoUpdateRequest {
        private String nome;
        private String descrizione;
        private Integer crediti;
        private Integer semestre;
    }

    @lombok.Data
    public static class IscrizioneRequest {
        private Long studenteId;
        private Long corsoId;
    }

    @lombok.Data
    public static class VotoRequest {
        private Double voto;
    }

    @lombok.Data
    public static class AssegnazioneRequest {
        private Long docenteId;
        private Long corsoId;
        private String annoAccademico;
    }
}