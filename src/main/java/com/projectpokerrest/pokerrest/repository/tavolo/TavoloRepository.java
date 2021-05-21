package com.projectpokerrest.pokerrest.repository.tavolo;

import com.projectpokerrest.pokerrest.model.Tavolo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TavoloRepository extends CrudRepository<Tavolo, Long>, CustomTavoloRepository {

    @Query("from Tavolo t left join fetch t.utenti u where t.id = :id")
    Tavolo findOneEager(Long id);

}
