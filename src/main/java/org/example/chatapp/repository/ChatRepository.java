package org.example.chatapp.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.example.chatapp.models.Chat;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends MongoRepository<Chat, UUID> {
    List<Chat> findByUserId(UUID userId, Sort sort);

    Optional<Chat> findByChatIdAndUserId(UUID chatId, UUID userId);

    Optional<Chat> findByChatId(UUID chatId);
}