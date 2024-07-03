package com.scouting.apigateway.filter;

import com.scouting.apigateway.utils.UserRole;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import com.fasterxml.jackson.databind.ObjectMapper;


public class RoleCheckFilter extends AbstractGatewayFilterFactory<RoleCheckFilter.Config> implements Ordered{

    private final WebClient webClient;
    private final Config config;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RoleCheckFilter(WebClient.Builder webClientBuilder, Config config) {
        super(Config.class);
        this.webClient = webClientBuilder.baseUrl("http://user-service:8082").build();
        this.config = config;
    }

    public static class Config {
        private UserRole requiredRole;

        public Config(UserRole requiredRole) {
            this.requiredRole = requiredRole;
        }

        public UserRole getRequiredRole() {
            return requiredRole;
        }

        public void setRequiredRole(UserRole requiredRole) {
            this.requiredRole = requiredRole;
        }
    }

    public void setConfigRole(UserRole role){
        config.setRequiredRole(role);
    }

    public Config getConfig(){
        return config;
    }


    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || authHeader.isEmpty()) {
                return createErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "Missing or empty Authorization header");
                //return exchange.getResponse().setComplete();
            }

            return webClient.get()
                    .uri("/validateRole?role=" + config.getRequiredRole().name())
                    .header(HttpHeaders.AUTHORIZATION, authHeader)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .flatMap(hasRole -> {
                        if (Boolean.TRUE.equals(hasRole)) {
                            return chain.filter(exchange);
                        } else {
                            System.out.println("Forbidden: insufficient role");
                            return createErrorResponse(exchange, HttpStatus.FORBIDDEN, "Forbidden: insufficient role");
                            //exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                            //return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden")); // printa se
                            //return Mono.defer(() -> exchange.getResponse().setComplete());
                            //return exchange.getResponse().setComplete();
                        }
                    })
                    .onErrorResume(e -> {
                        System.out.println("Error during role validation: " + e.getMessage());
                        return createErrorResponse(exchange, HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
                        //exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                        //return exchange.getResponse().setComplete();
                    });
        };
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    private Mono<Void> createErrorResponse(ServerWebExchange exchange, HttpStatus status, String message) {
        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ErrorResponse errorResponse = new ErrorResponse(status.value(), status.getReasonPhrase(), message, exchange.getRequest().getPath().value());
        DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
        DataBuffer dataBuffer;
        try {
            dataBuffer = bufferFactory.wrap(objectMapper.writeValueAsBytes(errorResponse));
        } catch (Exception e) {
            dataBuffer = bufferFactory.wrap("{}".getBytes());
        }

        return exchange.getResponse().writeWith(Mono.just(dataBuffer));
    }

    private static class ErrorResponse {
        private final int status;
        private final String error;
        private final String message;
        private final String path;

        public ErrorResponse(int status, String error, String message, String path) {
            this.status = status;
            this.error = error;
            this.message = message;
            this.path = path;
        }

        // Getters and setters
        public int getStatus() { return status; }
        public String getError() { return error; }
        public String getMessage() { return message; }
        public String getPath() { return path; }
    }
}
