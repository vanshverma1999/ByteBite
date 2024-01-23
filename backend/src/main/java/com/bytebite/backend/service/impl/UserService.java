package com.bytebite.backend.service.impl;

import com.bytebite.backend.exception.UserException;
import com.bytebite.backend.model.User;
import com.bytebite.backend.model.enums.StatusCode;
import com.bytebite.backend.repository.UserRepository;
import com.bytebite.backend.request.ChangePasswordRequest;
import com.bytebite.backend.request.CreateUserRequest;
import com.bytebite.backend.request.LoginUserRequest;
import com.bytebite.backend.response.AuthResponse;
import com.bytebite.backend.response.Response;
import com.bytebite.backend.security.JwtTokenProvider;
import com.bytebite.backend.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse login(LoginUserRequest loginUserRequest) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserRequest.getEmail(), loginUserRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.generateTokenFromAuthentication(authentication);
    }

    @Override
    public User signUp(CreateUserRequest user) {
        if (!userRepository.existsByEmail(user.getEmail())) {
            return userRepository
                    .save(
                            User.builder()
                                    .email(user.getEmail())
                                    .password(passwordEncoder.encode(user.getPassword()))
                                    .fullName(user.getFullName())
                                    .role("CUSTOMER").build()
                    );
        }
        throw new UserException("User already present with email: " + user.getEmail());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Response resetPassword(ChangePasswordRequest input){
        User user =
                userRepository
                        .findByEmailIgnoreCase(input.getEmail())
                        .orElseThrow(
                                () -> new UserException("The email provided is not registered. Please check the email address and try again")
                        );
        user.setPassword(passwordEncoder.encode(input.getNewPassword()));
        userRepository.save(user);
        return Response.builder()
                .statusCode(StatusCode.OK_200)
                .message("Password Reset Successful.")
                .build();
    }

    public User findById(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new UserException("User not found for Id " + userId));
    }
}
