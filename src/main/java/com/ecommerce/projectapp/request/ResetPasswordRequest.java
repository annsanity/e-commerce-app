package com.ecommerce.projectapp.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {

    private String password;
    private String token;

}