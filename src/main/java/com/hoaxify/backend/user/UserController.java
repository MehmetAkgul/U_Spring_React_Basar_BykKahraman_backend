package com.hoaxify.backend.user;

import com.hoaxify.backend.error.ApiError;
import com.hoaxify.backend.shared.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;


    @PostMapping("/api/1.0/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {

        String username = user.getUsername();
        String displayName = user.getDisplayName();
        String password = user.getPassword();


        ApiError error = new ApiError(400, "Validation error", "/api/1.0/errors");
        Map<String, String> validationErrors = new HashMap<>();

        if (username == null || username.isEmpty())
            validationErrors.put("username", "username cannot be null");

        if (displayName == null || displayName.isEmpty())
            validationErrors.put("displayName", "displayName cannot be null");

        if (password == null || password.isEmpty())
            validationErrors.put("password", "Password cannot be null");


        if (validationErrors.size() > 0) {
            error.setValidationErrors(validationErrors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        userService.save(user);

        return ResponseEntity.ok(new GenericResponse("User Created"));
    }

}
