package com.hoaxify.user;

import com.hoaxify.entity.User;
import com.hoaxify.error.ApiError;
import com.hoaxify.model.UserVM;
import com.hoaxify.response.GenericResponse;
import com.hoaxify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {


    UserService userService;

   @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/users")
    public GenericResponse createUser(@Valid @RequestBody User user){

            userService.save(user);
            return new GenericResponse("User Saved");
    }
    @GetMapping("/users")
    public Page<UserVM> getUsers(Pageable page){
       return userService.getUsers(page).map(UserVM::new);
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleValidationException(MethodArgumentNotValidException exception, HttpServletRequest request){
       ApiError apiError = new ApiError(400, "Validation error", request.getServletPath());

        BindingResult result = exception.getBindingResult();
        Map<String, String>validationErrors = new HashMap<>();

        for (FieldError fieldError : result.getFieldErrors()){
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        apiError.setValidationErrors(validationErrors);
       return apiError;
    }
}
