package org.example.chatapp.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatCreated {
    @JsonProperty("chat_id")
    private String chatId;

    @JsonProperty("chat_name")
    private String chatName;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("created_at")
    private String createdAt;

    private String message;
}
