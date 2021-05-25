package com.projectpokerrest.pokerrest.security.repository;

import com.projectpokerrest.pokerrest.model.Authority;
import com.projectpokerrest.pokerrest.model.AuthorityName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	Authority findByName(AuthorityName name);

}