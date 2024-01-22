package com.bytebite.backend.request;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String fullName;
    private String email;
    private String password;
}
