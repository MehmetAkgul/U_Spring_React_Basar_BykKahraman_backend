package com.hoaxify.backend.auth;

import com.fasterxml.jackson.annotation.JsonView;
import com.hoaxify.backend.shared.Views;
import com.hoaxify.backend.user.User;
import com.hoaxify.backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/api/1.0/auth")
    @JsonView(Views.Base.class)
    ResponseEntity<?> handleAuthentication(@RequestHeader(name = "Authorization", required = false) String authorization) {
        String base64encoded = authorization.split("Basic ")[1];
        String decoded = new String(Base64.getDecoder().decode(base64encoded)); // base64 bayt array döndüğünden onu string yaptık
        String[] parts = decoded.split(":");
        String username = parts[0];
        String password = parts[1];
        User inDb = userRepository.findByUsername(username);
        return ResponseEntity.ok().body(inDb);
    }

}
