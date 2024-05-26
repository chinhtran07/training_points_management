package com.tps.repositories;

import com.tps.dto.UserAssistantDTO;
import com.tps.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserRepository {
    User getUserByUsername(String username);
    User getUserById(int id);
    boolean authUser(String username, String password);
    User addUser(User user);
    User findById(int id);
    void updateUser(User user);
    void deleteUser(User user);
    List<User> getAllUsers(Map<String, String> params);

}
