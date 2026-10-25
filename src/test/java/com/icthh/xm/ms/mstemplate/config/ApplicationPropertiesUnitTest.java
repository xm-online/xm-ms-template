package com.icthh.xm.ms.mstemplate.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationPropertiesUnitTest {

    @Test
    public void testManagementDefaults() {
        ApplicationProperties properties = bind(Map.of("application.kafka-enabled", "false"));

        assertFalse(properties.getPrometheus().isPublic());
        assertEquals("SYSTEM-ADMIN", properties.getManagement().getAccess().getRole());
        assertFalse(properties.getManagement().getInfo().isPublic());
    }

    @Test
    public void testPropertiesOfXmCommonsAreBound() {
        ApplicationProperties properties = bind(Map.of(
            "application.prometheus.public", "true",
            "application.management.access.role", "SUPER-ADMIN",
            "application.management.info.public", "true",
            "application.config-private-key", "key"));

        assertTrue(properties.getPrometheus().isPublic());
        assertEquals("SUPER-ADMIN", properties.getManagement().getAccess().getRole());
        assertTrue(properties.getManagement().getInfo().isPublic());
        assertEquals("key", properties.getConfigPrivateKey());
    }

    @Test
    public void testTenantCacheRedisPropertiesAreBound() {
        ApplicationProperties properties = bind(Map.of(
            "application.tenant-cache.redis.enabled", "true",
            "application.tenant-cache.redis.host", "redis",
            "application.tenant-cache.redis.port", "6379",
            "application.tenant-cache.redis.password", "secret",
            "application.tenant-cache.redis.database", "0"));

        ApplicationProperties.TenantCache.Redis redis = properties.getTenantCache().getRedis();
        assertEquals("true", redis.getEnabled());
        assertEquals("redis", redis.getHost());
        assertEquals("6379", redis.getPort());
        assertEquals("secret", redis.getPassword());
        assertEquals("0", redis.getDatabase());
    }

    @Test
    public void testTenantMemoryCachePropertyIsBound() {
        ApplicationProperties properties = bind(Map.of("application.tenant-memory-cache.enabled", "true"));

        assertEquals("true", properties.getTenantMemoryCache().getEnabled());
    }

    @Test
    public void testEmptyValuesOfApplicationYmlAreBound() {
        new ApplicationContextRunner()
            .withUserConfiguration(EnableApplicationProperties.class)
            .withPropertyValues(
                "application.tenant-cache.redis.enabled=",
                "application.tenant-cache.redis.host=",
                "application.tenant-cache.redis.port=",
                "application.tenant-cache.redis.password=",
                "application.tenant-cache.redis.database=",
                "application.tenant-memory-cache.enabled=")
            .run(context -> {
                assertNull(context.getStartupFailure());
                assertEquals("", context.getBean(ApplicationProperties.class).getTenantCache().getRedis().getPort());
            });
    }

    @EnableConfigurationProperties(ApplicationProperties.class)
    static class EnableApplicationProperties {
    }

    private static ApplicationProperties bind(Map<String, String> values) {
        return new Binder(new MapConfigurationPropertySource(values))
            .bind("application", ApplicationProperties.class)
            .get();
    }
}
