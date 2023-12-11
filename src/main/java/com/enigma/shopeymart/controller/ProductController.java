package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.dto.request.CommonResponse;
import com.enigma.shopeymart.dto.request.ProductRequest;
import com.enigma.shopeymart.dto.response.ProductResponse;
import com.enigma.shopeymart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest){
        ProductResponse productResponse = productService.createProductAndProductPrice(productRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<ProductResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully created new product")
                        .data(productResponse)
                        .build());
    }
}
