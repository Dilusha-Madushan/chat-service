package org.example.chatapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.chatapp.dto.*;
import org.example.chatapp.services.ChatService;
import org.example.chatapp.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import java.util.UUID;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/chats")
public class ChatController {

    private final ChatService chatService;
    private final JwtService jwtService;


    @PostMapping()
    public ResponseEntity<ChatCreated> createChat(@AuthenticationPrincipal Jwt jwt, @RequestBody CreateNewChat newChat){
        UUID userId = jwtService.getUserId(jwt);
        return ResponseEntity.status(HttpStatus.CREATED)
                 .body(chatService.createChat(userId,newChat));
    }

    @GetMapping("/{chat_id}")
    public ResponseEntity<GetUserChats> getChat(@AuthenticationPrincipal Jwt jwt, @PathVariable("chat_id") UUID chatId){
        UUID userId = jwtService.getUserId(jwt);

        return ResponseEntity.status(HttpStatus.OK)
                .body( chatService.getChatByChatIdAndUserId(chatId,userId));
    }

    @GetMapping()
    public ResponseEntity<GetChats> getChats(@AuthenticationPrincipal Jwt jwt){
        UUID userID = jwtService.getUserId(jwt);

        return ResponseEntity.status(HttpStatus.OK)
                .body(chatService.getChatIdsByUserId(userID));
    }


//    @RequestMapping("/my")
//    public ResponseEntity<ChatInfo> chatInfo(@AuthenticationPrincipal Jwt jwt){
//        Map<String, Object> claims = jwt.getClaims();
//        String sub = (String) claims.get("sub");
//        log.info("User sub: {}", sub);
//        return ResponseEntity.ok(ChatInfo.builder().chatId("ABC").build());
//    }

//    @PostMapping("/chats")
//    public ResponseEntity<UUID> createChat() {
//        // Hardcoded user ID for testing
//        UUID userId = UUID.fromString("123e4567-e89b-12d3-a456-426655440000");
//
//        Chat createdChat = chatService.createChat(userId);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdChat.getChatId());
//    }

//    @GetMapping("/chats/{userId}")
//    public ResponseEntity<List<UUID>> getChatIdsByUserId(@PathVariable UUID userId) {
//        List<UUID> chats = chatService.getChatIdsByUserId(userId);
//
//        return ResponseEntity.ok(chats);
//    }

//    @PostMapping("/chats/{chatId}/messages")
//    public ResponseEntity<String> sendUserChat(@RequestBody String userMessage) {
//        // Hardcoded chat ID and user ID for testing
//        UUID chatId = UUID.fromString("chat_id_value_here");
//        UUID userId = UUID.fromString("user_id_value_here");
//
//        String botResponse = chatService.processChat(userId, chatId, userMessage);
//        return ResponseEntity.ok(botResponse);
//    }

}

