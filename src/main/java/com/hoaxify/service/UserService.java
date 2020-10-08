package com.hoaxify.service;

import com.hoaxify.Exceptions.DuplicateUsernameException;
import com.hoaxify.entity.User;
import com.hoaxify.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User save(User user){
        // Check if we have user in db with this username
        User inDB = userRepository.findByUsername(user.getUsername());
        if (inDB != null){
            throw new DuplicateUsernameException();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
       return userRepository.save(user);
    }

}
