package org.example.chatapp.repository;

import java.util.List;
import java.util.UUID;
import org.example.chatapp.models.UserChat;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserChatRepository extends MongoRepository<UserChat, UUID> {

    List<UserChat> findByUserIdAndChatId(UUID userId, UUID chatId, Sort sort);
}
