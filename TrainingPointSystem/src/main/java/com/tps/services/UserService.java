package com.tps.services;

import com.tps.pojo.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

public interface UserService extends UserDetailsService {
    User getUserByUsername(String username);
    boolean authUser(String username, String password);
    User addUser(User user);
    User findById(int id);
}
