package com.icthh.xm.ms.mstemplate.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
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
    private String configPrivateKey;
    private final ApiDocs apiDocs = new ApiDocs();
    private final Prometheus prometheus = new Prometheus();
    private final Management management = new Management();
    private final TenantCache tenantCache = new TenantCache();
    private final TenantMemoryCache tenantMemoryCache = new TenantMemoryCache();
    private final XmAutoConfigurationFilter xmAutoConfigurationFilter = new XmAutoConfigurationFilter();

    @Data
    public static class KafkaHealth {
        private Boolean enabled;
    }

    @Data
    public static class ConfigFetchAll {
        private Boolean enabled;
    }

    @Data
    public static class ApiDocs {
        private boolean privilegeCheck;
    }

    @Data
    public static class Prometheus {
        private boolean isPublic;
    }

    @Data
    public static class TenantCache {
        private final Redis redis = new Redis();

        @Data
        public static class Redis {
            private String enabled;
            private String host;
            private String port;
            private String username;
            private String password;
            private String database;
            private String ssl;
        }
    }

    @Data
    public static class TenantMemoryCache {
        private String enabled;
    }

    @Data
    public static class XmAutoConfigurationFilter {
        private boolean disabled;
    }

    @Data
    public static class Management {
        private final Access access = new Access();
        private final Info info = new Info();

        @Data
        public static class Access {
            private String role;
        }

        @Data
        public static class Info {
            private boolean isPublic;
        }
    }
}
