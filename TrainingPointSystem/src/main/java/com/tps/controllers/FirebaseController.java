package com.tps.controllers;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.tps.components.UserConverter;
import com.tps.dto.ChatMessages;
import com.tps.dto.UserChat;
import com.tps.pojo.User;
import com.tps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat")
public class FirebaseController {

    @Autowired
    private Firestore firestore;

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    private static final String COLLECTION_NAME = "messages";

    @PostMapping("/send")
    public ResponseEntity sendMessage(@RequestBody ChatMessages chatMessages) {
        CollectionReference messages = firestore.collection(COLLECTION_NAME);
        DocumentReference newMessage = messages.document();
        chatMessages.setTimestamp(System.currentTimeMillis());
        newMessage.set(chatMessages);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/get-users")
    public ResponseEntity<List<UserChat>> getUsers(Principal principal) throws ExecutionException, InterruptedException {
        User user = this.userService.getUserByUsername(principal.getName());

        Set<UserChat> users = new HashSet<>();

        CollectionReference messages = firestore.collection(COLLECTION_NAME);
        messages.get().get().getDocuments().forEach(
                queryDocumentSnapshot -> System.out.print(queryDocumentSnapshot.get("message"))
        );


        messages.whereEqualTo("sender", user.getId())
                .get()
                .get()
                .getDocuments()
                .forEach(document -> {
                    ChatMessages chatMessages = document.toObject(ChatMessages.class);
                    messages.add(chatMessages);
                    users.add(userConverter.toUserChat(this.userService.getUserById(Integer.parseInt(chatMessages.getReceiver()))));
                });

        messages.whereEqualTo("receiver", user.getId())
                .get()
                .get()
                .getDocuments()
                .forEach(document -> {
                    ChatMessages chatMessages = document.toObject(ChatMessages.class);
                    messages.add(chatMessages);
                    users.add(userConverter.toUserChat(this.userService.getUserById(Integer.parseInt(chatMessages.getSender()))));
                });


        return new ResponseEntity<>(new ArrayList<>(users), HttpStatus.OK);
    }
}
