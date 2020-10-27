package com.hoaxify;

import com.hoaxify.entity.User;
import com.hoaxify.hoax.Hoax;

public class TestUtil {


    public static User createValidUser() {
        User user = new User();
        user.setUsername("test-user");
        user.setDisplayName("test-display");
        user.setPassword("P4ssword");
        user.setImage("profile-image.png");
        return user;
    }

    public static User createValidUser(String username) {
        User user = createValidUser();
        user.setUsername(username);
        return user;
    }

    public static Hoax creatValidHoax(){
        Hoax hoax = new Hoax();
        hoax.setContent("test contet for the test hoax");
        return hoax;
    }

}
