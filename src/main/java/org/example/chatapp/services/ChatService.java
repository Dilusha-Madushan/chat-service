package org.example.chatapp.services;

import lombok.RequiredArgsConstructor;
import org.example.chatapp.dto.*;
import org.example.chatapp.models.Chat;
import org.example.chatapp.models.UserChat;
import org.example.chatapp.repository.ChatRepository;
import org.example.chatapp.repository.UserChatRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatApiService chatApiService;
    private final ChatRepository chatRepository;
    private final UserChatRepository userChatRepository;



    // FIXME: add the chat name here
    public ChatCreated createChat(UUID userId, CreateNewChat newChat) {


        Chat chat = Chat.builder()
                .userId(userId)
                .chatId(UUID.randomUUID())
                .chatName(newChat.getChatName())
                .createdTime(Instant.now())
                .build();
        chatRepository.save(chat);

        return ChatCreated.builder()
                .chatName(chat.getChatName())
                .chatId(chat.getChatId().toString())
                .userId(chat.getUserId().toString())
                .createdAt(chat.getCreatedTime().toString())
                .message("new chat created.")
                .build();
    }

    public GetChats getChatIdsByUserId(UUID userId) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdTime");
        List<Chat> chats = chatRepository.findByUserId(userId, sort);
        List<GetChat> chatList = new ArrayList<>();

        for (Chat chat : chats) {
            chatList.add(GetChat.builder()
                        .chatId(chat.getChatId().toString())
                        .chatName(chat.getChatName())
                        .createdAt(chat.getCreatedTime().toString())
                        .userId(chat.getUserId().toString())
                        .build());
        }

        return GetChats.builder()
                .chats(chatList)
                .build();
    }

    public GetUserChats getChatByChatIdAndUserId(UUID chatId, UUID userId) {

        Chat chat = chatRepository.findById(chatId).orElse(null);

        if (chat == null) {
            throw new IllegalArgumentException("invalid chat id");
        }
        if (!chat.getUserId().equals(userId)) {
            throw new IllegalArgumentException("invalid user id");
        }

        Sort sort = Sort.by(Sort.Direction.DESC, "createdTime");
        List<UserChat> userChats = userChatRepository.findByUserIdAndChatId(userId, chatId, sort);

        List<GetUserChat> UserChatList = new ArrayList<>();

        for (UserChat userChat : userChats) {
            UserChatList.add(GetUserChat.builder()
                            .role(userChat.getRole())
                            .text(userChat.getText())
                    .build());
        }

        return GetUserChats.builder()
                .UserChats(UserChatList)
                .build();
    }

    public ReplyMessage processChat(UUID userId, UUID chatId, String userMessage) {
        // Check if a chat record exists with the given chatId and userId
        Optional<Chat> existingChat = chatRepository.findByChatId(chatId);
        if (existingChat.isEmpty()) {
            throw new IllegalArgumentException("Invalid chatId or userId");
        }

        String botResponse = chatApiService.getResponseFromBot(userMessage);


        UserChat userChat = UserChat.builder()
                .chatId(chatId)
                .id(UUID.randomUUID())
                .userId(userId)
                .createdTime(Instant.now())
                .role("user")
                .text(userMessage)
                .build();
        userChatRepository.save(userChat);


        UserChat botChat = UserChat.builder()
                .chatId(chatId)
                .id(UUID.randomUUID())
                .userId(userId)
                .createdTime(Instant.now())
                .role("bot")
                .text(botResponse)
                .build();
        userChatRepository.save(botChat);


        return ReplyMessage.builder()
                .text(botResponse)
                .author("bot")
                .createdAt(botChat.getCreatedTime().toString())
                .build();
    }
}