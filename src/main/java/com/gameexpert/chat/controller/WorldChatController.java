package com.gameexpert.chat.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gameexpert.chat.dto.ChatMessageResponse;
import com.gameexpert.chat.service.RecentChatQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class WorldChatController {

    private final RecentChatQueryService chatService;

    @GetMapping("/worlds/{worldId}/chats")
    public ResponseEntity<List<ChatMessageResponse>> chats(@PathVariable("worldId") Long worldId, @RequestParam(name = "limit", defaultValue = "50") int limit) {
        return ResponseEntity.status(HttpStatus.OK).body(chatService.getRecentMessages(worldId, limit));
    }
}
