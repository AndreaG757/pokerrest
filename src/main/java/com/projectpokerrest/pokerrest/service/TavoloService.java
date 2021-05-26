package com.projectpokerrest.pokerrest.service;

import com.projectpokerrest.pokerrest.model.Tavolo;

import java.util.List;

public interface TavoloService {

	List<Tavolo> listAllElements();

	Tavolo caricaSingoloElemento(Long id);

	Tavolo aggiorna(Tavolo tavoloInstance);

	Tavolo inserisciNuovo(Tavolo tavoloInstance);

	void rimuovi(Tavolo tavoloInstance);

	List<Tavolo> findByExample(Tavolo example);

	List<Tavolo> trovaTavoliByEsperienza(Double esperienza);
}
