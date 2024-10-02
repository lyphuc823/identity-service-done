package com.lyphuc.identity_service.service;

import com.lyphuc.identity_service.dto.request.AuthenticationRequest;
import com.lyphuc.identity_service.dto.request.IntrospectRequest;
import com.lyphuc.identity_service.dto.response.AuthenticationResponse;
import com.lyphuc.identity_service.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
    AuthenticationResponse authenticate(AuthenticationRequest request);
    String generateToken(String username);
}
