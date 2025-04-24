package com.ecommerce.projectapp.config;

import com.ecommerce.projectapp.domain.USER_ROLE;
import com.ecommerce.projectapp.model.Cart;
import com.ecommerce.projectapp.model.User;
import com.ecommerce.projectapp.repository.CartRepository;
import com.ecommerce.projectapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Value("${ADMIN_EMAIL:admin@vendorhub.com}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD:VendorHub2024!Admin}")
    private String adminPassword;

    @Bean
    public CommandLineRunner initData(UserRepository userRepository,
                                      CartRepository cartRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            // Check if admin exists
            if (userRepository.findByEmail(adminEmail) == null) {
                // Create admin
                User admin = new User();
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setFullName("VendorHub Admin");
                admin.setMobile("9876543210");
                admin.setRole(USER_ROLE.ROLE_ADMIN);
                admin = userRepository.save(admin);

                // Create cart for admin
                Cart adminCart = new Cart();
                adminCart.setUser(admin);
                cartRepository.save(adminCart);

                logger.info("Admin user created: {}", adminEmail);
            }
        };
    }


    @Configuration
    public class EnvConfig {
        public EnvConfig() {
            Dotenv dotenv = Dotenv.configure().load();
            // Optionally, you can set system properties if needed
            dotenv.entries().forEach(entry ->
                    System.setProperty(entry.getKey(), entry.getValue())
            );
        }
    }
}