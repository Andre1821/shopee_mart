package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.dto.request.ProductRequest;
import com.enigma.shopeymart.dto.response.CommonResponse;
import com.enigma.shopeymart.dto.response.ProductResponse;
import com.enigma.shopeymart.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProductControllerTest {
    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createProduct() {
        // data dummy
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("Oreo");
        productRequest.setPrice(100L);

        // data response
        ProductResponse productResponse = new ProductResponse();
        productRequest.setProductId("1");
        productRequest.setProductName("Oreo");
        productRequest.setPrice(100L);

        when(productService.createProductAndProductPrice(productRequest)).thenReturn(productResponse);

        ResponseEntity<?> responseEntity = productController.createProduct(productRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        CommonResponse<ProductResponse> commonResponse = (CommonResponse<ProductResponse>) responseEntity.getBody();

        assertEquals(HttpStatus.CREATED.value(), commonResponse.getStatusCode());
        assertEquals("Successfully created new product", commonResponse.getMessage());
        assertEquals(productResponse, commonResponse.getData());
    }

    @Test
    void getAll() {
        List<ProductResponse> expectedResponse = new ArrayList<>();

        when(productService.getAll()).thenReturn(expectedResponse);

        List<ProductResponse> actualResponse = productController.getAll();

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getAllProdutPage() {
//        String nameProduct = "Oreo";
//        Long maxPrice = 100L;
//        Integer page = 0;
//        Integer size = 5;
//
//        Page<ProductResponse> productResponsePage = new PageImpl<>();
//
//        when(productService.getAllByNameOrPrice(nameProduct, maxPrice,page,size)).thenReturn()
    }
}