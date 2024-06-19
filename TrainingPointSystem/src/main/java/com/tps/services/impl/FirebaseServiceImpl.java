package com.tps.services.impl;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.tps.pojo.User;
import com.tps.services.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class FirebaseServiceImpl implements FirebaseService {

    @Autowired
    private Firestore firestore;

    @Override
    public void addUserToFirestore(User user) {
        CollectionReference users = firestore.collection("users");
        DocumentReference newUser = users.document(String.valueOf(user.getId()));
        Map<String, Object> data = new HashMap<>();
        data.put("firstName", user.getFirstName());
        data.put("lastName", user.getLastName());
        data.put("avatar", user.getAvatar());
        newUser.set(data);
    }
}
