package com.icthh.xm.ms.mstemplate.config;

import com.icthh.xm.commons.permission.access.XmPermissionEvaluator;
import com.icthh.xm.commons.security.jwt.JWTConfigurer;
import com.icthh.xm.commons.security.jwt.TokenProvider;
import com.icthh.xm.commons.security.spring.config.SecurityConfiguration;
import com.icthh.xm.commons.security.spring.config.UnauthorizedEntryPoint;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

import static com.icthh.xm.commons.security.RoleConstant.SUPER_ADMIN;

@Configuration
public class TemplateSecurityConfiguration
//    extends SecurityConfiguration
{

    private final String contentSecurityPolicy;
    private final TokenProvider tokenProvider;

    public TemplateSecurityConfiguration(TokenProvider tokenProvider,
                                         @Value("${jhipster.security.content-security-policy}") String contentSecurityPolicy) {
//        super(tokenProvider, contentSecurityPolicy);
        this.tokenProvider = tokenProvider;
        this.contentSecurityPolicy = contentSecurityPolicy;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .csrf(AbstractHttpConfigurer::disable)
            .headers(headers -> headers
                .contentSecurityPolicy(csp -> csp.policyDirectives(contentSecurityPolicy))
                .referrerPolicy(referrer -> referrer
                    .policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                )
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::deny)
            )
            .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        applyUrlSecurity(http);
        http.with(securityConfigurerAdapter(), Customizer.withDefaults());
        http.exceptionHandling(exceptionHandler ->
            exceptionHandler.authenticationEntryPoint(new UnauthorizedEntryPoint())
        );
        return http.build();
        // @formatter:on
    }

    @SneakyThrows
    protected HttpSecurity applyUrlSecurity(HttpSecurity http) {
        // @formatter:off
        return http
            .authorizeHttpRequests(auth ->
                auth.requestMatchers("/api/profile-info").permitAll()
                    .requestMatchers("/api/**").authenticated()
                    .requestMatchers("/test/**").permitAll()
                    .requestMatchers("/api/admin/**").hasAuthority(SUPER_ADMIN)
                    .requestMatchers("/management/health").permitAll()
                    .requestMatchers("/management/info").permitAll()
                    .requestMatchers("/management/prometheus").permitAll()
                    .requestMatchers("/management/prometheus/**").permitAll()
                    .requestMatchers("/management/**").hasAuthority(SUPER_ADMIN)
                    .requestMatchers("/swagger-resources/configuration/ui").permitAll()
            );
        // @formatter:on
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }

    @Primary
    @Bean
    static MethodSecurityExpressionHandler expressionHandler(XmPermissionEvaluator customPermissionEvaluator) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(customPermissionEvaluator);
        return expressionHandler;
    }
}
