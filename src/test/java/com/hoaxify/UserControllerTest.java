package com.hoaxify;


import com.hoaxify.entity.User;
import com.hoaxify.error.ApiError;
import com.hoaxify.repositories.UserRepository;
import com.hoaxify.response.GenericResponse;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.hoaxify.TestUtil.createValidUser;
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
        ResponseEntity<Object> response = postSingUp(user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void postUser_whenUserIsValid_SuccessMessage(){
        User user = createValidUser();
        ResponseEntity<GenericResponse> response = postSingUp(API_V_1_USERS, GenericResponse.class);
        assertThat(response.getBody().getMessage()).isNotNull();
    }
    @Test
    public void postUser_whenUserHasNullUsername_receiveBadRequest(){
        User user = createValidUser();
        user.setUsername(null);
        ResponseEntity<Object> response = postSingUp(user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postUser_whenUserHasUsernameWithLessThanRequire_receiveBadRequest(){
        User user = createValidUser();
        user.setUsername("abc");
        ResponseEntity<Object> response = postSingUp(user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postUser_whenUserHasDisplayNameWithLessThanRequire_receiveBadRequest(){
        User user = createValidUser();
        user.setDisplayName("abc");
        ResponseEntity<Object> response = postSingUp(user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postUser_whenUserHasPasswordWithLessThanRequire_receiveBadRequest(){
        User user = createValidUser();
        user.setPassword("Pbc1");
        ResponseEntity<Object> response = postSingUp(user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postUser_whenUserHasNullDisplayName_receiveBadRequest(){
        User user = createValidUser();
        user.setDisplayName(null);
        ResponseEntity<Object> response = postSingUp(user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postUser_whenUserHasNullPassword_receiveBadRequest(){
        User user = createValidUser();
        user.setPassword(null);
        ResponseEntity<Object> response = postSingUp(user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postUser_whenUserHasUsernameExceedsTheLimit_receiveBadRequest(){
        User user = createValidUser();
        String valueOf256Chars = IntStream.rangeClosed(1, 256).mapToObj(x -> "a").collect(Collectors.joining());
        user.setUsername(valueOf256Chars);
        ResponseEntity<Object> response = postSingUp(user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postUser_whenUserDisplayNameExceedsTheLimit_receiveBadRequest(){
        User user = createValidUser();
        String valueOf256Chars = IntStream.rangeClosed(1, 256).mapToObj(x -> "a").collect(Collectors.joining());
        user.setDisplayName(valueOf256Chars);
        ResponseEntity<Object> response = postSingUp(user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postUser_whenUserHasPasswordExceedsTheLimit_receiveBadRequest(){
        User user = createValidUser();
        String valueOf256Chars = IntStream.rangeClosed(1, 256).mapToObj(x -> "a").collect(Collectors.joining());
        user.setPassword(valueOf256Chars + "A1");
        ResponseEntity<Object> response = postSingUp(user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
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

    @Test
    public void postUser_whenUserHasPasswordWithAllLowercase_receiveBadRequest(){
        User user = createValidUser();
        user.setPassword("alllovercase3");
        ResponseEntity<Object> response = postSingUp(user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postUser_whenUserHasPasswordWithAllUppercase_receiveBadRequest(){
        User user = createValidUser();
        user.setPassword("ASEFGHJFD");
        ResponseEntity<Object> response = postSingUp(user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postUser_whenUserHasPasswordWithAllNumber_receiveBadRequest(){
        User user = createValidUser();
        user.setPassword("123456789");
        ResponseEntity<Object> response = postSingUp(user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postUser_whenUserIsInvalid_receiveApiError(){
        User user = new User();
        ResponseEntity<ApiError> response = postSingUp(user, ApiError.class);
        assertThat(response.getBody().getUrl()).isEqualTo(API_V_1_USERS);
    }
    @Test
    public void postUser_whenUserIsInvalid_receiveApiErrorWithValidationErrors(){
        User user = new User();
        ResponseEntity<ApiError> response = postSingUp(user, ApiError.class);
        assertThat(response.getBody().getValidationErrors().size()).isEqualTo(3);
    }
    @Test
    public void postUser_whenUserHasNullUsername_receiveMessageOfNullErrorFroUsername(){
        User user = createValidUser();
        user.setUsername(null);
        ResponseEntity<ApiError> response = postSingUp(user, ApiError.class);
        Map<String, String> validationError = response.getBody().getValidationErrors();
        assertThat(validationError.get("username")).isEqualTo("Username cannot be null");
    }
    @Test
    public void postUser_whenUserHasInvalidLengthUsername_receiveGenericMessageOfSizeError(){
        User user = createValidUser();
        user.setUsername("abc");
        ResponseEntity<ApiError> response = postSingUp(user, ApiError.class);
        Map<String, String> validationError = response.getBody().getValidationErrors();
        assertThat(validationError.get("username")).isEqualTo("It must have minimum 4 and maximum 255 characters");
    }
    @Test
    public void postUser_whenUserHasNullPassword_receiveGenericMessageOfNullError(){
        User user = createValidUser();
        user.setPassword(null);
        ResponseEntity<ApiError> response = postSingUp(user, ApiError.class);
        Map<String, String> validationError = response.getBody().getValidationErrors();
        assertThat(validationError.get("password")).isEqualTo("Cannot be null");
    }
    @Test
    public void postUser_whenUserInvalidPasswordPattern_receiveMessageOfPasswordPatternError(){
        User user = createValidUser();
        user.setPassword("alllowercase");
        ResponseEntity<ApiError> response = postSingUp(user, ApiError.class);
        Map<String, String> validationError = response.getBody().getValidationErrors();
        assertThat(validationError.get("password")).isEqualTo("Password must have at least one uppercase, one lowercase and one number");
    }
    @Test
    public void postUser_whenAnotherUserHasSameUsername_receiveBadRequest(){
        userRepository.save(createValidUser());
        User user = createValidUser();
        ResponseEntity<Object> response = postSingUp(user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }
    @Test
    public void postUser_whenAnotherUserHasSameUsername_receiveMessageOfDuplicateUsername(){
        userRepository.save(createValidUser());

        User user = createValidUser();
        ResponseEntity<ApiError> response = postSingUp(user, ApiError.class);
        Map<String, String> validationError = response.getBody().getValidationErrors();
        assertThat(validationError.get("username")).isEqualTo("This name is in use");
    }

    @Test
    public void getUsers_whenThereAreNoUsersInDB_receiveOK(){
        ResponseEntity<Object> response = testRestTemplate.getForEntity(API_V_1_USERS, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
    @Test
    public void getUsers_whenThereAreNoUsersInIB_receivePageWithZeroItems(){
        ResponseEntity<TestPage<Object>> response = getUsers(new ParameterizedTypeReference<TestPage<Object>>() {});
        assertThat(response.getBody().getTotalElements()).isEqualTo(0);
    }
    @Test
    public void getUsers_whenThereIsAUserInDB_receivePageWithUser(){
        userRepository.save(createValidUser());
        ResponseEntity<TestPage<Object>> response = getUsers(new ParameterizedTypeReference<TestPage<Object>>() {});
        assertThat(response.getBody().getNumberOfElements()).isEqualTo(1);
    }
    @Test
    public void getUsers_whenThereIsAUserInDB_receiveUserWithoutPassword(){
        userRepository.save(createValidUser());
        ResponseEntity<TestPage<Map<String, Object>>> response = getUsers(new ParameterizedTypeReference<TestPage<Map<String, Object>>>() {});
        Map<String, Object> entity = response.getBody().getContent().get(0);
        assertThat(entity.containsKey("password")).isFalse();
    }

    @Test
    public void getUsers_whenPageIsRequestedFor3ItemsPerPageWhereTheDatabaseHas20user_receive3Users(){
        IntStream.rangeClosed(1, 20).mapToObj(i -> "test-user-" + i)
                .map(TestUtil::createValidUser).forEach(userRepository::save);
        String path = API_V_1_USERS + "?page=0&size=3";
        ResponseEntity<TestPage<Object>> response = getUsers(path, new ParameterizedTypeReference<TestPage<Object>>() {});
        assertThat(response.getBody().getContent().size()).isEqualTo(3);
    }
    @Test
    public void getUsers_whenPageSizeNotProvided_receivePageSizeAs10(){
        ResponseEntity<TestPage<Object>> response = getUsers(new ParameterizedTypeReference<TestPage<Object>>() {});
        assertThat(response.getBody().getSize()).isEqualTo(10);
    }
    @Test
    public void getUsers_whenPageSizeIsGreaterThan100_receivePageSizeAs100(){
        String path = API_V_1_USERS + "?size=500";
        ResponseEntity<TestPage<Object>> response = getUsers(path, new ParameterizedTypeReference<TestPage<Object>>() {});
        assertThat(response.getBody().getSize()).isEqualTo(100);
    }
    @Test
    public void getUsers_whenPageSizeIsNegative_receivePageSizeAs10(){
        String path = API_V_1_USERS + "?size=-5";
        ResponseEntity<TestPage<Object>> response = getUsers(path, new ParameterizedTypeReference<TestPage<Object>>() {});
        assertThat(response.getBody().getSize()).isEqualTo(10);
    }
    @Test
    public void getUsers_whenPageSizeIsNegative_receiveFirstPage(){
        String path = API_V_1_USERS + "?size=-5";
        ResponseEntity<TestPage<Object>> response = getUsers(path, new ParameterizedTypeReference<TestPage<Object>>() {});
        assertThat(response.getBody().getNumber()).isEqualTo(0);
    }

    public <T> ResponseEntity<T> postSingUp(Object request, Class<T> response){
        return testRestTemplate.postForEntity(API_V_1_USERS, request, response);
    }

    public<T> ResponseEntity<T> getUsers(ParameterizedTypeReference<T> responseType){
        return testRestTemplate.exchange(API_V_1_USERS, HttpMethod.GET, null, responseType);
    }
    public<T> ResponseEntity<T> getUsers(String path, ParameterizedTypeReference<T> responseType){
        return testRestTemplate.exchange(path, HttpMethod.GET, null, responseType);
    }

}
