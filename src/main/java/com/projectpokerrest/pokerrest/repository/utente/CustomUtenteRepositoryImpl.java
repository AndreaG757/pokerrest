package com.projectpokerrest.pokerrest.repository.utente;

import com.projectpokerrest.pokerrest.model.Utente;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomUtenteRepositoryImpl implements CustomUtenteRepository {
	
	@PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Utente> findByExample(Utente example) {
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		StringBuilder queryBuilder = new StringBuilder(
				"select u from Utente u left join fetch u.tavolo t left join fetch u.ruoli r where 1 = 1 ");

		if (StringUtils.isNotEmpty(example.getNome())) {
			whereClauses.add(" u.nome like :nome ");
			paramaterMap.put("nome", "%" + example.getNome() + "%");
		}
		if (StringUtils.isNotEmpty(example.getCognome())) {
			whereClauses.add(" u.cognome like :cognome ");
			paramaterMap.put("cognome", "%" + example.getCognome() + "%");
		}
		if (StringUtils.isNotEmpty(example.getUsername())) {
			whereClauses.add(" u.username like :username ");
			paramaterMap.put("username", "%" + example.getUsername() + "%");
		}
		if (example.getDateCreated() != null) {
			whereClauses.add(" u.dateCreated  >= :dateCreated ");
			paramaterMap.put("dateCreated", example.getDateCreated());
		}
		if (example.getStato() != null) {
			whereClauses.add(" u.stato  >= :stato ");
			paramaterMap.put("stato", example.getStato());
		}
		if (example.getEsperienzaAccumulata() != null) {
			whereClauses.add("u.esperienzaAccumulata >= :esperienzaAccumulata ");
			paramaterMap.put("esperienzaAccumulata", example.getEsperienzaAccumulata());
		}
		if (example.getCreditoResiduo() != null) {
			whereClauses.add("u.creditoResiduo >= :creditoResiduo ");
			paramaterMap.put("creditoResiduo", example.getCreditoResiduo());
		}
		if (example.getRuoli() != null && !example.getRuoli().isEmpty()) {
			whereClauses.add(" r in :ruoli ");
			paramaterMap.put("ruoli", example.getRuoli());
		}
		if (example.getTavolo() != null) {
			whereClauses.add(" t =:tavolo ");
			paramaterMap.put("tavolo", example.getTavolo());
		}

		queryBuilder.append(!whereClauses.isEmpty() ? " and " : "");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Utente> typedQuery = entityManager.createQuery(queryBuilder.toString(), Utente.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();
    }

}
