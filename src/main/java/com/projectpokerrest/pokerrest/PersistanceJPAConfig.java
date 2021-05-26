package com.projectpokerrest.pokerrest;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.projectpokerrest.pokerrest.repository", "com.projectpokerrest.pokerrest.security.repository"})
public class PersistanceJPAConfig {

}
