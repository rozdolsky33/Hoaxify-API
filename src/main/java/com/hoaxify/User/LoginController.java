package com.hoaxify.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.hoaxify.entity.User;
import com.hoaxify.entity.Views;
import com.hoaxify.utils.CurrentUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/api/v1/login")
    @JsonView(Views.Base.class)
    public User handleLogin(@CurrentUser User loggedInUser){
         return loggedInUser;
    }


}
