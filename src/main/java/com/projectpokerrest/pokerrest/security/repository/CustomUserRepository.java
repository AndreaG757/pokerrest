package com.projectpokerrest.pokerrest.security.repository;

import com.projectpokerrest.pokerrest.model.Authority;
import com.projectpokerrest.pokerrest.model.User;

import java.util.List;

public interface CustomUserRepository {

	void aggiungiRuolo(User utenteEsistente, Authority ruoloInstance);

	List<User> findByExample(User example);

}
