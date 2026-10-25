package com.icthh.xm.ms.mstemplate.config;

import com.icthh.xm.commons.security.RoleConstant;
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
    private final Prometheus prometheus = new Prometheus();
    private final Management management = new Management();
    private final TenantCache tenantCache = new TenantCache();
    private final TenantMemoryCache tenantMemoryCache = new TenantMemoryCache();

    @Data
    public static class KafkaHealth {
        private Boolean enabled;
    }

    @Data
    public static class ConfigFetchAll {
        private Boolean enabled;
    }

    @Data
    public static class Prometheus {
        /**
         * application.prometheus.public: /management/prometheus is available without authentication.
         */
        private boolean isPublic;
    }

    /**
     * application.tenant-cache.redis: Redis connection of xm-commons-cache-redis, bound by XmRedisCacheAutoConfiguration.
     * Strings here, so that the empty values of application.yml are bound.
     */
    @Data
    public static class TenantCache {
        private final Redis redis = new Redis();

        @Data
        public static class Redis {
            private String enabled;
            private String host;
            private String port;
            private String password;
            private String database;
        }
    }

    /**
     * application.tenant-memory-cache.enabled: caches of cache.yml, bound by XmTenantLepCacheConfig.
     * String here, so that the empty value of application.yml is bound.
     */
    @Data
    public static class TenantMemoryCache {
        private String enabled;
    }

    @Data
    public static class Management {
        private final Access access = new Access();
        private final Info info = new Info();

        @Data
        public static class Access {
            private String role = RoleConstant.SYSTEM_ADMIN;
        }

        @Data
        public static class Info {
            /**
             * application.management.info.public: /management/info is available without authentication.
             */
            private boolean isPublic;
        }
    }
}
