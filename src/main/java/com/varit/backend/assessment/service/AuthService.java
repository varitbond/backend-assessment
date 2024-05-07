package com.varit.backend.assessment.service;


import com.varit.backend.assessment.model.authentication.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    public LoginResponse handleCallback(HttpServletRequest request) {
        String idToken = request.getParameter("id_token");
        String tokenType = "Bearer";
        // Return Bearer ID token
        return new LoginResponse(tokenType + " " + idToken);
    }
}
