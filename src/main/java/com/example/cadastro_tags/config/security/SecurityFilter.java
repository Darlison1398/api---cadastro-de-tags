package com.example.cadastro_tags.config.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.cadastro_tags.model.User_admin_model;
import com.example.cadastro_tags.model.User_view_model;
import com.example.cadastro_tags.repository.User_admin_repository;
import com.example.cadastro_tags.repository.User_view_repository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private User_admin_repository user_admin_repository;

    @Autowired
    private User_view_repository user_view_repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = recoverToken(request);
        if (token != null) {
            try {
                String login = tokenService.validarToken(token);

                if (login != null) {

                    Optional<User_admin_model> user_admin_model = user_admin_repository.findByEmail(login);

                    if (user_admin_model.isPresent()) {
                        User_admin_model admin = user_admin_model.get();
                        var authentication = new UsernamePasswordAuthenticationToken(admin, null, admin.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                    } else {

                        Optional<User_view_model> user_view_model = user_view_repository.findByEmail(login);
                        if (user_view_model.isPresent()) {
                            User_view_model view = user_view_model.get();
                            var authentication = new UsernamePasswordAuthenticationToken(view, null, view.getAuthorities());
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }

                    }
                }
                
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Falha na autenticação: " + e.getMessage());
                return;
                
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
