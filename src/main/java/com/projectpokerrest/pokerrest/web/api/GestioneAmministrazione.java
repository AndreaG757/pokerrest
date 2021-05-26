package com.projectpokerrest.pokerrest.web.api;

import com.projectpokerrest.pokerrest.model.User;
import com.projectpokerrest.pokerrest.service.UserService;
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
    private UserService utenteService;

    @GetMapping
    public ResponseEntity<List<User>> listAll() {
        return ResponseEntity.ok(utenteService.listAllElements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable(required = true) Long id) {
        User utente = utenteService.caricaSingoloElemento(id);

        if (utente == null)
            throw new UtenteNotFoundException("Utente non trovato!");

        return ResponseEntity.ok(utente);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createNewUtente(@Valid @RequestBody User utenteInput, @RequestHeader("Authorization") String username) {
        return ResponseEntity.ok(utenteService.inserisciNuovo(utenteInput));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUtente(@Valid @RequestBody User utenteInput, @PathVariable(required = true) Long id, @RequestHeader("Authorization") String username) {
        User utente = utenteService.caricaSingoloElemento(id);

        if (utente == null)
            throw new UtenteNotFoundException("Utente non trovato!");

        utenteInput.setId(id);
        return ResponseEntity.ok(utenteService.aggiorna(utenteInput));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUtente(@PathVariable(required = true) Long id, @RequestHeader("Authorization") String username) {
        User utente = utenteService.caricaSingoloElemento(id);

        if (utente == null)
            throw new UtenteNotFoundException("Utente non trovato!");

        utenteService.rimuovi(utente);
    }

    @PostMapping("/search")
    public ResponseEntity<List<User>> findByExample(@RequestHeader("Authorization") String username, @RequestBody User utente) {
        return ResponseEntity.ok(utenteService.findByExample(utente));
    }

}
