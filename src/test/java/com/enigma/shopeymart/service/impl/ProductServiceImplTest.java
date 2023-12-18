package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.ProductRequest;
import com.enigma.shopeymart.dto.response.ProductResponse;
import com.enigma.shopeymart.dto.response.StoreResponse;
import com.enigma.shopeymart.entity.Product;
import com.enigma.shopeymart.entity.ProductPrice;
import com.enigma.shopeymart.repository.ProductRepository;
import com.enigma.shopeymart.service.ProductPriceService;
import com.enigma.shopeymart.service.ProductService;
import com.enigma.shopeymart.service.StoreService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceImplTest {

    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final ProductPriceService productPriceService = mock(ProductPriceService.class);
    private final StoreService storeService = mock(StoreService.class);
    private final ProductService productService = new ProductServiceImpl(productRepository, storeService, productPriceService);

    public void setUp(){
        //reset mock behavior
        reset(productRepository, storeService, productPriceService);
    }

    @Test
    void createProductAndProductPrice() {
        // store request
        StoreResponse dummyStore = new StoreResponse();
        dummyStore.setId("store1");
        dummyStore.setStoreName("Berkah Selalu");
        dummyStore.setNoSiup("12345");

        when(storeService.getById(anyString())).thenReturn(dummyStore);

        // Save And Flush product
        Product saveProduct = new Product();
        saveProduct.setName("Oreo");
        saveProduct.setDescription("Hitam Enak");

        when(productRepository.saveAndFlush(any(Product.class))).thenReturn(saveProduct);

        // data dummy request product
        ProductRequest dummyProductRequest = mock(ProductRequest.class);
        when(dummyProductRequest.getProductName()).thenReturn(saveProduct.getName());
        when(dummyProductRequest.getDescription()).thenReturn(saveProduct.getDescription());
        when(dummyProductRequest.getPrice()).thenReturn(10000L);
        when(dummyProductRequest.getStock()).thenReturn(10);
        when(dummyProductRequest.getStoreId()).thenReturn(dummyStore);

        // call method createProductAndProductPrice
        ProductResponse productResponse = productService.createProductAndProductPrice(dummyProductRequest);

        // validate response
        assertNotNull(productResponse);
        assertEquals(saveProduct.getName(), productResponse.getProductName());

        // validate that the product price was set correct
        assertEquals(dummyProductRequest.getPrice(), productResponse.getPrice());
        assertEquals(dummyProductRequest.getStock(), productResponse.getStock());

        // validate interaction with store
        assertEquals(dummyStore.getId(), productResponse.getStore().getId());
        assertEquals(dummyStore.getStoreName(), productResponse.getStore().getStoreName());
        assertEquals(dummyStore.getNoSiup(), productResponse.getStore().getNoSiup());

        // verify interaction with mock object
        verify(storeService).getById(anyString());
        verify(productRepository).saveAndFlush(any(Product.class));
        verify(productPriceService).create(any(ProductPrice.class));


    }
}