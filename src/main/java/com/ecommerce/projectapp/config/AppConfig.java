package com.ecommerce.projectapp.config;

@Configuration
@EnableWebSecurity
public class AppConfig {

    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionizeHttpRequests(Authorize -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(Authorize -> 
            .requestMatchers("/api/admin/**").hasAnyRole("SHOP_OWNER", "ADMIN")
            .requestMatchers("/api/**").authenticated()
            .requestMatchers("/api/products/*/reviews").permitAll()
            .anyRequest().permitAll()

            )
            .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
            .csrf(csrf -> csrf.disable)
            .cors(cors -> configurationSource(corsConfigurationSource()));

            return http.build();
    }

    // CORS configuration
    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cfg = new CorsConfiguration();
                cfg.setAllowedOrigins(Arrays.asList("https://my-ecommerce-bazaar.vercel.app",
                        "http://localhost:3000"));
                cfg.setAllowedMethods(Collections.singletonList("*"));
                cfg.setAllowCredentials(true);
                cfg.setAllowedHeaders(Collections.singletonList("*"));
                cfg.setExposedHeaders(Arrays.asList("Authorization"));
                cfg.setMaxAge(3600L);
                return cfg;
            }
        };

        @Bean
        PasswordEncoder passwordEncoder() {
            return new BCryptPassswordEncoder();
        }

        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }

}
