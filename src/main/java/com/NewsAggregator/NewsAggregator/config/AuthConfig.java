package com.NewsAggregator.NewsAggregator.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class AuthConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers("/register","/verifyRegistration","/signin").permitAll() // Allow access to all learners endpoints
                                .anyRequest().authenticated() // Require authentication for any other request
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/hellow") // Custom login page
                        .permitAll() // Allow all users to access the login page
                ).addFilterBefore(
                        new JwtAuthenticationFilter(), // Custom JWT authentication filter
                        UsernamePasswordAuthenticationFilter.class
                );

        return httpSecurity.build();
    }
}
