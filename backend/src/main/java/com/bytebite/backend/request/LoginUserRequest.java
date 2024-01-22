package com.bytebite.backend.request;

import lombok.Data;

@Data
public class LoginUserRequest {
    private String email;
    private String password;
}
