package com.scouting.apigateway;

import com.scouting.apigateway.filter.RoleCheckFilter;
import com.scouting.apigateway.utils.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class RoutingConfig {

    @Autowired
    private RoleCheckFilter leaderRoleCheckFilter;

    @Autowired
    private RoleCheckFilter adminRoleCheckFilter;

    @Autowired
    private RoleCheckFilter memberRoleCheckFilter;
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {

        return builder.routes()
                .route(p -> p
                        .path("/activities/getAll/**", "/activities/register/**")
                        .filters(f -> f.filter(memberRoleCheckFilter.apply(memberRoleCheckFilter.getConfig())))
                        .uri("http://activity-service:8081")
                ).route(p -> p
                        .method(HttpMethod.POST)
                        .and()
                        .path("/activities")
                        .filters(f -> f.filter(adminRoleCheckFilter.apply(adminRoleCheckFilter.getConfig())))
                        .uri("http://activity-service:8081")
                ).route(p -> p
                        .path("/activities/planning/**")
                        .filters(f -> f.filter(leaderRoleCheckFilter.apply(leaderRoleCheckFilter.getConfig())))
                        .uri("http://activity-planning-service:8084")
                ).route(p -> p
                        .path("/users/**")
                        .filters(f -> f.filter(adminRoleCheckFilter.apply(adminRoleCheckFilter.getConfig())))
                        .uri("http://user-service:8082")
                ).build();
    }
}
