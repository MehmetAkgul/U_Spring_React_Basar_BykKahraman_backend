package com.hoaxify.backend.auth;

import com.fasterxml.jackson.annotation.JsonView;
import com.hoaxify.backend.shared.CurrentUser;
import com.hoaxify.backend.shared.Views;
import com.hoaxify.backend.user.User;
import com.hoaxify.backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/api/1.0/auth")
    @JsonView(Views.Base.class)
    ResponseEntity<?> handleAuthentication(@CurrentUser User user) {


        return ResponseEntity.ok().body(userRepository.findByUsername(user.getUsername()));
    }

}
