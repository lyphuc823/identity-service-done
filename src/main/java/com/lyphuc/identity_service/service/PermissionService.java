package com.lyphuc.identity_service.service;

import com.lyphuc.identity_service.dto.request.PermissionRequest;
import com.lyphuc.identity_service.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    PermissionResponse create(PermissionRequest request);

    List<PermissionResponse> getAll();

    void delete(String permission);
}
