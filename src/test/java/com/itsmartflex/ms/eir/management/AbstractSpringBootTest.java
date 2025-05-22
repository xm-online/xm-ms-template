package com.itsmartflex.ms.eir.management;

import com.itsmartflex.ms.eir.management.config.IntegrationTestConfiguration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = {
    IntegrationTestConfiguration.class,
    EirManagementApp.class,
})
@Tag("com.icthh.xm.ms.eir.management.AbstractSpringBootTest")
@ExtendWith(SpringExtension.class)
public abstract class AbstractSpringBootTest {

    // TODO: To speedup test:
    //      - find all cases which break Spring context like @MockBean and fix.
    //      - separate tests by categories: Unit, SpringBoot, WebMwc

}
