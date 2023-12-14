package com.enigma.shopeymart.service;

import com.enigma.shopeymart.dto.request.AdminRequest;
import com.enigma.shopeymart.dto.response.AdminResponse;
import com.enigma.shopeymart.entity.Admin;

import java.util.List;

public interface AdminService {
    AdminResponse create(AdminRequest adminRequest);
    AdminResponse createNewAdmin(Admin request);
    AdminResponse getById(String id);
    List<AdminResponse> getAll();
    AdminResponse update(AdminRequest adminRequest);
    void delete(String id);
}
