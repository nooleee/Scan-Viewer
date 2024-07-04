package com.pacs.scanviewer.SCV.Chat.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@Entity
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sender;

    private String recipient;

    private String content;

    private MessageType type;

    @CreatedDate
    private LocalDateTime timestamp; // 또는 Date


    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    // Getters and Setters
}
