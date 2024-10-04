package com.lyphuc.identity_service.service;

import com.lyphuc.identity_service.dto.request.AuthenticationRequest;
import com.lyphuc.identity_service.dto.request.IntrospectRequest;
import com.lyphuc.identity_service.dto.request.LogoutRequest;
import com.lyphuc.identity_service.dto.request.RefreshRequest;
import com.lyphuc.identity_service.dto.response.AuthenticationResponse;
import com.lyphuc.identity_service.dto.response.IntrospectResponse;
import com.lyphuc.identity_service.entity.User;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
    AuthenticationResponse authenticate(AuthenticationRequest request);
    String generateToken(User user);

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
}
