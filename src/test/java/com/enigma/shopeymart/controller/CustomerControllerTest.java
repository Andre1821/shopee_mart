package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.dto.request.CustomerRequest;
import com.enigma.shopeymart.dto.response.CustomerResponse;
import com.enigma.shopeymart.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @BeforeEach
    public void setUp(){
        // Inisialisation mock dan controller
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createCustomer() {
        CustomerResponse customerResponse = new CustomerResponse();
        CustomerRequest customerRequest = new CustomerRequest();

        when(customerService.create(customerRequest)).thenReturn(customerResponse);

        CustomerResponse actualResponse = customerController.createCustomer(customerRequest);

        assertEquals(customerResponse, actualResponse);
    }

    @Test
    void getAllCustomer() {
        List<CustomerResponse> expectedResponses = new ArrayList<>();

        when(customerService.getAll()).thenReturn(expectedResponses);

        List<CustomerResponse> actualResponse = customerController.getAllCustomer();

        assertEquals(expectedResponses, actualResponse);

    }

    @Test
    void getByIdCustomer() {
        String customerId = "1";
        CustomerResponse expectedResponse = new CustomerResponse();

        when(customerService.getById(customerId)).thenReturn(expectedResponse);

        CustomerResponse actualReponse = customerController.getByIdCustomer(customerId);

        assertEquals(expectedResponse,actualReponse);
    }

    @Test
    void updateCustomer() {
        CustomerResponse expectedResponse = new CustomerResponse();
        CustomerRequest request = new CustomerRequest();

        when(customerService.update(request)).thenReturn(expectedResponse);

        CustomerResponse actualResponse = customerController.updateCustomer(request);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void deleteCustomer() {
        String customerId = "1";
        customerController.deleteCustomer(customerId);
        verify(customerService, times(1)).delete(customerId);
    }
}