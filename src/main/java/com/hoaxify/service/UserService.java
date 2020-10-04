package com.hoaxify.service;

import com.hoaxify.enteties.User;
import com.hoaxify.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user){
       return userRepository.save(user);
    }

}
