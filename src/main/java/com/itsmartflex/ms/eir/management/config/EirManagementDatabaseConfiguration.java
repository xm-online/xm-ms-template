package com.itsmartflex.ms.eir.management.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@Configuration
@EnableJpaRepositories({"com.itsmartflex.ms.eir.management.repository"})
public class EirManagementDatabaseConfiguration {

}

