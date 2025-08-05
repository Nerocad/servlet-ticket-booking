package com.walking.tbooking.service;

import com.walking.tbooking.model.User;
import com.walking.tbooking.repository.UserRepository;
import com.walking.tbooking.util.PasswordUtil;

import java.util.Optional;

public class AuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> authenticate(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty()) {
            return Optional.empty();
        }

        User user = userOptional.get();

        if (user.isBlocked()) {
            throw new RuntimeException("Аккаунт заблокирован");
        }

        if (!PasswordUtil.checkPassword(password, user.getPassword())) {
            return Optional.empty();
        }

        userRepository.updateLastLogin(user.getId());

        return Optional.of(user);
    }
}
