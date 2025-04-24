package com.ecommerce.projectapp.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
