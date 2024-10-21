package com.icthh.xm.ms.mstemplate;

import com.icthh.xm.ms.mstemplate.config.IntegrationTestConfiguration;
import com.icthh.xm.ms.mstemplate.config.TestLepConfiguration;
import com.icthh.xm.ms.mstemplate.config.XmDatabaseConfiguration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

@SpringBootTest(classes = {
        TestLepConfiguration.class,
        MstemplateApp.class,
        IntegrationTestConfiguration.class,
        XmDatabaseConfiguration.class
})
@Tag("com.icthh.xm.ms.mstemplate.AbstractSpringBootTest")
@ExtendWith(SpringExtension.class)
public abstract class AbstractSpringBootTest {

    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14.7");

    @DynamicPropertySource
    static void setupContainers(DynamicPropertyRegistry registry) {
        Startables.deepStart(postgreSQLContainer).join();
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    // TODO: To speedup test:
    //      - find all cases which break Spring context like @MockBean and fix.
    //      - separate tests by categories: Unit, SpringBoot, WebMwc

}
