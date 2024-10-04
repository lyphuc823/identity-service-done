package com.lyphuc.identity_service.service.impl;

import com.lyphuc.identity_service.dto.request.PermissionRequest;
import com.lyphuc.identity_service.dto.response.PermissionResponse;
import com.lyphuc.identity_service.entity.Permission;
import com.lyphuc.identity_service.mapper.PermissionMapper;
import com.lyphuc.identity_service.repository.PermissionRepository;
import com.lyphuc.identity_service.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PermissionServiceImpl implements PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;
    @Override
    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);

        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }
    @Override
    public List<PermissionResponse> getAll(){
        return permissionRepository.findAll().stream().map(permissionMapper::toPermissionResponse).collect(Collectors.toList());
    }
    @Override
    public void delete(String permission){
        permissionRepository.deleteById(permission);
    }
}
