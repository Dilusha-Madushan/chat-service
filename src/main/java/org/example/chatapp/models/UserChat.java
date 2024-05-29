package org.example.chatapp.models;

import java.time.Instant;

import java.util.UUID;

import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_chat")
public class UserChat {
    @Id
    private UUID id;
    private UUID userId;
    private UUID chatId;
    private String role;
    private Instant createdTime;
    private String text;
}
