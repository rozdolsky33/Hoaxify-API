package com.hoaxify.user;

import com.hoaxify.entity.User;
import com.hoaxify.model.UserVM;
import com.hoaxify.utils.CurrentUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/api/v1/login")
    public UserVM handleLogin(@CurrentUser User loggedInUser){
         return new UserVM(loggedInUser);
    }


}
