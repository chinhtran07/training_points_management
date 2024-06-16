package com.tps.controllers;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.tps.dto.ChatMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/chat")
public class FirebaseController {

    @Autowired
    private Firestore firestore;

    private static final String COLLECTION_NAME = "messages";

    @PostMapping("/send")
    public ResponseEntity sendMessage(@RequestBody ChatMessages chatMessages) {
        CollectionReference messages = firestore.collection(COLLECTION_NAME);
        DocumentReference newMessage = messages.document();
        chatMessages.setTimestamp(System.currentTimeMillis());
        newMessage.set(chatMessages);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/history")
    public ResponseEntity<List<ChatMessages>> getChatHistory() throws ExecutionException, InterruptedException {
        CollectionReference messages = firestore.collection(COLLECTION_NAME);

        List<ChatMessages> chatHistory = messages.get().get().toObjects(ChatMessages.class);

        return new ResponseEntity<>(chatHistory, HttpStatus.OK);

    }
}
