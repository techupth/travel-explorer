package com.travelapp.travel_explorer.service;

import com.travelapp.travel_explorer.dto.AuthResponse;
import com.travelapp.travel_explorer.dto.LoginRequest;
import com.travelapp.travel_explorer.dto.RegisterRequest;
import com.travelapp.travel_explorer.dto.UserDto;
import com.travelapp.travel_explorer.entity.User;
import com.travelapp.travel_explorer.exception.DuplicateEmailException;
import com.travelapp.travel_explorer.exception.ResourceNotFoundException;
import com.travelapp.travel_explorer.repository.UserRepository;
import com.travelapp.travel_explorer.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("This email is already registered");
        }
        
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setDisplayName(request.getDisplayName());
        
        User savedUser = userRepository.save(user);
        
        String token = jwtTokenProvider.generateToken(savedUser.getEmail(), savedUser.getId());
        
        UserDto userDto = convertToDto(savedUser);
        return new AuthResponse(token, userDto);
    }
    
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        String token = jwtTokenProvider.generateToken(user.getEmail(), user.getId());
        
        UserDto userDto = convertToDto(user);
        return new AuthResponse(token, userDto);
    }
    
    public UserDto getCurrentUser(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return convertToDto(user);
    }
    
    private UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getDisplayName(),
                user.getCreatedAt()
        );
    }
}
