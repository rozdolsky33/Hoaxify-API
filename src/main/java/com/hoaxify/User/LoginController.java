package com.hoaxify.user;

import com.hoaxify.entity.User;
import com.hoaxify.utils.CurrentUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class LoginController {

    @PostMapping("/api/v1/login")
    public Map<String, Object> handleLogin(@CurrentUser User loggedInUser){
         return Collections.singletonMap("id", loggedInUser.getId());
    }


}
