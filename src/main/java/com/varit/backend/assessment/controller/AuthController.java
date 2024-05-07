package com.varit.backend.assessment.controller;

import com.varit.backend.assessment.model.authentication.LoginResponse;
import com.varit.backend.assessment.model.response.ResponseModel;
import com.varit.backend.assessment.model.response.ResponseStatus;
import com.varit.backend.assessment.model.response.ResponseStatusEnum;
import com.varit.backend.assessment.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/callback")
    public ResponseEntity<ResponseModel<LoginResponse>> handleCallback(HttpServletRequest request) {
        var loginResponse = authService.handleCallback(request);
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        ResponseModel<LoginResponse> response = new ResponseModel<>();
        response.setStatus(status);
        response.setData(loginResponse);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}

