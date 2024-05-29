package org.example.chatapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.chatapp.dto.NewMessage;
import org.example.chatapp.dto.ReplyMessage;
import org.example.chatapp.services.ChatService;
import org.example.chatapp.services.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {
    private final ChatService chatService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<ReplyMessage> sendUserChat(@AuthenticationPrincipal Jwt jwt, @RequestBody NewMessage body){
        UUID userId  = jwtService.getUserId(jwt);
        return ResponseEntity.status(HttpStatus.OK)
                .body(chatService.processChat(
                        userId,
                        UUID.fromString(body.getChatId()),
                        body.getText()));

    }
}
