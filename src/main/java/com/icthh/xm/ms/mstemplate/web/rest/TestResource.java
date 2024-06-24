package com.icthh.xm.ms.mstemplate.web.rest;

import com.icthh.xm.commons.topic.service.KafkaTemplateService;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping
public class TestResource {

    private final KafkaTemplateService kafkaTemplateService;
    private final Tracer tracer;
    private final ObservationRegistry registry;
    private final RestTemplate tracingRestTemplate;
    private final RestTemplateBuilder restTemplateBuilder;

    public TestResource(
        KafkaTemplateService kafkaTemplateService,
        Tracer tracer,
        ObservationRegistry observationRegistry,
        @Qualifier("tracingRestTemplate") RestTemplate tracingRestTemplate,
        RestTemplateBuilder restTemplateBuilder
    ) {
        this.kafkaTemplateService = kafkaTemplateService;
        this.tracer = tracer;
        this.registry = observationRegistry;
        this.tracingRestTemplate = tracingRestTemplate;
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @GetMapping("/kafka-send")
    public String test() {
        kafkaTemplateService.send("my-topic", "Hello world");
        return "Message sent";
    }

    @GetMapping("/rest1")
    public String rest1() {
        log.info("REST_1 was called here");
        RestTemplate restTemplate = tracingRestTemplate;
        String url = "http://localhost:8082/rest2";
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Tenant", "OM");
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class, new HashedMap());
        return "REST_1";
    }

    @GetMapping("/rest2")
    public String rest2() {
        log.info("REST_2 was called here");
        return "REST_2";
    }

    private RestTemplate createExternalRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ClientHttpRequestInterceptor sleuthInterceptor = new BasicAuthenticationInterceptor("", "");
        //        if (!sleuthInterceptor) {
        //            log.error("Could not get traceService from lepContext")
        //            return restTemplate;
        //        }
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>(restTemplate.getInterceptors());
        interceptors.add(0, sleuthInterceptor);
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}
