package com.scouting.apigateway.filter;

import com.scouting.apigateway.utils.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RoleFilterConfig {
    private final WebClient.Builder webClientBuilder;

    public RoleFilterConfig(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Bean
    public RoleCheckFilter adminRoleCheckFilter() {
        return new RoleCheckFilter(webClientBuilder, new RoleCheckFilter.Config(UserRole.ADMIN));
    }

    @Bean
    public RoleCheckFilter leaderRoleCheckFilter() {
        return new RoleCheckFilter(webClientBuilder, new RoleCheckFilter.Config(UserRole.LEADER));
    }

    @Bean
    public RoleCheckFilter memberRoleCheckFilter() {
        return new RoleCheckFilter(webClientBuilder, new RoleCheckFilter.Config(UserRole.MEMBER));
    }
}

