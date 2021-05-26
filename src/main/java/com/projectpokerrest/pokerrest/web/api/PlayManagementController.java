package com.projectpokerrest.pokerrest.web.api;

import com.projectpokerrest.pokerrest.model.Tavolo;
import com.projectpokerrest.pokerrest.model.User;
import com.projectpokerrest.pokerrest.service.TavoloService;
import com.projectpokerrest.pokerrest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/playmanagement")
public class PlayManagementController {

    @Autowired
    private TavoloService tavoloService;

    @Autowired
    private UserService utenteService;

    @PutMapping("/compracredito")
    public ResponseEntity<User> compraCredito(@RequestBody(required = true) Double credito) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User utente = utenteService.trovaByUsername(username);
        utente.setCreditoResiduo(utente.getCreditoResiduo() + credito);
        return ResponseEntity.ok(utenteService.aggiorna(utente));
    }

    @GetMapping("/ultimagiocata")
    public ResponseEntity<Tavolo> getUltimaGiocata() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User utente = utenteService.trovaByUsername(username);
        return ResponseEntity.ok(utente.getTavolo());
    }

    @PutMapping("/abbandonapartita")
    public ResponseEntity<User> abbandonaPartita() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User utente = utenteService.trovaByUsername(username);
        utente.setTavolo(null);
        utente.setEsperienzaAccumulata(utente.getEsperienzaAccumulata() + 1);
        return ResponseEntity.ok(utenteService.aggiorna(utente));
    }

    @GetMapping("/ricercatavoli")
    public ResponseEntity<List<Tavolo>> ricercaTavolo() throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User utente = utenteService.trovaByUsername(username);
        List<Tavolo> tavoliRicerca = new ArrayList<>();
        List<Tavolo> tavoli = tavoloService.listAllElements();
        for (Tavolo tavoloItem : tavoli) {
            if (tavoloItem.getEsperienzaMin() <= utente.getEsperienzaAccumulata())
                tavoliRicerca.add(tavoloItem);
            else
                throw new Exception("Esperienza minima richiesta non adatta!");
        }
        return ResponseEntity.ok(tavoliRicerca);
    }

    @PostMapping("/gioca/{id}")
    public ResponseEntity<String> gioca(@PathVariable(required = true) Long id) throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        User utente = utenteService.trovaByUsername(username);
        Tavolo tavolo = tavoloService.caricaSingoloElemento(id);
        tavolo.getUsers().add(utente);

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
        utente.setCreditoResiduo(utente.getCreditoResiduo() + tot);

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
