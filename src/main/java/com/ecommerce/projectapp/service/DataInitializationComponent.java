package com.ecommerce.projectapp.service;

import com.ecommerce.projectapp.domain.USER_ROLE;
import com.ecommerce.projectapp.model.User;
import com.ecommerce.projectapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializationComponent implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passEncoder;

    public void run(String... args) {
        initializeAdminUser();
    }

    private void initializeAdminUser() {

        String adminUsername = "admin01@gmail.com";

        if(userRepository.findByEmail(adminUsername) == null){

            User adminUser = new User();

            adminUser.setPassword(passEncoder.encode("Beabadoobee"));
            adminUser.setFullName("Ana");
            adminUser.setEmail(adminUsername);
            adminUser.setRole(USER_ROLE.ROLE_ADMIN);

            User admin = userRepository.save(adminUser);
        }
    }
}