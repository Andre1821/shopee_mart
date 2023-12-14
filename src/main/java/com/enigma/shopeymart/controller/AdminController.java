package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.constant.AppPath;
import com.enigma.shopeymart.dto.response.AdminResponse;
import com.enigma.shopeymart.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.ADMIN)
public class AdminController {
    private final AdminService adminService;

    @GetMapping
    public List<AdminResponse> getAllAdmin(){
        return adminService.getAll();
    }

}
