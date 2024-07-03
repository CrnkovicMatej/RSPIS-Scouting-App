package com.scouting.activityplanningservice.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@ConfigurationProperties(prefix = "activity-planning-service")
public class PlanningProperties {
    @NotNull
    @Valid
    private Azure azure;

    public Azure getAzure() {
        return azure;
    }

    public void setAzure(Azure azure) {
        this.azure = azure;
    }

    public static class Azure {

        @NotNull
        private String apiEndpoint;

        @NotNull
        private String apiKey;

        @NotNull
        private String deploymentName;

        public String getApiEndpoint() {
            return apiEndpoint;
        }

        public void setApiEndpoint(String apiEndpoint) {
            this.apiEndpoint = apiEndpoint;
        }

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public String getDeploymentName() {
            return deploymentName;
        }

        public void setDeploymentName(String deploymentName) {
            this.deploymentName = deploymentName;
        }
    }
}
