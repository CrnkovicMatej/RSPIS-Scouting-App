package com.scouting.activityplanningservice.config;

import org.springframework.context.annotation.Configuration;
import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.OpenAIServiceVersion;
import com.azure.core.credential.AzureKeyCredential;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlanningConfiguration {

    @Bean
    public OpenAIClient openAIClient(PlanningProperties planningProperties) {
        var openAIProperties = planningProperties.getAzure();
        return new OpenAIClientBuilder()
                .credential(new AzureKeyCredential(openAIProperties.getApiKey()))
                .endpoint(openAIProperties.getApiEndpoint())
                .serviceVersion(OpenAIServiceVersion.V2023_05_15)
                .buildClient();
    }
}
