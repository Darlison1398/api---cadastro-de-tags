package com.example.cadastro_tags.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain( HttpSecurity http) throws Exception {

        http
           .csrf( csrf -> csrf.disable())
           .sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
           .authorizeRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/userView/loginUserView").permitAll()
                        .requestMatchers(HttpMethod.GET, "/tags").hasAnyRole("ADMIN", "VIEW")
                        .requestMatchers(HttpMethod.POST, "/tags").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/tags/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/tags/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/userView/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/userView/getUserById/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/userView/updateUserView/**").hasAnyRole("ADMIN", "VIEW")
                        .requestMatchers(HttpMethod.DELETE, "/userView/deleteUserView/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/userView/allUsers").hasRole("ADMIN")
                        .anyRequest().authenticated()
           
           )

           .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

           return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    
}
