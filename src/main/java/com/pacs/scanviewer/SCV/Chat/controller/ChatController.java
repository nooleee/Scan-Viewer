package com.pacs.scanviewer.SCV.Chat.controller;

import com.pacs.scanviewer.SCV.Chat.domain.ChatMessage;
import com.pacs.scanviewer.SCV.Chat.domain.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/sendMessage")
    @SendToUser("/queue/reply")
    public void sendMessage(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
        messagingTemplate.convertAndSendToUser(chatMessage.getRecipient(), "/queue/reply", chatMessage);
    }

    @GetMapping("/messages/{sender}/{recipient}")
    @ResponseBody
    public List<ChatMessage> getMessages(@PathVariable String sender, @PathVariable String recipient) {
        System.out.println("sender: " + sender);
        System.out.println("recipient: " + recipient);
        List<ChatMessage> messages = chatMessageRepository.findBySenderAndRecipient(sender, recipient);
        messages.addAll(chatMessageRepository.findByRecipientAndSender(sender, recipient));

        messages.sort(Comparator.comparing(ChatMessage::getTimestamp));
        return messages;
    }

    @GetMapping("/chat")
    public String chat() {
        return "chat/chat";
    }
}
