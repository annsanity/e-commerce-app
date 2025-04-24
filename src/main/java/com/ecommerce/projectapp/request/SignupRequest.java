package com.ecommerce.projectapp.request;

import lombok.Data;

@Data
public class SignupRequest {
    private String email;
    private String fullName;
    private String mobile;
    private String password;
}
