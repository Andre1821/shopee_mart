package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.constant.AppPath;
import com.enigma.shopeymart.dto.request.CustomerRequest;
import com.enigma.shopeymart.dto.response.CustomerResponse;
import com.enigma.shopeymart.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.CUSTOMER)
public class CustomerController {
    private final CustomerService customerService;
    @PostMapping()
    public CustomerResponse createCustomer(@RequestBody CustomerRequest customerRequest){
        return customerService.create(customerRequest);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')") // untuk membatasi hak akses fitur
    public List<CustomerResponse> getAllCustomer(){
        return customerService.getAll();
    }

    @GetMapping(value = "/{id}")
    public CustomerResponse getByIdCustomer(@PathVariable String id){
        return customerService.getById(id);
    }

    @PutMapping
    public CustomerResponse updateCustomer(@RequestBody CustomerRequest customerRequest){
        return customerService.update(customerRequest);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SELER')") // untuk membuat beberapa role bisa akses
    public void deleteCustomer(@PathVariable String id){
        customerService.delete(id);
    }
}
