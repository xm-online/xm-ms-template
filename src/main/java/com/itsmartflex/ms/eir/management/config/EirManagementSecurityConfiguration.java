package com.itsmartflex.ms.eir.management.config;

import com.icthh.xm.commons.permission.access.XmPermissionEvaluator;
import com.icthh.xm.commons.security.jwt.TokenProvider;
import com.icthh.xm.commons.security.spring.config.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;

@Configuration
public class EirManagementSecurityConfiguration extends SecurityConfiguration {

    public EirManagementSecurityConfiguration(TokenProvider tokenProvider,
        @Value("${jhipster.security.content-security-policy}") String contentSecurityPolicy) {
        super(tokenProvider, contentSecurityPolicy);
    }

    @Bean
    static MethodSecurityExpressionHandler expressionHandler(XmPermissionEvaluator xmPermissionEvaluator) {
        var expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(xmPermissionEvaluator);
        return expressionHandler;
    }
}
