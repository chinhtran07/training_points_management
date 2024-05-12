package com.tps.services;

import com.tps.dto.UserAssistantDTO;
import com.tps.pojo.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

public interface UserService extends UserDetailsService {
    User getUserByUsername(String username);
    boolean authUser(String username, String password);
    User addUser(User user);
    User findById(int id);
    void updateUser(User user);
    void deleteUser(User user);
    List<User> getAllUsers(Map<String, String> params);
}
