package com.hoaxify;

import com.hoaxify.entity.User;
import com.hoaxify.error.ApiError;
import com.hoaxify.repositories.UserRepository;
import com.hoaxify.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static com.hoaxify.TestUtil.createValidUser;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class LoginControllerTest {

    private static final String API_V_1_LOGIN = "/api/v1/login";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Before
    public void cleanUp(){
        userRepository.deleteAll();
        testRestTemplate.getRestTemplate().getInterceptors().clear();
    }

    @Test
    public void postLogin_withoutUserCredentials_receiveUnauthorized(){
        ResponseEntity<Object> response = login(Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
    @Test
    public void postLogin_withIncorrectCredentials_receiveUnauthorized(){
        authenticate();
        ResponseEntity<Object> response = login(Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
    @Test
    public void postLogin_withoutUserCredentials_receiveApiError(){
        ResponseEntity<ApiError> response = login(ApiError.class);
        assertThat(response.getBody().getUrl()).isEqualTo(API_V_1_LOGIN);
    }
    @Test
    public void postLogin_withoutUserCredentials_receiveApiErrorWithoutValidationErrors(){
        ResponseEntity<String> response = login(String.class);
        assertThat(response.getBody().contains("validationErrors")).isFalse();
    }
    @Test
    public void postLogin_withoutUserCredentials_receiveApiErrorWithoutWWWAuthenticationHeader(){
        authenticate();
        ResponseEntity<Object> response = login(Object.class);
        assertThat(response.getHeaders().containsKey("WWW-Authenticate")).isFalse();
    }

    @Test
    public void postLogin_withValidCredentials_receiveOk(){
        userService.save(createValidUser());
        authenticate();
        ResponseEntity<Object> response = login(Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    public void postLogin_withValidCredentials_receiveLoggedInUserId(){
        User inDB = userService.save(createValidUser());
        authenticate();
        ResponseEntity<Map<String, Object>> response = login(new ParameterizedTypeReference<Map<String, Object>>() {});
        Map<String, Object> body = response.getBody();
        Integer id = (Integer) response.getBody().get("id");
        assertThat(id).isEqualTo(inDB.getId());
    }
    @Test
    public void postLogin_withValidCredentials_receiveLoggedInUserImage(){
        User inDB = userService.save(createValidUser());
        authenticate();
        ResponseEntity<Map<String, Object>> response = login(new ParameterizedTypeReference<Map<String, Object>>() {});
        Map<String, Object> body = response.getBody();
        String image = (String) body.get("image");
        assertThat(image).isEqualTo(inDB.getImage());
    }
    @Test
    public void postLogin_withValidCredentials_receiveLoggedInUserDisplayName(){
        User inDB = userService.save(createValidUser());
        authenticate();
        ResponseEntity<Map<String, Object>> response = login(new ParameterizedTypeReference<Map<String, Object>>() {});
        Map<String, Object> body = response.getBody();
        String displayName = (String) body.get("displayName");
        assertThat(displayName).isEqualTo(inDB.getDisplayName());
    }
    @Test
    public void postLogin_withValidCredentials_receiveLoggedInUsername(){
        User inDB = userService.save(createValidUser());
        authenticate();
        ResponseEntity<Map<String, Object>> response = login(new ParameterizedTypeReference<Map<String, Object>>() {});
        Map<String, Object> body = response.getBody();
        String username = (String) body.get("username");
        assertThat(username).isEqualTo(inDB.getUsername());
    }
    @Test
    public void postLogin_withValidCredentials_notReceiveLoggedInUsersPassword(){
        User inDB = userService.save(createValidUser());
        authenticate();
        ResponseEntity<Map<String, Object>> response = login(new ParameterizedTypeReference<Map<String, Object>>() {});
        Map<String, Object> body = response.getBody();
        assertThat(body.containsKey("password")).isFalse();
    }


    public <T>ResponseEntity<T>login(Class<T> responseType){
        return testRestTemplate.postForEntity(API_V_1_LOGIN, null, responseType);
    }
    public <T>ResponseEntity<T>login(ParameterizedTypeReference<T> responseType){
        return testRestTemplate.exchange(API_V_1_LOGIN, HttpMethod.POST, null, responseType);
    }

    private void authenticate() {
        testRestTemplate.getRestTemplate()
                .getInterceptors()
                .add(new BasicAuthenticationInterceptor("test-user", "P4ssword"));
    }


}
