//package com.icthh.xm.ms.mstemplate.web.rest;
//
//import com.icthh.xm.commons.migration.db.jsonb.CustomExpression;
//import com.icthh.xm.commons.topic.service.KafkaTemplateService;
//import com.icthh.xm.ms.mstemplate.domain.ExampleEntityFirst;
//import com.icthh.xm.ms.mstemplate.domain.ExampleEntitySecond;
//import com.icthh.xm.ms.mstemplate.domain.ExampleEntitySecond_;
//import com.icthh.xm.ms.mstemplate.repository.ExampleEntityFirstRepository;
//import com.icthh.xm.ms.mstemplate.repository.ExampleEntitySecondRepository;
//import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
//import io.micrometer.observation.ObservationRegistry;
//import io.micrometer.tracing.Tracer;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//import jakarta.persistence.EntityManager;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.collections.map.HashedMap;
//import org.hibernate.SessionFactory;
//import org.hibernate.type.descriptor.jdbc.JsonJdbcType;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.actuate.info.InfoContributor;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.client.ClientHttpRequestInterceptor;
//import org.springframework.http.client.support.BasicAuthenticationInterceptor;
//import org.springframework.kafka.retrytopic.RetryTopicConfiguration;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//import javax.persistence.PersistenceContext;
//
//import static com.icthh.xm.commons.migration.db.jsonb.CustomDialect.JSON_QUERY;
//import static com.icthh.xm.commons.migration.db.jsonb.CustomPostgreSQLDialect.TO_JSON_B;
//import static com.icthh.xm.commons.migration.db.jsonb.CustomPostgreSQLDialect.TO_JSON_B_TEXT;
//
//
//@Slf4j
//@RestController
//@RequestMapping(value = "/test")
//public class TestResource {
//
//    private final KafkaTemplateService kafkaTemplateService;
//    private final RestTemplate tracingRestTemplate;
//    private final ExampleEntityFirstRepository exampleEntityFirstRepository;
//    private final ExampleEntitySecondRepository exampleEntitySecondRepository;
//    private static SessionFactory sessionFactory = null;
//    @PersistenceContext
//    private final EntityManager entityManager;
//    private final CustomExpression customExpression;
//
//    public TestResource(
//        KafkaTemplateService kafkaTemplateService,
//        Tracer tracer,
//        ObservationRegistry observationRegistry,
//        @Qualifier("tracingRestTemplate") RestTemplate tracingRestTemplate,
//        RestTemplateBuilder restTemplateBuilder,
//        ExampleEntityFirstRepository exampleEntityFirstRepository,
//        ExampleEntitySecondRepository exampleEntitySecondRepository,
//        EntityManager entityManager,
//        @Qualifier("buildInfoContributor") InfoContributor buildInfoContributor, CustomExpression customExpression, Set<RetryTopicConfiguration> retryTopicConfigurations) {
//        this.kafkaTemplateService = kafkaTemplateService;
//        this.tracingRestTemplate = tracingRestTemplate;
//        this.exampleEntityFirstRepository = exampleEntityFirstRepository;
//        this.exampleEntitySecondRepository = exampleEntitySecondRepository;
//        this.entityManager = entityManager;
//        this.customExpression = customExpression;
//    }
//
//    @GetMapping("/jsonb-search-jdbc-type-text")
//    public List<ExampleEntitySecond> jsonbSearchJdbcTypeText() {
//        return exampleEntitySecondRepository.findAll(Specification.where((root, query, cb) -> {
//            return cb.equal(
//                customExpression.jsonQuery(cb, root, ExampleEntitySecond_.DATA, "$.key2", JsonJdbcType.class),
//                cb.function(TO_JSON_B_TEXT, JsonJdbcType.class, cb.literal("5.0"))
//            );
//        }));
//    }
//
//    @GetMapping("/jsonb-search-jdbc-no-type")
//    public List<ExampleEntitySecond> jsonbSearchJdbcType() {
//        return exampleEntitySecondRepository.findAll(Specification.where((root, query, cb) -> {
//            return cb.equal(
//                customExpression.jsonQuery(cb, root, ExampleEntitySecond_.DATA, "$.key2", JsonJdbcType.class),
//                customExpression.toJsonB(cb, cb.literal(5.0), JsonJdbcType.class)
////                cb.function(TO_JSON_B, JsonJdbcType.class, cb.literal(5.0))
//            );
//        }));
//    }
//
//    @GetMapping("/jsonb-search-binary")
//    public List<ExampleEntitySecond> jsonbSearchBinary() {
//        return exampleEntitySecondRepository.findAll(Specification.where((root, query, cb) -> {
//            return cb.equal(
//                customExpression.jsonQuery(cb, root, ExampleEntitySecond_.DATA, "$.key", JsonBinaryType.class),
//                cb.function(TO_JSON_B, JsonBinaryType.class, cb.literal("value"))
//            );
//        }));
//    }
//
//    @GetMapping("/jsonb-search")
//    public List<ExampleEntitySecond> jsonbSearch() {
//        return exampleEntitySecondRepository.findAll(Specification.where((root, query, cb) -> {
//            return cb.and(
//                cb.equal(root.get(ExampleEntitySecond_.NAME), "name5"),
//                cb.isNotNull(
//                    cb.function(
//                        JSON_QUERY,
//                        JsonJdbcType.class,
//                        root.get(ExampleEntitySecond_.DATA),
//                        cb.literal("$.key")
//                    )
//                ),
//                cb.equal(
//                    cb.function(JSON_QUERY, JsonJdbcType.class, root.get(ExampleEntitySecond_.DATA), cb.literal("$.key")),
//                    cb.function(TO_JSON_B_TEXT, JsonJdbcType.class, cb.literal("value"))
//                )
//            );
//        }));
//    }
//
////    @GetMapping("/jsonb-data-key")
////    public List<ExampleEntitySecond> findByDataKey() {
////        return exampleEntitySecondRepository.findByDataKey(Set.of(),  "value", "value1", "mane12");
////    }
//
//    @GetMapping("/jsonb")
//    public void jsonb() {
////        exampleEntitySecondRepository.jsonb_path_query_first();
////        exampleEntitySecondRepository.json_query();
////        exampleEntitySecondRepository.to_jsonb();
////        exampleEntitySecondRepository.to_json_b();
////        exampleEntitySecondRepository.jsonb_extract_path_text();
////        exampleEntitySecondRepository.jsonb_to_string();
//    }
//
//    @GetMapping("/kafka-send")
//    public String test() {
//        kafkaTemplateService.send("mytopic", "Hello world");
////        throw new HttpServerErrorException(HttpStatusCode.valueOf(500));
//        return "Message sent";
//    }
//
//    @GetMapping("/rest1")
//    public String rest1() {
//        log.info("REST_1 was called here");
//        RestTemplate restTemplate = tracingRestTemplate;
//        String url = "http://localhost:8082/test/rest2";
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("X-Tenant", "OM");
//        HttpEntity<Void> request = new HttpEntity<>(headers);
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class, new HashedMap());
//        return "REST_1";
//    }
//
//    @GetMapping("/rest2")
//    public String rest2() {
//        log.info("REST_2 was called here");
//        return "REST_2";
//    }
//
//    @GetMapping("/db-save")
//    public String entity() {
//        log.info("ENTITY DB SAVE");
////        TestEntity testEntity = new TestEntity(1L, "name", "entity");
////        entityManager.persist(testEntity);
//        ExampleEntityFirst entity = new ExampleEntityFirst();
//        exampleEntityFirstRepository.save(entity);
//        return "ENTITY DB SAVE";
//    }
//
////    @GetMapping("/db-save")
////    public String dbSave() {
////        log.info("DB SAVE");
////        setup();
////
////        try (Session session = sessionFactory.withOptions()
//////            .interceptor(databaseSourceInterceptor)
////            .openSession()) {
////            session.beginTransaction();
////
//////            String insertSql = String.format("INSERT INTO products (name,cost) VALUES('%s',%s);", product.getName(), product.getCost());
//////            session.createQuery(insertSql).executeUpdate();
//////            session.getTransaction().commit();
////
////            EmployeeEntity employee = new EmployeeEntity();
//////            employee.setFirstName("Lokesh");
//////            employee.setLastName("Gupta");
//////            employee.setEmail("howtodoinjava@gmail.com");
////
////            //Save here
////            session.save(employee);
//////            session.flush();
////
////            //Update here
//////            employee.setFirstName("Akash");
////
////            session.getTransaction().commit();
////        }
//////        exampleEntityFirstRepository.save(new ExampleEntityFirst());
////        return "DB_SAVE";
////    }
////
////    private void setup() {
////        try {
////            StandardServiceRegistry standardRegistry
////                = new StandardServiceRegistryBuilder()
////                .configure("hibernate-test.cfg.xml")
////                .build();
////
////            Metadata metadata = new MetadataSources(standardRegistry)
////                .addAnnotatedClass(EmployeeEntity.class)
////                .getMetadataBuilder()
////                .build();
////
////            sessionFactory = metadata
////                .getSessionFactoryBuilder()
////                .applyInterceptor(databaseSourceInterceptor)
////                .build();
////
////        } catch (Throwable ex) {
////            throw new ExceptionInInitializerError(ex);
////        }
////    }
//
//    private RestTemplate createExternalRestTemplate() {
//        RestTemplate restTemplate = new RestTemplate();
//        ClientHttpRequestInterceptor sleuthInterceptor = new BasicAuthenticationInterceptor("", "");
//        //        if (!sleuthInterceptor) {
//        //            log.error("Could not get traceService from lepContext")
//        //            return restTemplate;
//        //        }
//        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>(restTemplate.getInterceptors());
//        interceptors.add(0, sleuthInterceptor);
//        restTemplate.setInterceptors(interceptors);
//        return restTemplate;
//    }
//}
