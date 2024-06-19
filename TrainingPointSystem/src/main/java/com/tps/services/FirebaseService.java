package com.tps.services;

import com.tps.pojo.User;

public interface FirebaseService {
    void addUserToFirestore(User user);
}
