package com.example.example_auth.filter;

import com.example.example_auth.exception.custom.UserNotFoundException;
import com.example.example_auth.model.entity.User;
import com.example.example_auth.repository.UserRepository;
import com.example.example_auth.utils.PasswordUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
public class BasicAuthenticationFilter implements Filter {

    private final UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpservletResponse = (HttpServletResponse) servletResponse;

        String authHeader = httpServletRequest.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            httpservletResponse.setHeader("WWW-Authenticate", "Basic realm=\"Access to the site\"");
            httpservletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication required");
            return;
        }

        try {
            String base64Credentials = authHeader.substring(6).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
            String[] value = credentials.split(":", 2);

            if (value.length != 2) {
                httpservletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid authentication header");
                return;
            }

            String username = value[0];
            String password = value[1];

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            if (PasswordUtils.checkPassword(password, user.getPassword())) {
                httpServletRequest.setAttribute("authenticatedUser", user);
                filterChain.doFilter(httpServletRequest, httpservletResponse);
            } else {
                log.warn("Failed authentication attempt for user: {}", username);
                httpservletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials");
            }

        } catch (UserNotFoundException e) {
            log.warn("Authentication attempt for non-existent user");
            httpservletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials");
        } catch (IllegalArgumentException e) {
            httpservletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Base64 encoding");
        }
    }
}