package com.hoaxify.user;

import com.hoaxify.enteties.User;
import com.hoaxify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    UserService userService;

   @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/api/v1/users")
    public void createUser(@RequestBody User user){
            userService.save(user);
    }
}
