package com.projectpokerrest.pokerrest.web.api;

import com.projectpokerrest.pokerrest.model.Tavolo;
import com.projectpokerrest.pokerrest.service.TavoloService;
import com.projectpokerrest.pokerrest.service.UserService;
import com.projectpokerrest.pokerrest.web.api.exception.TavoloNotFoundException;
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
    private UserService utenteService;

    @GetMapping
    public ResponseEntity<List<Tavolo>> listAll() {
        return ResponseEntity.ok(tavoloService.listAllElements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tavolo> findById(@PathVariable(required = true) Long id) {
        Tavolo tavolo = tavoloService.caricaSingoloElemento(id);

        if (tavolo == null)
            throw new TavoloNotFoundException("Tavolo non trovato!");

        return ResponseEntity.ok(tavolo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Tavolo> createNewTavolo(@Valid @RequestBody Tavolo tavoloInput) {
        return ResponseEntity.ok(tavoloService.inserisciNuovo(tavoloInput));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tavolo> updateTavolo(@Valid @RequestBody Tavolo tavoloInput, @PathVariable(required = true) Long id) {
        tavoloInput.setId(id);
        return ResponseEntity.ok(tavoloService.aggiorna(tavoloInput));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTavolo(@PathVariable(required = true) Long id) {
        Tavolo tavolo = tavoloService.caricaSingoloElemento(id);
        tavoloService.rimuovi(tavolo);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Tavolo>> findByExample(@RequestBody Tavolo tavolo) {
        return ResponseEntity.ok(tavoloService.findByExample(tavolo));
    }

}
