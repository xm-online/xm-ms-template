package com.icthh.xm.ms.mstemplate.config;

import com.icthh.xm.commons.config.XmCommonsApplicationProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties extends XmCommonsApplicationProperties {
    private String specificationPathPattern;
    private List<String> tenantIgnoredPathList = Collections.emptyList();
    private List<String> timelineIgnoredHttpMethods = Collections.emptyList();

    private boolean timelinesEnabled;
    private boolean kafkaEnabled;
    private boolean schedulerEnabled;

    private List<String> tenantWithCreationAccessList;
    private String kafkaSystemTopic;
    private String kafkaSystemQueue;
    private Integer kafkaMetadataMaxAge;
    private KafkaHealth kafkaHealth;
    private ConfigFetchAll configFetchAll;

    @Data
    public static class KafkaHealth {
        private Boolean enabled;
    }

    @Data
    public static class ConfigFetchAll {
        private Boolean enabled;
    }
}
