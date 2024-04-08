package com.tps.repositories;

import com.tps.pojo.User;

public interface UserRepository {
    User getUserByUsername(String username);
}
