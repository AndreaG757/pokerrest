package com.projectpokerrest.pokerrest.service.utente;

import com.projectpokerrest.pokerrest.model.StatoUtente;
import com.projectpokerrest.pokerrest.model.Utente;
import com.projectpokerrest.pokerrest.repository.utente.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UtenteServiceImpl implements UtenteService {

	@Autowired
	private UtenteRepository repository;

	@Override
	public List<Utente> listAllUtenti() {
		return (List<Utente>) repository.findAll();
	}

	@Override
	public Utente caricaSingoloUtente(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Utente aggiorna(Utente utenteInstance) {
		return repository.save(utenteInstance);
	}

	@Override
	public Utente inserisciNuovo(Utente utenteInstance) {
		return repository.save(utenteInstance);
	}

	@Override
	public void rimuovi(Utente utenteInstance) {
		repository.delete(utenteInstance);
	}

	@Override
	public List<Utente> findByExample(Utente example) {
		return repository.findByExample(example);
	}

	@Override
	public Utente findByUsernameAndPassword(String username, String password) {
		return repository.findByUsernameAndPassword(username, password);
	}

	@Override
	public Utente eseguiAccesso(String username, String password) {
		return repository.findByUsernameAndPasswordAndStato(username, password, StatoUtente.ATTIVO);
	}

	@Override
	public Utente findByUsername(String username) {
		return repository.findByUsername(username).orElse(null);
	}

	@Override
	public void sottraiCredito(Utente utente, Double costoAnnuncio) {
		Double creditoResiduo = utente.getCreditoResiduo();
		creditoResiduo = creditoResiduo - costoAnnuncio;
		utente.setCreditoResiduo(creditoResiduo);
		repository.save(utente);
	}

	@Override
	public Utente caricaUtenteConRuoli(Long id) {
		return repository.findOneEagerRuoli(id).orElse(null);
	}

}
