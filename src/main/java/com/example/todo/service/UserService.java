package com.example.todo.service;

import com.example.todo.entity.User;
import com.example.todo.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User create(final User user) {
        if (user == null || user.getEmail() == null) {
            throw new RuntimeException("Invalid arguments");
        }

        final String email = user.getEmail();

        if (userRepository.existsByEmail(email)) {
            log.warn("Email already exists {}", email);
            throw new RuntimeException("Email already exists");
        }

        return userRepository.save(user);
    }

    public User getByCredentials(final String email, final String password, final PasswordEncoder encoder) {
        User originUser = userRepository.findByEmail(email);

        if (originUser != null && encoder.matches(password, originUser.getPassword())) {
            return originUser;
        } else {
            return null;
        }
    }

}
