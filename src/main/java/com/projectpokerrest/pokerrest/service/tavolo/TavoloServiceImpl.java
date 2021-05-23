package com.projectpokerrest.pokerrest.service.tavolo;

import com.projectpokerrest.pokerrest.model.Tavolo;
import com.projectpokerrest.pokerrest.repository.tavolo.TavoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TavoloServiceImpl implements TavoloService {

    @Autowired
    private TavoloRepository repository;

    @Override
    public List<Tavolo> listAllTavolo() {
        return (List<Tavolo>) repository.findAll();
    }

    @Override
    public Tavolo caricaSingoloTavolo(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Tavolo caricaSingoloTavoloConUtenti(Long id) {
        return repository.findOneEager(id);
    }

    @Override
    public Tavolo aggiorna(Tavolo tavoloInstance) {
        if (!repository.findOneEager(tavoloInstance.getId()).getUtenti().isEmpty()) {
            throw new RuntimeException("Ci sono ancora dei giocatori al tavolo.");
        }

        return repository.save(tavoloInstance);
    }

    @Override
    public Tavolo inserisciNuovo(Tavolo tavoloInstance) {
        return repository.save(tavoloInstance);
    }

    @Override
    public void rimuovi(Tavolo tavoloInstance) {
        if (!repository.findOneEager(tavoloInstance.getId()).getUtenti().isEmpty()) {
            throw new RuntimeException("Ci sono ancora dei giocatori al tavolo.");
        }

        repository.delete(tavoloInstance);
    }

    @Override
    public List<Tavolo> findByExample(Tavolo tavoloInstance) {
        return repository.findByExample(tavoloInstance);
    }

}
