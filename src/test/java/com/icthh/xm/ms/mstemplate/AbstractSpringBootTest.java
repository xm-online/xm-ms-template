package com.icthh.xm.ms.mstemplate;

import com.icthh.xm.commons.lep.config.LepConfiguration;
import com.icthh.xm.ms.mstemplate.config.IntegrationTestConfiguration;
import com.icthh.xm.ms.mstemplate.config.TestLepConfiguration;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {
        MstemplateApp.class,
        LepConfiguration.class,
        IntegrationTestConfiguration.class,
        TestLepConfiguration.class,
})
@Category(AbstractSpringBootTest.class)
@RunWith(SpringRunner.class)
public abstract class AbstractSpringBootTest {

    // TODO: To speedup test:
    //      - find all cases which break Spring context like @MockBean and fix.
    //      - separate tests by categories: Unit, SpringBoot, WebMwc

}
