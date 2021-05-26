package com.projectpokerrest.pokerrest.service;

import com.projectpokerrest.pokerrest.model.Authority;
import com.projectpokerrest.pokerrest.model.User;

import java.util.List;

public interface UserService {

	List<User> listAllElements();

	User caricaSingoloElemento(Long id);

	User aggiorna(User utenteInstance);

	User inserisciNuovo(User utenteInstance);

	void rimuovi(User utenteInstance);

	List<User> findByExample(User example);

	void aggiungiRuolo(User utenteEsistente, Authority ruoloInstance);

	User trovaByUsername(String username);
}
