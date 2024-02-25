package edu.java.clients.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;

@Setter @Getter @Data public class QuestionInfo {

    @JsonProperty("question_id")
    private long questionId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("body")
    private String body;

    @JsonProperty("score")
    private int score;

    @JsonProperty("view_count")
    private int viewCount;

    @JsonProperty("creation_date")
    private OffsetDateTime creationDate;

    @JsonProperty("last_activity_date")
    private OffsetDateTime lastActivityDate;

}

