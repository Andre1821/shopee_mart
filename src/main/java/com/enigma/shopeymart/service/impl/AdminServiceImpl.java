package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.AdminRequest;
import com.enigma.shopeymart.dto.response.AdminResponse;
import com.enigma.shopeymart.entity.Admin;
import com.enigma.shopeymart.repository.AdminRepository;
import com.enigma.shopeymart.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    @Override
    public AdminResponse create(AdminRequest adminRequest) {
        return null;
    }

    @Override
    public AdminResponse createNewAdmin(Admin request) {
        Admin admin = adminRepository.saveAndFlush(request);
        return AdminResponse.builder()
                .id(admin.getId())
                .name(admin.getName())
                .phoneNumber(admin.getPhoneNumber())
                .build();
    }

    @Override
    public AdminResponse getById(String id) {
        return null;
    }

    @Override
    public List<AdminResponse> getAll() {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream().map(admin -> AdminResponse.builder()
                .id(admin.getId())
                .name(admin.getName())
                .phoneNumber(admin.getPhoneNumber())
                .build()).toList();
    }

    @Override
    public AdminResponse update(AdminRequest adminRequest) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
