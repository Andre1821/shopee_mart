package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.CustomerRequest;
import com.enigma.shopeymart.dto.response.CustomerResponse;
import com.enigma.shopeymart.entity.Customer;
import com.enigma.shopeymart.repository.CustomerRepository;
import com.enigma.shopeymart.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    @Override
    public CustomerResponse create(CustomerRequest customerRequest) {
        Customer customer = Customer.builder()
                .name(customerRequest.getName())
                .email(customerRequest.getEmail())
                .mobilePhone(customerRequest.getMobilePhone())
                .address(customerRequest.getAddress())
                .build();
        customerRepository.save(customer);
        return CustomerResponse.builder()
                .id(customer.getId())
                .customerName(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getMobilePhone())
                .address(customer.getAddress())
                .build();
    }

    @Override
    public CustomerResponse getById(String id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        return CustomerResponse.builder()
                .id(customer.getId())
                .customerName(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getMobilePhone())
                .address(customer.getAddress())
                .build();
    }

    @Override
    public List<CustomerResponse> getAll() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(customer -> CustomerResponse.builder()
                .id(customer.getId())
                .customerName(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getMobilePhone())
                .address(customer.getAddress())
                .build()).collect(Collectors.toList());
    }

    @Override
    public CustomerResponse update(CustomerRequest customerRequest) {
        CustomerResponse currentCustomer = getById(customerRequest.getId());
        if (currentCustomer != null){
            Customer customer = Customer.builder()
                .id(customerRequest.getId())
                .name(customerRequest.getName())
                .email(customerRequest.getEmail())
                .mobilePhone(customerRequest.getMobilePhone())
                .address(customerRequest.getAddress())
                .build();
            customerRepository.save(customer);
            return CustomerResponse.builder()
                    .id(customer.getId())
                    .customerName(customer.getName())
                    .email(customer.getEmail())
                    .phone(customer.getMobilePhone())
                    .address(customer.getAddress())
                    .build();
        }
        return null;
    }

    @Override
    public void delete(String id) {
        CustomerResponse currentCustomer = getById(id);
        if (currentCustomer != null) customerRepository.deleteById(id);
        else System.out.println("Data with ID "+id+" not exist");
    }
}
