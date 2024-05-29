package org.example.chatapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatInfo {
    @JsonProperty("chat_id")
    private String chatId;

    @JsonProperty("chat_name")
    private String chatName;

    @JsonProperty("created_at")
    private String createdAt;
}
