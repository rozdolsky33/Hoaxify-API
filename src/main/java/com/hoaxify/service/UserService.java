package com.hoaxify.service;

import com.hoaxify.entity.User;
import com.hoaxify.error.NotFoundException;
import com.hoaxify.model.UserUpdateVM;
import com.hoaxify.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

     UserRepository userRepository;
     PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
       return userRepository.save(user);
    }

    public Page<User> getUsers(User loggedInUser, Pageable pageable) {

        if (loggedInUser != null){
                return userRepository.findByUsernameNot(loggedInUser.getUsername(), pageable);
        }
        return userRepository.findAll(pageable);
    }
    public User getByUsername(String username){
        User inDB = userRepository.findByUsername(username);
        if (inDB == null){
            throw new NotFoundException("User with username + "+ username + " Not Found");
        }
        return inDB;
    }

    public User update(long id, UserUpdateVM userUpdateVM) {
        User inDB = userRepository.getOne(id);
        inDB.setDisplayName(userUpdateVM.getDisplayName());
        return userRepository.save(inDB);
    }
}
