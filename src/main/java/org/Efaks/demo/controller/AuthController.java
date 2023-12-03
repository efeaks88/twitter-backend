package org.Efaks.demo.controller;

import lombok.RequiredArgsConstructor;
import org.Efaks.demo.AppConfig.JwtProvider;
import org.Efaks.demo.dto.response.AuthResponse;
import org.Efaks.demo.exception.UserException;
import org.Efaks.demo.repository.IUserRepository;
import org.Efaks.demo.repository.entity.User;
import org.Efaks.demo.repository.entity.Varification;
import org.Efaks.demo.service.CustomUserDetailsServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private IUserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private JwtProvider jwtProvider;
    private CustomUserDetailsServiceImplementation customUserDetailsServiceImplementation;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user)throws UserException {
        System.out.println("user = " + user);
        String email=user.getEmail();
        String password=user.getPassword();
        String fullName=user.getFullName();
        String birthDate=user.getBirthDate();
        User isEmailExist=userRepository.findByEmail(email);
        if(isEmailExist!=null){
            throw new UserException("Email already exist");
        }
        User newUser=User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .fullName(fullName)
                .birthDate(birthDate)
                .verification(new Varification())
                .build();
        userRepository.save(newUser);
        Authentication authentication=new UsernamePasswordAuthenticationToken(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token=jwtProvider.generateToken(authentication);

        AuthResponse response=AuthResponse.builder()
                .jwt(token)
                .status(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> sigin(@RequestBody User user){
        String username=user.getEmail();
        String password=user.getPassword();
        Authentication authentication=authenticate(username,password);

        String token=jwtProvider.generateToken(authentication);

        AuthResponse response=AuthResponse.builder()
                .jwt(token)
                .status(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails=customUserDetailsServiceImplementation.loadUserByUsername(email);
        if (userDetails==null){
            throw new BadCredentialsException("Invalid username...");
        }
        if (!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid username or password...");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }

}
