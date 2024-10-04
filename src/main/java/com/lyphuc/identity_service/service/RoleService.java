package com.lyphuc.identity_service.service;

import com.lyphuc.identity_service.dto.request.RoleRequest;
import com.lyphuc.identity_service.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse create(RoleRequest request);

    List<RoleResponse> getAll();

    void delete(String role);
}
