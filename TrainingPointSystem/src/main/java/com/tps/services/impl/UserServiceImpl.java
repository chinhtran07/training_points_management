package com.tps.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tps.dto.UserAssistantDTO;
import com.tps.dto.UserDTO;
import com.tps.pojo.User;
import com.tps.repositories.UserRepository;
import com.tps.services.UserService;
//import jdk.internal.org.objectweb.asm.commons.RemappingMethodAdapter;
import org.hibernate.id.ForeignGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static com.tps.pojo.User.*;

@Service("userDetailService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = this.userRepository.getUserByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), getAuthorities(u));
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.getUserByUsername(username);
    }

    @Override
    public boolean authUser(String username, String password) {
        return this.userRepository.authUser(username, password);
    }

    @Override
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(STUDENT);

//        if (!user.getFile().isEmpty()) {
//
//            try {
//                Map res = this.cloudinary.uploader().upload(user.getFile().getBytes(),
//                        ObjectUtils.asMap("resource_type", "auto"));
//                user.setAvatar((String) res.get("secure_url"));
//
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }

        return this.userRepository.addUser(user);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public void updateUser(User user) {
        this.userRepository.updateUser(user);
    }

    @Override
    public void deleteUser(User user) {
        this.userRepository.deleteUser(user);
    }

    @Override
    public List<User> getAllUsers(Map<String, String> params) {
        return this.userRepository.getAllUsers(params);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        return authorities;
    }
}
