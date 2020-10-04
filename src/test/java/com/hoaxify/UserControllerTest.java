package com.hoaxify;


import com.hoaxify.repositories.UserRepository;
import com.hoaxify.entity.User;
import com.hoaxify.response.GenericResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {
    public static final String API_V_1_USERS = "/api/v1/users";

/*
naming convention note
methodName_condition_expectedBehavior
*/
    // posting object to server - http client
    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    UserRepository userRepository;

    @Before
    public void cleanup(){
        userRepository.deleteAll();
    }

    @Test
    public void postUser_whenUserIsValid_receiveOk(){
        User user = createValidUser();
        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_V_1_USERS, user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void postUser_whenUserIsValid_SuccessMessage(){
        User user = createValidUser();
        ResponseEntity<GenericResponse> response = testRestTemplate.postForEntity(API_V_1_USERS, user, GenericResponse.class);
        assertThat(response.getBody().getMessage()).isNotNull();
    }

    @Test
    public void postUser_whenUserIsValid_userSavedToDatabase(){
        User user = createValidUser();
        testRestTemplate.postForEntity(API_V_1_USERS, user, Object.class);
        assertThat(userRepository.count()).isEqualTo(1);
    }
    @Test
    public void postUser_whenUserIsValid_passwordIsHashedInDatabase(){
        User user = createValidUser();
        testRestTemplate.postForEntity(API_V_1_USERS, user, Object.class);
        List<User> users = userRepository.findAll();
        User inDb = users.get(0);
        assertThat(inDb.getPassword()).isNotEqualTo(user.getPassword());
    }

    private User createValidUser() {
        User user = new User();
        user.setUsername("test-user");
        user.setDisplayName("test-display");
        user.setPassword("P4ssword");
        return user;
    }

}
