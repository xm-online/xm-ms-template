package com.icthh.xm.ms.mstemplate;

import com.icthh.xm.commons.web.spring.config.JacksonConfiguration;
import com.icthh.xm.ms.mstemplate.config.AsyncSyncConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { MstemplateApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class })
public @interface IntegrationTest {
}
