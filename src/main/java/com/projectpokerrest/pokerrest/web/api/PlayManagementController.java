package com.projectpokerrest.pokerrest.web.api;

import com.projectpokerrest.pokerrest.model.Tavolo;
import com.projectpokerrest.pokerrest.model.Utente;
import com.projectpokerrest.pokerrest.service.ruolo.RuoloService;
import com.projectpokerrest.pokerrest.service.tavolo.TavoloService;
import com.projectpokerrest.pokerrest.service.utente.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@RestController
@RequestMapping("api/playmanagement")
public class PlayManagementController {

    @Autowired
    private TavoloService tavoloService;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private RuoloService ruoloService;

    @PutMapping("/compracredito/{credito}")
    public ResponseEntity<Utente> compraCredito(@RequestHeader("Authorization") String username, @PathVariable(required = true) Double credito) {
        Utente utente = utenteService.findByUsername(username);
        Double creditoAttuale = utente.getCreditoResiduo();
        utente.setCreditoResiduo(credito + creditoAttuale);
        return ResponseEntity.ok(utenteService.aggiorna(utente));
    }

    @GetMapping("/ultimagiocata")
    public ResponseEntity<Stream<Object>> getUltimaGiocata(@RequestHeader("Authorization") String username) {
        Utente utente = utenteService.findByUsername(username);
        List<Tavolo> tavoli = tavoloService.listAllTavolo();
        AtomicReference<Tavolo> tavoloTemp = null;
        /*for (Tavolo tavoloItem : tavoli) {
            if (tavoloItem.getUtenti().contains(utente)) {
                if (tavoloItem.getDataCreazione().after(tavoloItem.getDataCreazione())) {
                    tavoloTemp = tavoloItem;
                }
            }
        }*/
        return null;
    }

}
