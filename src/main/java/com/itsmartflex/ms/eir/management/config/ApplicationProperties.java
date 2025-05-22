package com.itsmartflex.ms.eir.management.config;

import java.util.Collections;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private String specificationPathPattern;
    private List<String> tenantIgnoredPathList = Collections.emptyList();

    private boolean kafkaEnabled;
    private boolean schedulerEnabled;

    private String kafkaSystemTopic;
    private String kafkaSystemQueue;
    private Integer kafkaMetadataMaxAge;
    private KafkaHealth kafkaHealth;

    @Data
    public static class KafkaHealth {

        private Boolean enabled;
    }
}
