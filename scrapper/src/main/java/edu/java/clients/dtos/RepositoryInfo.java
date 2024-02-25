package edu.java.clients.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.Map;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Setter @Getter @Data
public class RepositoryInfo {
    private String owner;

    @JsonProperty("owner")
    public void setOwner(Map<String, String> owner) {
        this.owner = owner.get("login");
    }

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("language")
    private String language;

    @JsonProperty("stargazers_count")
    private int stars;

    @JsonProperty("created_at")
    private OffsetDateTime createdAt;

    @JsonProperty("updated_at")
    private OffsetDateTime updatedAt;

}
