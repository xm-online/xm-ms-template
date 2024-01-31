package com.icthh.xm.ms.mstemplate.config;

import io.micrometer.tracing.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MicrometerConfiguration {

    @Bean
    public Tracer tracer() {
        return Tracer.NOOP;
    }
}
