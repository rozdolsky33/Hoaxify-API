package com.hoaxify.user;

import com.hoaxify.entity.User;
import com.hoaxify.response.GenericResponse;
import com.hoaxify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {


    UserService userService;

   @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/api/v1/users")
    public GenericResponse createUser(@Valid @RequestBody User user){

            userService.save(user);
            return new GenericResponse("User Saved");
    }
}
