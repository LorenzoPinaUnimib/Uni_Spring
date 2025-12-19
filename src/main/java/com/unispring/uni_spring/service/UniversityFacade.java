package com.unispring.uni_spring.service;

import com.unispring.uni_spring.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class UniversityFacade {
    
    @Autowired private StudenteService studenteService;
    @Autowired private DocenteService docenteService;
    @Autowired private DipartimentoService dipartimentoService;
    @Autowired private CorsoService corsoService;
    @Autowired private SegueService segueService;
    @Autowired private InsegnaService insegnaService;
    
    // ========== STUDENTI ==========
    
    public Studente createStudente(String nome, String cognome, LocalDate dataNascita,
                                  String email,  String matricola,
                                  Integer annoImmatricolazione) {
        Studente studente = new Studente();
        studente.setNome(nome);
        studente.setCognome(cognome);
        studente.setDataNascita(dataNascita);
        studente.setEmail(email);
        studente.setMatricola(matricola);
        studente.setAnnoImmatricolazione(annoImmatricolazione);
        
        return studenteService.createStudente(studente);
    }
    
    public Optional<Studente> getStudenteById(Long id) {
        return studenteService.findById(id);
    }
    
    public Optional<Studente> getStudenteByMatricola(String matricola) {
        return studenteService.findByMatricola(matricola);
    }
    
    public List<Studente> getAllStudenti() {
        return studenteService.findAll();
    }
    
    public Studente updateStudente(Long id, String nome, String cognome, 
                                   Integer semestreCorrente) {
        Studente studenteDetails = new Studente();
        if (nome != null) studenteDetails.setNome(nome);
        if (cognome != null) studenteDetails.setCognome(cognome);
        if (semestreCorrente != null) studenteDetails.setSemestreCorrente(semestreCorrente);
        
        return studenteService.updateStudente(id, studenteDetails);
    }
    
    public void deleteStudente(Long id) {
        studenteService.deleteStudente(id);
    }
    
    // ========== DOCENTI ==========
    
    public Docente createDocente(String nome, String cognome, LocalDate dataNascita,
                                String email, String codiceDocente,
                                Docente.GradoAccademico gradoAccademico, BigDecimal stipendio,
                                LocalDate dataAssunzione, String ufficio, String specializzazione) {
        Docente docente = new Docente();
        docente.setNome(nome);
        docente.setCognome(cognome);
        docente.setDataNascita(dataNascita);
        docente.setEmail(email);
        docente.setCodiceDocente(codiceDocente);
        docente.setGradoAccademico(gradoAccademico);
        docente.setStipendio(stipendio);
        docente.setDataAssunzione(dataAssunzione);
        docente.setUfficio(ufficio);
        docente.setSpecializzazione(specializzazione);
        
        return docenteService.createDocente(docente);
    }
    
    public Optional<Docente> getDocenteById(Long id) {
        return docenteService.findById(id);
    }
    
    public List<Docente> getAllDocenti() {
        return docenteService.findAll();
    }
    
    public Docente updateDocente(Long id, String nome, String cognome,
                                BigDecimal stipendio, 
                                String ufficio, String specializzazione) {
        Docente docenteDetails = new Docente();
        if (nome != null) docenteDetails.setNome(nome);
        if (cognome != null) docenteDetails.setCognome(cognome);
        if (stipendio != null) docenteDetails.setStipendio(stipendio);
        if (ufficio != null) docenteDetails.setUfficio(ufficio);
        if (specializzazione != null) docenteDetails.setSpecializzazione(specializzazione);
        
        return docenteService.updateDocente(id, docenteDetails);
    }
    
    public void deleteDocente(Long id) {
        docenteService.deleteDocente(id);
    }
    
    // ========== DIPARTIMENTI ==========
    
    public Dipartimento createDipartimento(String codice, String nome, String descrizione) {
        Dipartimento dipartimento = new Dipartimento();
        dipartimento.setCodice(codice);
        dipartimento.setNome(nome);
        dipartimento.setDescrizione(descrizione);
        
        return dipartimentoService.createDipartimento(dipartimento);
    }
    
    public Optional<Dipartimento> getDipartimentoById(Long id) {
        return dipartimentoService.findById(id);
    }
    
    public List<Dipartimento> getAllDipartimenti() {
        return dipartimentoService.findAll();
    }
    
    public Dipartimento updateDipartimento(Long id, String nome, String descrizione) {
        Dipartimento dipartimentoDetails = new Dipartimento();
        if (nome != null) dipartimentoDetails.setNome(nome);
        if (descrizione != null) dipartimentoDetails.setDescrizione(descrizione);
        
        return dipartimentoService.updateDipartimento(id, dipartimentoDetails);
    }
    
    public void deleteDipartimento(Long id) {
        dipartimentoService.deleteDipartimento(id);
    }
    
    // ========== CORSI ==========
    
    public Corso createCorso(String codice, String nome, String descrizione,
                            Integer crediti, Integer semestre, Long dipartimentoId) {
        Corso corso = new Corso();
        corso.setCodice(codice);
        corso.setNome(nome);
        corso.setDescrizione(descrizione);
        corso.setCrediti(crediti);
        corso.setSemestre(semestre);
        
        if (dipartimentoId != null) {
            Dipartimento dipartimento = dipartimentoService.findById(dipartimentoId)
                    .orElseThrow(() -> new RuntimeException("Dipartimento non trovato"));
            corso.setDipartimento(dipartimento);
        }
        
        return corsoService.createCorso(corso);
    }
    
    public Optional<Corso> getCorsoById(Long id) {
        return corsoService.findById(id);
    }
    
    public List<Corso> getAllCorsi() {
        return corsoService.findAll();
    }
    
    public Corso updateCorso(Long id, String nome, String descrizione,
                            Integer crediti, Integer semestre) {
        Corso corsoDetails = new Corso();
        if (nome != null) corsoDetails.setNome(nome);
        if (descrizione != null) corsoDetails.setDescrizione(descrizione);
        if (crediti != null) corsoDetails.setCrediti(crediti);
        if (semestre != null) corsoDetails.setSemestre(semestre);
        
        return corsoService.updateCorso(id, corsoDetails);
    }
    
    public void deleteCorso(Long id) {
        corsoService.deleteCorso(id);
    }
    
    // ========== ISCRIZIONI (SEGUE) ==========
    
    public Segue iscriviStudenteACorso(Long studenteId, Long corsoId) {
        Studente studente = studenteService.findById(studenteId)
                .orElseThrow(() -> new RuntimeException("Studente non trovato"));
        
        Corso corso = corsoService.findById(corsoId)
                .orElseThrow(() -> new RuntimeException("Corso non trovato"));
        
        // Validazioni business
        if (!studenteService.canIscriversiACorso(studenteId, corso.getCrediti())) {
            throw new RuntimeException("Studente non può iscriversi - limite crediti superato");
        }
        
        if (corsoService.isCorsoPieno(corsoId)) {
            throw new RuntimeException("Corso è pieno");
        }
        
        if (!corsoService.hasPrerequisitiSoddisfatti(corsoId, studenteId)) {
            throw new RuntimeException("Prerequisiti non soddisfatti");
        }
        
        return segueService.iscriviStudente(studente, corso);
    }
    
    public List<Segue> getIscrizioniStudente(Long studenteId) {
        return segueService.findByStudenteId(studenteId);
    }
    
    public List<Segue> getIscrizioniCorso(Long corsoId) {
        return segueService.findByCorsoId(corsoId);
    }
    
    public Segue assegnaVoto(Long iscrizioneId, Double voto) {
        // Assegna voto
        Segue iscrizione = segueService.assegnaVoto(iscrizioneId, voto);
        
        // Ricalcola media voti studente
        Double nuovaMedia = segueService.calculateMediaVotoStudente(iscrizione.getStudente().getId());
        studenteService.updateMediaVoti(iscrizione.getStudente().getId(), nuovaMedia);
        
        // Aggiorna crediti studente
        studenteService.addCrediti(iscrizione.getStudente().getId(), iscrizione.getCorso().getCrediti());
        
        return iscrizione;
    }
    
    public void annullaIscrizione(Long iscrizioneId) {
        segueService.deleteSegue(iscrizioneId);
    }
    
    // ========== ASSEGNAZIONI (INSEGNA) ==========
    
    public Insegna assegnaCorsoADocente(Long docenteId, Long corsoId, String annoAccademico) {
        Docente docente = docenteService.findById(docenteId)
                .orElseThrow(() -> new RuntimeException("Docente non trovato"));
        
        Corso corso = corsoService.findById(corsoId)
                .orElseThrow(() -> new RuntimeException("Corso non trovato"));
        
        // Validazione: docente può insegnare questa materia?
        if (!docenteService.canInsegnareCorso(docenteId, corso.getNome())) {
            throw new RuntimeException("Docente non specializzato in questa materia");
        }
        
        return insegnaService.assegnaCorsoADocente(docente, corso, annoAccademico);
    }
    
    public List<Insegna> getCorsiInsegnatiDaDocente(Long docenteId) {
        return insegnaService.findByDocenteId(docenteId);
    }
    
    public List<Insegna> getDocentiCheInsegnanoCorso(Long corsoId) {
        return insegnaService.findByCorsoId(corsoId);
    }
    
    public void rimuoviAssegnazione(Long assegnazioneId) {
        insegnaService.deleteInsegna(assegnazioneId);
    }
    
    // ========== GESTIONE REQUISITI CORSI ==========
    
    public Corso aggiungiPrerequisito(Long corsoId, Long prerequisitoId) {
        return corsoService.addPrerequisito(corsoId, prerequisitoId);
    }
    
    public Corso rimuoviPrerequisito(Long corsoId, Long prerequisitoId) {
        return corsoService.removePrerequisito(corsoId, prerequisitoId);
    }
    
    public List<Corso> getPrerequisitiCorso(Long corsoId) {
        return corsoService.getPrerequisiti(corsoId);
    }
    
    public List<Corso> getCorsiCheRichiedonoCorso(Long corsoId) {
        return corsoService.getCorsiCheRichiedono(corsoId);
    }
    
    // ========== RICERCHE AVANZATE ==========
    
    public List<Studente> searchStudenti(String nome, Integer minCrediti, Double minMedia) {
        List<Studente> risultati = studenteService.findAll();
        
        if (nome != null && !nome.isEmpty()) {
            risultati = risultati.stream()
                    .filter(s -> s.getNomeCompleto().toLowerCase().contains(nome.toLowerCase()))
                    .toList();
        }
        
        if (minCrediti != null) {
            risultati = risultati.stream()
                    .filter(s -> s.getCreditiTotali() >= minCrediti)
                    .toList();
        }
        
        if (minMedia != null) {
            risultati = risultati.stream()
                    .filter(s -> s.getMediaVoti() >= minMedia)
                    .toList();
        }
        
        return risultati;
    }
    
    public List<Docente> searchDocenti(String nome, Docente.GradoAccademico grado, String specializzazione) {
        List<Docente> risultati = docenteService.findAll();
        
        if (nome != null && !nome.isEmpty()) {
            risultati = risultati.stream()
                    .filter(d -> d.getNomeCompleto().toLowerCase().contains(nome.toLowerCase()))
                    .toList();
        }
        
        if (grado != null) {
            risultati = risultati.stream()
                    .filter(d -> d.getGradoAccademico() == grado)
                    .toList();
        }
        
        if (specializzazione != null && !specializzazione.isEmpty()) {
            risultati = risultati.stream()
                    .filter(d -> d.getSpecializzazione() != null && 
                                d.getSpecializzazione().toLowerCase().contains(specializzazione.toLowerCase()))
                    .toList();
        }
        
        return risultati;
    }
    
    public List<Corso> searchCorsi(String keyword, Integer semestre, Integer minCrediti, Long dipartimentoId) {
        List<Corso> risultati = corsoService.findAll();
        
        if (keyword != null && !keyword.isEmpty()) {
            risultati = risultati.stream()
                    .filter(c -> c.getNome().toLowerCase().contains(keyword.toLowerCase()) ||
                                (c.getDescrizione() != null && 
                                 c.getDescrizione().toLowerCase().contains(keyword.toLowerCase())))
                    .toList();
        }
        
        if (semestre != null) {
            risultati = risultati.stream()
                    .filter(c -> c.getSemestre().equals(semestre))
                    .toList();
        }
        
        if (minCrediti != null) {
            risultati = risultati.stream()
                    .filter(c -> c.getCrediti() >= minCrediti)
                    .toList();
        }
        
        if (dipartimentoId != null) {
            risultati = risultati.stream()
                    .filter(c -> c.getDipartimento() != null && 
                                c.getDipartimento().getId().equals(dipartimentoId))
                    .toList();
        }
        
        return risultati;
    }
    
    // ========== OPERAZIONI COMPLESSE ==========
    
    public String trasferisciCorsoADipartimento(Long corsoId, Long nuovoDipartimentoId) {
        Corso corso = corsoService.findById(corsoId)
                .orElseThrow(() -> new RuntimeException("Corso non trovato"));
        
        Dipartimento nuovoDipartimento = dipartimentoService.findById(nuovoDipartimentoId)
                .orElseThrow(() -> new RuntimeException("Nuovo dipartimento non trovato"));
        
        corso.setDipartimento(nuovoDipartimento);
        corsoService.createCorso(corso); // Update
        
        return "Corso trasferito al dipartimento " + nuovoDipartimento.getNome();
    }
    
    
    // ========== STATISTICHE E REPORT ==========
    
    public Map<String, Object> getStatisticheUniversita() {
        long totalStudenti = studenteService.countAll();
        long totalDocenti = docenteService.countAll();
        long totalDipartimenti = dipartimentoService.countAll();
        long totalCorsi = corsoService.countAll();
        long totalIscrizioni = segueService.countAll();
        long totalAssegnazioni = insegnaService.countAll();
        BigDecimal costoStipendi = docenteService.calculateCostoTotaleStipendi();
        Double mediaVotiUniversita = studenteService.getMediaVotiUniversita();
        
        return Map.of(
            "totalStudenti", totalStudenti,
            "totalDocenti", totalDocenti,
            "totalDipartimenti", totalDipartimenti,
            "totalCorsi", totalCorsi,
            "totalIscrizioni", totalIscrizioni,
            "totalAssegnazioni", totalAssegnazioni,
            "costoTotaleStipendi", costoStipendi,
            "mediaVotiUniversita", Math.round(mediaVotiUniversita * 100.0) / 100.0
        );
    }
    
    public Map<String, Object> getStatisticheCorso(Long corsoId) {
        Corso corso = corsoService.findById(corsoId)
                .orElseThrow(() -> new RuntimeException("Corso non trovato"));
        
        long studentiIscritti = corsoService.countStudentiIscritti(corsoId);
        Double mediaVoto = segueService.calculateMediaVotoCorso(corsoId);
        long promossi = segueService.countPromossi(corsoId);
        long votiAssegnati = segueService.countVotiAssegnati(corsoId);
        int docentiAssegnati = insegnaService.countDocentiCorso(corsoId);
        
        double tassoPromozione = votiAssegnati > 0 ? (double) promossi / votiAssegnati * 100 : 0;
        
        return Map.of(
            "corsoId", corsoId,
            "corsoNome", corso.getNome(),
            "studentiIscritti", studentiIscritti,
            "mediaVoto", Math.round(mediaVoto * 100.0) / 100.0,
            "promossi", promossi,
            "votiAssegnati", votiAssegnati,
            "docentiAssegnati", docentiAssegnati,
            "tassoPromozione", Math.round(tassoPromozione * 100.0) / 100.0
        );
    }
    
    public Map<String, Object> getStatisticheStudente(Long studenteId) {
        Studente studente = studenteService.findById(studenteId)
                .orElseThrow(() -> new RuntimeException("Studente non trovato"));
        
        List<Segue> iscrizioni = segueService.findByStudenteId(studenteId);
        long totalCorsi = iscrizioni.size();
        long corsiSuperati = iscrizioni.stream()
                .filter(s -> s.getVoto() != null && s.getVoto() >= 18.0)
                .count();
        
        Double mediaVotoStudente = segueService.calculateMediaVotoStudente(studenteId);
        
        return Map.of(
            "studenteId", studenteId,
            "studenteNome", studente.getNomeCompleto(),
            "matricola", studente.getMatricola(),
            "creditiTotali", studente.getCreditiTotali(),
            "mediaVoti", studente.getMediaVoti(),
            "totalCorsiSeguiti", totalCorsi,
            "corsiSuperati", corsiSuperati,
            "mediaVotoCalcolata", Math.round(mediaVotoStudente * 100.0) / 100.0,
            "percentualeSuccesso", totalCorsi > 0 ? Math.round((double) corsiSuperati / totalCorsi * 10000.0) / 100.0 : 0
        );
    }
    
    public Map<String, Object> getStatisticheDipartimento(Long dipartimentoId) {
        Dipartimento dipartimento = dipartimentoService.findById(dipartimentoId)
                .orElseThrow(() -> new RuntimeException("Dipartimento non trovato"));
        
        List<Corso> corsiDipartimento = corsoService.findByDipartimento(dipartimentoId);
        long totalCorsi = corsiDipartimento.size();
        
        // Conta studenti nei corsi del dipartimento
        long totalStudenti = corsiDipartimento.stream()
                .mapToLong(c -> corsoService.countStudentiIscritti(c.getId()))
                .sum();
        
        // Conta docenti che insegnano corsi del dipartimento
        long totalDocenti = corsiDipartimento.stream()
                .flatMap(c -> insegnaService.findByCorsoId(c.getId()).stream())
                .map(i -> i.getDocente().getId())
                .distinct()
                .count();
        
        return Map.of(
            "dipartimentoId", dipartimentoId,
            "dipartimentoNome", dipartimento.getNome(),
            "totalCorsi", totalCorsi,
            "totalStudenti", totalStudenti,
            "totalDocenti", totalDocenti
        );
    }
    
    public List<Studente> getTopStudenti(int limit) {
        return studenteService.getTopStudenti(limit);
    }
    
    public Map<String, Object> analizzaDistribuzioneCrediti() {
        List<Studente> studenti = studenteService.findAll();
        
        if (studenti.isEmpty()) {
            return Map.of("messaggio", "Nessuno studente trovato");
        }
        
        double mediaCrediti = studenti.stream()
                .mapToInt(Studente::getCreditiTotali)
                .average()
                .orElse(0.0);
        
        int maxCrediti = studenti.stream()
                .mapToInt(Studente::getCreditiTotali)
                .max()
                .orElse(0);
        
        int minCrediti = studenti.stream()
                .mapToInt(Studente::getCreditiTotali)
                .min()
                .orElse(0);
        
        Map<String, Long> distribuzioneCrediti = studenti.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                    s -> {
                        int crediti = s.getCreditiTotali();
                        if (crediti < 60) return "0-59 (Primo anno)";
                        else if (crediti < 120) return "60-119 (Secondo anno)";
                        else if (crediti < 180) return "120-179 (Terzo anno)";
                        else return "180+ (Laureando)";
                    },
                    java.util.stream.Collectors.counting()
                ));
        
        return Map.of(
            "mediaCrediti", Math.round(mediaCrediti * 100.0) / 100.0,
            "maxCrediti", maxCrediti,
            "minCrediti", minCrediti,
            "totalStudenti", studenti.size(),
            "distribuzioneCrediti", distribuzioneCrediti
        );
    }
    
    public Map<String, Object> analizzaDistribuzioneVoti(Long corsoId) {
        List<Segue> iscrizioni = segueService.findByCorsoId(corsoId);
        
        if (iscrizioni.isEmpty()) {
            return Map.of("messaggio", "Nessuna iscrizione trovata per questo corso");
        }
        
        List<Segue> iscrizioniConVoto = iscrizioni.stream()
                .filter(s -> s.getVoto() != null)
                .toList();
        
        if (iscrizioniConVoto.isEmpty()) {
            return Map.of("messaggio", "Nessun voto assegnato per questo corso");
        }
        
        Map<String, Long> distribuzioneVoti = iscrizioniConVoto.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                    s -> {
                        double voto = s.getVoto();
                        if (voto < 18) return "Bocciati (<18)";
                        else if (voto < 22) return "Sufficiente (18-21)";
                        else if (voto < 26) return "Buono (22-25)";
                        else if (voto < 28) return "Distinto (26-27)";
                        else return "Ottimo (28-30)";
                    },
                    java.util.stream.Collectors.counting()
                ));
        
        double mediaVoto = iscrizioniConVoto.stream()
                .mapToDouble(Segue::getVoto)
                .average()
                .orElse(0.0);
        
        long promossi = iscrizioniConVoto.stream()
                .filter(s -> s.getVoto() >= 18.0)
                .count();
        
        return Map.of(
            "totalIscrizioni", iscrizioni.size(),
            "iscrizioniConVoto", iscrizioniConVoto.size(),
            "mediaVoto", Math.round(mediaVoto * 100.0) / 100.0,
            "promossi", promossi,
            "tassoPromozione", Math.round((double) promossi / iscrizioniConVoto.size() * 10000.0) / 100.0,
            "distribuzioneVoti", distribuzioneVoti
        );
    }
    
    // ========== BATCH OPERATIONS ==========
    
    public Map<String, Object> batchIscriviStudenti(List<Map<String, Long>> richieste) {
        int successo = 0;
        int fallito = 0;
        List<Segue> iscrizioni = new java.util.ArrayList<>();
        List<String> errori = new java.util.ArrayList<>();
        
        for (Map<String, Long> richiesta : richieste) {
            try {
                Long studenteId = richiesta.get("studenteId");
                Long corsoId = richiesta.get("corsoId");
                
                if (studenteId == null || corsoId == null) {
                    fallito++;
                    errori.add("Mancano studenteId o corsoId nella richiesta");
                    continue;
                }
                
                Segue iscrizione = iscriviStudenteACorso(studenteId, corsoId);
                iscrizioni.add(iscrizione);
                successo++;
            } catch (RuntimeException e) {
                fallito++;
                errori.add("Fallito: " + e.getMessage());
            }
        }
        
        return Map.of(
            "richiesteTotali", richieste.size(),
            "successo", successo,
            "fallito", fallito,
            "iscrizioni", iscrizioni,
            "errori", errori
        );
    }
    
    public Map<String, Object> batchAssegnaVoti(List<Map<String, Object>> assegnazioniVoti) {
        int successo = 0;
        int fallito = 0;
        List<Segue> iscrizioniAggiornate = new java.util.ArrayList<>();
        List<String> errori = new java.util.ArrayList<>();
        
        for (Map<String, Object> assegnazione : assegnazioniVoti) {
            try {
                Long iscrizioneId = ((Number) assegnazione.get("iscrizioneId")).longValue();
                Double voto = ((Number) assegnazione.get("voto")).doubleValue();
                
                if (iscrizioneId == null || voto == null) {
                    fallito++;
                    errori.add("Mancano iscrizioneId o voto nella richiesta");
                    continue;
                }
                
                if (voto < 0 || voto > 30) {
                    fallito++;
                    errori.add("Voto deve essere tra 0 e 30");
                    continue;
                }
                
                Segue iscrizione = assegnaVoto(iscrizioneId, voto);
                iscrizioniAggiornate.add(iscrizione);
                successo++;
            } catch (RuntimeException e) {
                fallito++;
                errori.add("Fallito: " + e.getMessage());
            }
        }
        
        return Map.of(
            "assegnazioniTotali", assegnazioniVoti.size(),
            "successo", successo,
            "fallito", fallito,
            "iscrizioniAggiornate", iscrizioniAggiornate,
            "errori", errori
        );
    }
    /**
     * Filtra studenti che seguono corsi di un dipartimento specifico
     */
    public List<Studente> filtraStudentiPerCorsiDipartimento(String nomeDipartimento) {
        if (nomeDipartimento == null || nomeDipartimento.trim().isEmpty()) {
            throw new IllegalArgumentException("Il nome del dipartimento è obbligatorio");
        }
        
        return studenteService.getStudentiByCorsiDipartimento(nomeDipartimento);
    }
    
    /**
     * Filtra studenti con filtri avanzati per corsi di un dipartimento
     */
    public List<Studente> filtraStudentiPerCorsiDipartimentoConFiltri(
            String nomeDipartimento,
            String nomeStudente,
            Integer minCrediti,
            Double minMedia) {
        
        if (nomeDipartimento == null || nomeDipartimento.trim().isEmpty()) {
            throw new IllegalArgumentException("Il nome del dipartimento è obbligatorio");
        }
        
        return studenteService.getStudentiByCorsiDipartimentoConFiltri(
            nomeDipartimento,
            nomeStudente,
            minCrediti,
            minMedia
        );
    }
    
    /**
     * Statistiche per studenti che seguono corsi di un dipartimento
     */
    public Map<String, Object> getStatisticheStudentiPerCorsiDipartimento(String nomeDipartimento) {
        if (nomeDipartimento == null || nomeDipartimento.trim().isEmpty()) {
            throw new IllegalArgumentException("Il nome del dipartimento è obbligatorio");
        }
        
        List<Studente> studenti = studenteService.getStudentiByCorsiDipartimento(nomeDipartimento);
        Long totaleStudenti = studenteService.countStudentiByCorsiDipartimento(nomeDipartimento);
        
        double mediaCrediti = studenti.stream()
                .mapToInt(Studente::getCreditiTotali)
                .average()
                .orElse(0.0);
        
        double mediaVoti = studenti.stream()
                .mapToDouble(Studente::getMediaVoti)
                .average()
                .orElse(0.0);
        
        return Map.of(
            "nomeDipartimento", nomeDipartimento,
            "totaleStudenti", totaleStudenti,
            "mediaCrediti", Math.round(mediaCrediti * 100.0) / 100.0,
            "mediaVoti", Math.round(mediaVoti * 100.0) / 100.0,
            "studenti", studenti
        );
    }
    
    /**
     * Filtra corsi con docenti assunti nell'anno corrente
     */
    public List<Corso> filtraCorsiConDocentiAssuntiAnnoCorrente() {
        return corsoService.getCorsiConDocentiAssuntiAnnoCorrente();
    }
    
    /**
     * Filtra corsi con docenti assunti in un anno specifico
     */
    public List<Corso> filtraCorsiConDocentiAssuntiAnno(Integer anno) {
        if (anno == null) {
            throw new IllegalArgumentException("L'anno è obbligatorio");
        }
        
        return corsoService.getCorsiConDocentiAssuntiAnno(anno);
    }
    
    /**
     * Filtra corsi con docenti assunti recentemente con filtri avanzati
     */
    public List<Corso> filtraCorsiConDocentiAssuntiConFiltri(
            Integer anno,
            String nomeCorso,
            Integer minCrediti,
            Long dipartimentoId) {
        
        if (anno == null) {
            // Default: anno corrente
            anno = java.time.LocalDate.now().getYear();
        }
        
        if (anno.equals(java.time.LocalDate.now().getYear())) {
            return corsoService.getCorsiConDocentiAssuntiAnnoCorrenteConFiltri(
                nomeCorso,
                minCrediti,
                dipartimentoId
            );
        } else {
            // Per anni diversi dal corrente, filtriamo manualmente
            List<Corso> corsi = corsoService.getCorsiConDocentiAssuntiAnno(anno);
            
            return corsi.stream()
                    .filter(c -> nomeCorso == null || c.getNome().toLowerCase().contains(nomeCorso.toLowerCase()))
                    .filter(c -> minCrediti == null || c.getCrediti() >= minCrediti)
                    .filter(c -> dipartimentoId == null || 
                                (c.getDipartimento() != null && c.getDipartimento().getId().equals(dipartimentoId)))
                    .toList();
        }
    }
    
    // ========== UTILITY METHODS ==========
    
    public boolean studenteEsiste(Long studenteId) {
        return studenteService.findById(studenteId).isPresent();
    }
    
    public boolean docenteEsiste(Long docenteId) {
        return docenteService.findById(docenteId).isPresent();
    }
    
    public boolean dipartimentoEsiste(Long dipartimentoId) {
        return dipartimentoService.findById(dipartimentoId).isPresent();
    }
    
    public boolean corsoEsiste(Long corsoId) {
        return corsoService.findById(corsoId).isPresent();
    }
    
    public boolean iscrizioneEsiste(Long iscrizioneId) {
        return segueService.findById(iscrizioneId).isPresent();
    }
    
    public boolean assegnazioneEsiste(Long assegnazioneId) {
        return insegnaService.findById(assegnazioneId).isPresent();
    }
}