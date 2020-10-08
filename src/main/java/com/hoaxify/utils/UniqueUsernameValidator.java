package com.hoaxify.utils;


import com.hoaxify.entity.User;
import com.hoaxify.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //TODO: Auto-generated method stub
        User inDb = userRepository.findByUsername(value);
        if (inDb == null){
            return true;
        }

        return false;
    }
}
