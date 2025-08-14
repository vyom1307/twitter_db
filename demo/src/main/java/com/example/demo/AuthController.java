package com.example.demo;


import com.example.demo.otp.OtpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
    @RequestMapping("/api/auth")

    public class AuthController {

        private final UserService userService;
        private final OtpService otpService;
        public AuthController(UserService userService, OtpService otpService) {
            this.userService = userService;
            this.otpService = otpService;
        }

        @PostMapping("/signup")
        public String signup(@RequestBody SignUpRequest request) {

            return userService.signup(request);
        }
        @PostMapping("/otp")
        public ResponseEntity<?>verifyOtp(@RequestBody Map<String,String>body){
            return otpService.emailVerified(body.get("otp"),body.get("email"));
        }
        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody LoginRequest request) {
            return userService.login(request);
        }
    }

