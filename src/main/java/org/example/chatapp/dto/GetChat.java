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
public class GetChat {
    @JsonProperty("chat_id")
    private String chatId;

    @JsonProperty("chat_name")
    private String chatName;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("created_at")
    private String createdAt;
}
