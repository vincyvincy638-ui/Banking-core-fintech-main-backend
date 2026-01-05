package com.fintech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
public class SecurityConfig {

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                http
                                .cors(Customizer.withDefaults()) // ✅ CORS enable
                                .csrf(csrf -> csrf.disable()) // ✅ REST API
                                .authorizeHttpRequests(auth -> auth

                                                // ✅ WEEK 3 – PUBLIC API
                                                .requestMatchers("/api/portfolio/**").permitAll()

                                                // USER + ADMIN
                                                .requestMatchers(
                                                                "/api/user/**",
                                                                "/api/transactions/**",
                                                                "/api/transfer/**",
                                                                "/api/stocks/**")
                                                .hasAnyRole("USER", "ADMIN")

                                                // ADMIN only
                                                .requestMatchers("/api/admin/**")
                                                .hasRole("ADMIN")

                                                // everything else needs login
                                                .anyRequest().authenticated())
                                .httpBasic(Customizer.withDefaults());

                return http.build();
        }

        @Bean
        public InMemoryUserDetailsManager userDetailsService(PasswordEncoder encoder) {

                UserDetails admin = User.withUsername("admin")
                                .password(encoder.encode("admin123"))
                                .roles("ADMIN")
                                .build();

                UserDetails user = User.withUsername("user")
                                .password(encoder.encode("user123"))
                                .roles("USER")
                                .build();

                return new InMemoryUserDetailsManager(admin, user);
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}
