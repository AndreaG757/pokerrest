package com.projectpokerrest.pokerrest.web.api;

import com.projectpokerrest.pokerrest.model.Tavolo;
import com.projectpokerrest.pokerrest.model.Utente;
import com.projectpokerrest.pokerrest.service.tavolo.TavoloService;
import com.projectpokerrest.pokerrest.service.utente.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/playmanagement")
public class PlayManagementController {

    @Autowired
    private TavoloService tavoloService;

    @Autowired
    private UtenteService utenteService;

    @PutMapping("/compracredito")
    public ResponseEntity<Utente> compraCredito(@RequestHeader("Authorization") String username, @RequestBody(required = true) Double credito) {
        Utente utente = utenteService.findByUsername(username);
        utente.setCreditoResiduo(utente.getCreditoResiduo() + credito);
        return ResponseEntity.ok(utenteService.aggiorna(utente));
    }

    @GetMapping("/ultimagiocata")
    public ResponseEntity<Tavolo> getUltimaGiocata(@RequestHeader("Authorization") String username) {
        Utente utente = utenteService.findByUsername(username);
        return ResponseEntity.ok(utente.getTavolo());
    }

    @PutMapping("/abbandonapartita")
    public ResponseEntity<Utente> abbandonaPartita(@RequestHeader("Authorization") String username) {
        Utente utente = utenteService.findByUsername(username);
        utente.setTavolo(null);
        utente.setEsperienzaAccumulata(utente.getEsperienzaAccumulata() + 1);
        return ResponseEntity.ok(utenteService.aggiorna(utente));
    }

    @GetMapping("/ricercatavoli")
    public ResponseEntity<List<Tavolo>> ricercaTavolo(@RequestHeader("Authorization") String username) throws Exception {
        Utente utente = utenteService.findByUsername(username);
        List<Tavolo> tavoliRicerca = new ArrayList<>();
        List<Tavolo> tavoli = tavoloService.listAllTavolo();
        for (Tavolo tavoloItem : tavoli) {
            if (tavoloItem.getEsperienzaMin() <= utente.getEsperienzaAccumulata())
                tavoliRicerca.add(tavoloItem);
            else
                throw new Exception("Esperienza minima richiesta non adatta!");
        }
        return ResponseEntity.ok(tavoliRicerca);
    }

    @PostMapping("/gioca/{id}")
    public ResponseEntity<String> gioca(@PathVariable(required = true) Long id, @RequestHeader("Authorization") String username) throws Exception {

        Utente utente = utenteService.findByUsername(username);
        Tavolo tavolo = tavoloService.caricaSingoloTavoloConUtenti(id);
        tavolo.getUtenti().add(utente);

        if (tavolo.getEsperienzaMin() > utente.getEsperienzaAccumulata())
            throw new Exception("Esperienza minima richiesta non sufficiente! Chiedi a Lorenzo di farti entrare");
        if (tavolo.getCifraMinima() > utente.getCreditoResiduo())
            throw new Exception("Credito non sufficiente!");

        utente.setCreditoResiduo(utente.getCreditoResiduo() - tavolo.getCifraMinima());

        Double segno = Math.random();
        Integer somma = (int) (Math.random() * 1000);
        Double tot = null;
        String messaggio = null;

        if (segno >= 0.5) {
            segno = 1D;
        } else if (segno <= 0.5) {
            segno = -1D;
        }
        tot = segno * somma;
        utente.setCreditoAccumulato(utente.getCreditoAccumulato() + tot);

        if (tot <= 0) {
            messaggio = "Hai perso!";
            if (utente.getCreditoResiduo() < 0) {
                messaggio = "Hai esaurito il credito!";
                utente.setCreditoResiduo(0D);
                utente.setTavolo(null);
            }
            utenteService.aggiorna(utente);
        } else {
            messaggio = "Hai vinto!";
            utenteService.aggiorna(utente);
        }

        return ResponseEntity.ok(messaggio);

    }

}
