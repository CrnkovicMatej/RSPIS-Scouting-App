package com.scouting.activityservice.activities.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserIdRetrieverService {
    private final WebClient.Builder webClientBuilder;
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public UserIdRetrieverService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Long getUserIdByApiKey_s(String apiKey) {
        String userServiceUrl = "http://user-service:8082/users";
        String endpoint = "?apiKey=" + apiKey;
        return restTemplate.getForObject(userServiceUrl + endpoint, Long.class);
    }

    public Mono<Long> getUserIdByApiKey(String apiKey) {
        return webClientBuilder.build()
                .get()
                .uri("http://user-service:8082/users?apiKey=" + apiKey)
                .retrieve()
                .bodyToMono(Long.class);
    }
}
