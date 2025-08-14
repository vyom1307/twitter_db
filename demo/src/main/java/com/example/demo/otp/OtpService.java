package com.example.demo.otp;

import com.example.demo.SignUpRequest;
import com.example.demo.User;
import com.example.demo.UserLogin.PendingUserRepo;
import com.example.demo.UserLogin.pendingUser;
import com.example.demo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
@Service
public class OtpService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private EmailService emailService;
    @Autowired
    private OptRepo optRepo;

    @Autowired
    private PendingUserRepo pendingUserRepo;





    public ResponseEntity<?> verification(SignUpRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Email already in use!");
        }
        pendingUser user = new pendingUser();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        pendingUserRepo.save(user);


        String genOtp = OtpGenerator();
        Otp userOtp = new Otp(request.getEmail(), encoder.encode(genOtp));
        optRepo.save(userOtp);

        emailService.sendOtpEmail(request.getEmail(), genOtp);

        return ResponseEntity
                .ok("OTP sent to the email " + request.getEmail());
    }

    public ResponseEntity<?> emailVerified(String otp,String email){
        Optional<Otp>userOtp=optRepo.findByEmail(email);
        if(userOtp.isPresent()){
            if(encoder.matches(otp,userOtp.get().getOtp())){
                Optional<pendingUser> user=pendingUserRepo.findByEmail(email);
                if(user.isPresent()){

                    User curr= new User(user.get().getName(),user.get().getEmail(),user.get().getPassword(),user.get().getCreatedAt());
                    userRepository.save(curr);
                    pendingUserRepo.deleteById(user.get().getId());
                    optRepo.deleteByEmail(email);

                }else{
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }

                return ResponseEntity.ok("Registered Succesfully");

            }else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Otp");
            }
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Try Again");
        }
    }

    public String OtpGenerator(){

            int length=6;
            // Define the characters that can be used in the OTP (digits 0-9)
            String numbers = "0123456789";
            Random random = new Random();
            StringBuilder otp = new StringBuilder();

            // Generate random digits and append them to the StringBuilder
            for (int i = 0; i < length; i++) {
                otp.append(numbers.charAt(random.nextInt(numbers.length())));
            }

            return otp.toString();
    }
}

