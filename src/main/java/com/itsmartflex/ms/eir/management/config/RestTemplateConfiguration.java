package com.itsmartflex.ms.eir.management.config;

import com.icthh.xm.commons.config.client.config.XmRestTemplateConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration extends XmRestTemplateConfiguration {

    @Bean
    public RestTemplate eirRestTemplate() {
        return new RestTemplateBuilder().build();
    }
}
