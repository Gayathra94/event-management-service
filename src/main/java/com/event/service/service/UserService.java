package com.event.service.service;

import com.event.service.dto.AuthResponse;
import com.event.service.dto.LoginDTO;
import com.event.service.dto.UserDTO;
import com.event.service.model.User;
import com.event.service.repository.UserRepository;
import com.event.service.security.JWTService;
import com.event.service.util.EventUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .build();
    }
    public User registerUser(User user){

        if (userRepository.findUserByUsername(user.getUsername()) != null) {
            throw new DuplicateKeyException("Username '" + user.getUsername() + "' already exists.");
        }

        if(user.getEmail() != null){
            if (userRepository.findUserByEmail(user.getEmail()) != null) {
                throw new DuplicateKeyException("Email '" + user.getEmail() + "' already exists.");
            }
        }

        String encodedPassword = new BCryptPasswordEncoder(12).encode(user.getPassword());
        user.setId(EventUtil.generateEventId());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public ResponseEntity<?> loginUser(LoginDTO loginDTO){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

            String token = jwtService.generateToken(loginDTO.getUsername());


            ResponseCookie clearOldCookie = ResponseCookie.from("EMS_COOKIE", "")
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(0) // This deletes the cookie
                    .sameSite("Strict")
                    .build();


            ResponseCookie newCookie = ResponseCookie.from("EMS_COOKIE", token)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(Duration.ofDays(1))
                    .sameSite("Strict")
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, clearOldCookie.toString()) // delete old one
                    .header(HttpHeaders.SET_COOKIE, newCookie.toString())      // set new one
                    .body(new AuthResponse("F", true));

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)  .body("Username or password is incorrect.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) .body("An unexpected error occurred.");
        }
    }

    public UserDTO getUserDetails(String username) {
        User user = userRepository.findUserByUsername(username);
        return new UserDTO(user);
    }
}
