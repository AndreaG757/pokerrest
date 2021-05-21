package com.projectpokerrest.pokerrest.service.ruolo;

import com.projectpokerrest.pokerrest.model.Ruolo;
import com.projectpokerrest.pokerrest.repository.ruolo.RuoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RuoloServiceImpl implements RuoloService {

	@Autowired
	private RuoloRepository repository;
	
	@Override
	public List<Ruolo> listAll() {
		return (List<Ruolo>) repository.findAll();
	}

	@Override
	public Ruolo caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public void aggiorna(Ruolo ruoloInstance) {
		repository.save(ruoloInstance);
	}

	@Override
	public void inserisciNuovo(Ruolo ruoloInstance) {
		repository.save(ruoloInstance);
	}

	@Override
	public void rimuovi(Ruolo ruoloInstance) {
		repository.delete(ruoloInstance);
	}

	@Override
	public Ruolo cercaPerDescrizioneECodice(String descrizione, String codice) {
		return repository.findByDescrizioneAndCodice(descrizione, codice);
	}

}
