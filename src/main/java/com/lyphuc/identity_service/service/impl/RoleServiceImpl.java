package com.lyphuc.identity_service.service.impl;

import com.lyphuc.identity_service.dto.request.RoleRequest;
import com.lyphuc.identity_service.dto.response.RoleResponse;
import com.lyphuc.identity_service.mapper.RoleMapper;
import com.lyphuc.identity_service.repository.PermissionRepository;
import com.lyphuc.identity_service.repository.RoleRepository;
import com.lyphuc.identity_service.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;
    @Override
    public RoleResponse create(RoleRequest request){
        var role = roleMapper.toRole(request);
        var permissions = permissionRepository.findAllById(request.getPermissions());

        role.setPermissions(new HashSet<>(permissions));

        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    @Override
    public List<RoleResponse> getAll(){
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }
    @Override
    public void delete(String role){
        permissionRepository.deleteById(role);
    }
}
