package com.projectpokerrest.pokerrest.security.repository;

import com.projectpokerrest.pokerrest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {
	Optional<User> findByUsername(String username);

}