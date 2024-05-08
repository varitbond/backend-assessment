package com.varit.backend.assessment.service;

import com.varit.backend.assessment.model.authentication.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private AuthService authService;

    @Test
    public void testHandleCallback_Success() {
        // Mock
        when(request.getParameter("id_token")).thenReturn("mockIdToken");
        // Call
        LoginResponse response = authService.handleCallback(request);
        // Verify
        assertEquals("Bearer mockIdToken", response.getAccessToken());
    }
}
