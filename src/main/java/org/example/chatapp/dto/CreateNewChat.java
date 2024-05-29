package org.example.chatapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewChat {
    @JsonProperty("chat_name")
    private String chatName;
}
