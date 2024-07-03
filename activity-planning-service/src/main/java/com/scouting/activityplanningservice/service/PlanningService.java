package com.scouting.activityplanningservice.service;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.models.*;
import com.scouting.activityplanningservice.config.PlanningProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//detailed
@Service
public class PlanningService {
    private static final String PROMPT = """
            You are an expert scout leader, highly skilled in planning engaging and educational scouting activities.
            You are very polite and always create plans that are inclusive, fun, and suitable for the specified conditions.
            You are very creative and always come up with unique and exciting activities.
            Please create a short plan for a scouting activity plan based on the following details:
            - Topic: %s
            - Place type: %s
            - Number of people: %s
            - Age group: %s
            - Price range: %s
            - With extra equipment: %s
            """;

    private final String deploymentName;
    private final OpenAIClient client;

    public PlanningService(PlanningProperties planningProperties, OpenAIClient client) {
        deploymentName = planningProperties.getAzure().getDeploymentName();
        this.client = client;
    }

    public String planActivity(String topic, String placeType, String numberOfPeople, String ageGroup, String priceRange, Boolean withEquipment) {
        String safeTopic = Optional.ofNullable(topic).orElse("General fun summer scouting activity");
        String safePlaceType = Optional.ofNullable(placeType).orElse("anywhere");
        String safeNumberOfPeople = Optional.ofNullable(numberOfPeople).orElse("any number");
        String safeAgeGroup = Optional.ofNullable(ageGroup).orElse("all ages");
        String safePriceRange = Optional.ofNullable(priceRange).orElse("any budget");
        String safeWithEquipment = Optional.ofNullable(withEquipment).map(e -> e ? "with equipment" : "without equipment").orElse("either with or without equipment");

        List<ChatRequestMessage> chatMessages = List.of(
                new ChatRequestSystemMessage(PROMPT.formatted(safeTopic, safePlaceType, safeNumberOfPeople, safeAgeGroup, safePriceRange, safeWithEquipment))
        );

        ChatCompletions chatCompletions = client.getChatCompletions(
                deploymentName,
                new ChatCompletionsOptions(chatMessages)
        );

        return chatCompletions.getChoices().stream()
                .map(ChatChoice::getMessage)
                .map(ChatResponseMessage::getContent)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
