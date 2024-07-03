package com.pacs.scanviewer.SCV.Chat.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findBySenderAndRecipient(String sender, String recipient);
    List<ChatMessage> findByRecipientAndSender(String recipient, String sender);

}
