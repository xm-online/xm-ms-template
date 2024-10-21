package com.icthh.xm.ms.mstemplate.config;

import com.icthh.xm.commons.migration.db.config.DatabaseConfiguration;
import com.icthh.xm.commons.migration.db.tenant.SchemaResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@Configuration
@EnableJpaRepositories({"com.icthh.xm.ms.mstemplate.repository"})
public class XmDatabaseConfiguration extends DatabaseConfiguration {

    private static final String JPA_PACKAGES = "com.icthh.xm.ms.mstemplate.domain";

    public XmDatabaseConfiguration(Environment env,
                                 JpaProperties jpaProperties,
                                 SchemaResolver schemaResolver) {
        super(env, jpaProperties, schemaResolver);
    }

    @Override
    public String getJpaPackages() {
        return JPA_PACKAGES;
    }

}

