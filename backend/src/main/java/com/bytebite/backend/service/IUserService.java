package com.bytebite.backend.service;

import com.bytebite.backend.model.User;
import com.bytebite.backend.request.CreateUserRequest;
import com.bytebite.backend.request.LoginUserRequest;
import com.bytebite.backend.response.AuthResponse;

import java.util.List;

public interface IUserService {
    User signUp(CreateUserRequest user);
    AuthResponse login(LoginUserRequest loginUserRequest);
    List<User> getAllUsers();
}
