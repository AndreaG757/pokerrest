package com.projectpokerrest.pokerrest.service;

import com.projectpokerrest.pokerrest.model.Tavolo;
import com.projectpokerrest.pokerrest.repository.tavolo.TavoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TavoloServiceImpl implements TavoloService {

	@Autowired
	private TavoloRepository repository;

	@Override
	public List<Tavolo> listAllElements() {
		return (List<Tavolo>) repository.findAll();
	}

	@Override
	public Tavolo caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Tavolo aggiorna(Tavolo tavoloInstance) {
		return repository.save(tavoloInstance);
	}

	@Override
	public Tavolo inserisciNuovo(Tavolo tavoloInstance) {
		return repository.save(tavoloInstance);
	}

	@Override
	public void rimuovi(Tavolo tavoloInstance) {
		repository.delete(tavoloInstance);
	}

	@Override
	public List<Tavolo> findByExample(Tavolo example) {
		return repository.findByExample(example);
	}

	@Override
	public List<Tavolo> trovaTavoliByEsperienza(Double esperienza) {
		//return repository.findTavoloByEsperienzaMinima(esperienza);
		return null;
	}
}
