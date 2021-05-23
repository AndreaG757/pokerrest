package com.projectpokerrest.pokerrest.web.api;

import com.projectpokerrest.pokerrest.model.Utente;
import com.projectpokerrest.pokerrest.service.ruolo.RuoloService;
import com.projectpokerrest.pokerrest.service.utente.UtenteService;
import com.projectpokerrest.pokerrest.web.api.exception.UtenteNotAuthorizedException;
import com.projectpokerrest.pokerrest.web.api.exception.UtenteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/gestioneamministrazione")
public class GestioneAmministrazione {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private RuoloService ruoloService;

    @GetMapping
    public ResponseEntity<List<Utente>> listAll(@RequestHeader("Authorization") String username) {
        verifyUtente(username);
        return ResponseEntity.ok(utenteService.listAllUtenti());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utente> findById(@PathVariable(required = true) Long id, @RequestHeader("Authorization") String username) {
        verifyUtente(username);

        Utente utente = utenteService.caricaUtenteConRuoli(id);

        if (utente == null)
            throw new UtenteNotFoundException("Utente non trovato!");

        return ResponseEntity.ok(utente);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Utente> createNewUtente(@Valid @RequestBody Utente utenteInput, @RequestHeader("Authorization") String username) {
        verifyUtente(username);
        return ResponseEntity.ok(utenteService.inserisciNuovo(utenteInput));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utente> updateUtente(@Valid @RequestBody Utente utenteInput, @PathVariable(required = true) Long id, @RequestHeader("Authorization") String username) {
        verifyUtente(username);

        Utente utente = utenteService.caricaUtenteConRuoli(id);

        if (utente == null)
            throw new UtenteNotFoundException("Utente non trovato!");

        utenteInput.setId(id);
        return ResponseEntity.ok(utenteService.aggiorna(utenteInput));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUtente(@PathVariable(required = true) Long id, @RequestHeader("Authorization") String username) {
        verifyUtente(username);
        Utente utente = utenteService.caricaUtenteConRuoli(id);

        if (utente == null)
            throw new UtenteNotFoundException("Utente non trovato!");

        utenteService.rimuovi(utente);
    }

    public void verifyUtente(String utenteInput) {
        Utente utente = utenteService.findByUsername(utenteInput);

        if (!utente.getRuoli().contains(ruoloService.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN")) &&
                !utente.getRuoli().contains(ruoloService.cercaPerDescrizioneECodice("Special Player", "ROLE_SPECIAL_PLAYER")))
            throw new UtenteNotAuthorizedException("Non sei autorizzato ad accedere a questa funzionalita'.");
    }

}
