package com.icthh.xm.ms.mstemplate.config;

import com.icthh.xm.commons.lep.TenantScriptStorage;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private String specificationPathPattern;
    private List<String> tenantIgnoredPathList = Collections.emptyList();
    private List<String> timelineIgnoredHttpMethods = Collections.emptyList();

    private final Lep lep = new Lep();
    private final Retry retry = new Retry();
    private String dbSchemaSuffix;

    private boolean timelinesEnabled;
    private boolean kafkaEnabled;
    private boolean schedulerEnabled;

    private List<String> tenantWithCreationAccessList;
    private String kafkaSystemTopic;
    private String kafkaSystemQueue;
    private Integer kafkaMetadataMaxAge;
    private KafkaHealth kafkaHealth;

    private KafkaMetric kafkaMetric;
    private DomainEvent domainEvent;

    @Data
    public static class KafkaHealth {
        private Boolean enabled;
    }

    @Getter
    @Setter
    public static class KafkaMetric {
        private boolean enabled;
        private int connectionTimeoutTopic;
        List<String> metricTopics;
    }

    @Getter
    @Setter
    private static class Retry {
        private int maxAttempts;
        private long delay;
        private int multiplier;
    }

    @Getter
    @Setter
    public static class Lep {
        private TenantScriptStorage tenantScriptStorage;
        private String lepResourcePathPattern;
        private Boolean warmupScripts;
        private List<String> tenantsWithLepWarmup;
    }

    @Getter
    @Setter
    public static class DomainEvent {
        private boolean enabled;
    }

}
