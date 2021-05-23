package com.projectpokerrest.pokerrest.web.api;

import com.projectpokerrest.pokerrest.model.Tavolo;
import com.projectpokerrest.pokerrest.model.Utente;
import com.projectpokerrest.pokerrest.service.ruolo.RuoloService;
import com.projectpokerrest.pokerrest.service.tavolo.TavoloService;
import com.projectpokerrest.pokerrest.service.utente.UtenteService;
import com.projectpokerrest.pokerrest.web.api.exception.TavoloNotFoundException;
import com.projectpokerrest.pokerrest.web.api.exception.UtenteNotAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/gestionetavolo")
public class GestioneTavoloController {

    @Autowired
    private TavoloService tavoloService;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private RuoloService ruoloService;

    @GetMapping
    public ResponseEntity<List<Tavolo>> listAll(@RequestHeader("Authorization") String username) {
        verifyUtente(username);
        return ResponseEntity.ok(tavoloService.listAllTavolo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tavolo> findById(@PathVariable(required = true) Long id, @RequestHeader("Authorization") String username) {
        verifyUtente(username);

        Tavolo tavolo = tavoloService.caricaSingoloTavoloConUtenti(id);

        if (tavolo == null)
            throw new TavoloNotFoundException("Tavolo non trovato!");

        return ResponseEntity.ok(tavolo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Tavolo> createNewTavolo(@Valid @RequestBody Tavolo tavoloInput, @RequestHeader("Authorization") String username) {
        verifyUtente(username);

        if (tavoloInput.getUtenteCreazione().getRuoli().contains(ruoloService.cercaPerDescrizioneECodice("Normal Player", "ROLE_PLAYER")))
            throw new UtenteNotAuthorizedException("Non sei autorizzato ad accedere a questa funzionalita'.");

        return ResponseEntity.ok(tavoloService.inserisciNuovo(tavoloInput));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tavolo> updateTavolo(@Valid @RequestBody Tavolo tavoloInput, @PathVariable(required = true) Long id, @RequestHeader("Authorization") String username) {
        verifyUtente(username);
        Tavolo tavoloTemp = tavoloService.caricaSingoloTavoloConUtenti(id);

        if (tavoloTemp == null)
            throw new TavoloNotFoundException("Tavolo non trovato!");

        tavoloInput.setId(id);
        return ResponseEntity.ok(tavoloService.aggiorna(tavoloInput));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTavolo(@PathVariable(required = true) Long id, @RequestHeader("Authorization") String username) {
        verifyUtente(username);
        Tavolo tavolo = tavoloService.caricaSingoloTavoloConUtenti(id);

        if (tavolo == null)
            throw new TavoloNotFoundException("Tavolo non trovato!");

        tavoloService.rimuovi(tavolo);
    }

    public void verifyUtente(String utenteInput) {
        Utente utente = utenteService.findByUsername(utenteInput);

        if (!utente.getRuoli().contains(ruoloService.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN")) &&
                !utente.getRuoli().contains(ruoloService.cercaPerDescrizioneECodice("Special Player", "ROLE_SPECIAL_PLAYER")))
            throw new UtenteNotAuthorizedException("Non sei autorizzato ad accedere a questa funzionalita'.");
    }

}
