package com.event.service.service;

import com.event.service.dto.AuthResponse;
import com.event.service.dto.LoginDTO;
import com.event.service.model.User;
import com.event.service.repository.UserRepository;
import com.event.service.security.JWTService;
import com.event.service.util.EventUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public AuthResponse loginUser(LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        if(authentication.isAuthenticated()){
            return new AuthResponse(jwtService.generateToke(loginDTO.getUsername()), authentication.isAuthenticated());
        }
        return new AuthResponse(null, authentication.isAuthenticated());
    }
}
