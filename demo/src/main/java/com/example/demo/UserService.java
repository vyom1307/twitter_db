package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;

import static org.springframework.http.RequestEntity.put;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    @Autowired
    private MyUserDetailService userDetailService;





    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;
    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder,AuthenticationManager authenticationManager,JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.encoder = encoder;

        this.authenticationManager=authenticationManager;
        this.jwtUtil=jwtUtil;
    }
    public ResponseEntity<?> login(LoginRequest request){
//        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
//        if (optionalUser.isEmpty()) {
//            return "Invalid email or password";
//        }
//        User user=optionalUser.get();
//
//        if (!encoder.matches(request.getPassword(), user.getPassword())) {
//            return "Invalid email or password";
//        }
//        return "logged in!";

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        final UserDetails userDetails =
                userDetailService.loadUserByUsername(request.getEmail());

        final String jwt = jwtUtil.generateJWT(userDetails.getUsername());

        return ResponseEntity.ok(new HashMap<String, String>() {{
            put("token", jwt);
        }});
    }

    public String signup(SignUpRequest request) {
        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already in use!";
        }



        // Save user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));

        userRepository.save(user);

        return "User registered successfully!";
    }






}