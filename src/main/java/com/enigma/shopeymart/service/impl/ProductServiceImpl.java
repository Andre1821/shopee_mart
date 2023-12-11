package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.ProductRequest;
import com.enigma.shopeymart.dto.response.ProductResponse;
import com.enigma.shopeymart.dto.response.StoreResponse;
import com.enigma.shopeymart.entity.Product;
import com.enigma.shopeymart.entity.ProductPrice;
import com.enigma.shopeymart.entity.Store;
import com.enigma.shopeymart.repository.ProductRepository;
import com.enigma.shopeymart.service.ProductPriceService;
import com.enigma.shopeymart.service.ProductService;
import com.enigma.shopeymart.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final StoreService storeService;
    private final ProductPriceServiceImpl productPriceServiceImpl;

    @Override
    public ProductResponse create(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getProductName())
                .description(productRequest.getDescription())
                .build();
        productRepository.save(product);
        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getName())
                .description(product.getDescription())
                .build();
    }

    @Override
    public ProductResponse getById(String id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()){
            return ProductResponse.builder()
                    .id(product.get().getId())
                    .productName(product.get().getName())
                    .description(product.get().getDescription())
                    .build();
        }
        return null;
    }

    @Override
    public List<ProductResponse> getAll() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> ProductResponse.builder()
                .id(product.getId())
                .productName(product.getName())
                .description(product.getDescription())
                .build()).collect(Collectors.toList());
    }

    @Override
    public ProductResponse update(ProductRequest productRequest) {
        ProductResponse currentProduct = getById(productRequest.getProductId());
        if (currentProduct != null){
            Product product = Product.builder()
                    .id(productRequest.getProductId())
                    .name(productRequest.getProductName())
                    .description(productRequest.getDescription())
                    .build();
            productRepository.save(product);
            return ProductResponse.builder()
                    .id(product.getId())
                    .productName(product.getName())
                    .description(product.getDescription())
                    .build();
        }
        return null;
    }

    @Override
    public void delete(String id) {
        ProductResponse currentProduct = getById(id);
        if (currentProduct != null) productRepository.deleteById(id);
        else System.out.println("Data with ID "+id+" not exist");
    }

    @Override
    public ProductResponse createProductAndProductPrice(ProductRequest productRequest) {
        StoreResponse storeResponse = this.storeService.getById(productRequest.getStoreId().getId());
        Store stores = Store.builder()
                .id(storeResponse.getId())
                .build();

        Product product = Product.builder()
                .name(productRequest.getProductName())
                .description(productRequest.getDescription())
                .build();
        productRepository.saveAndFlush(product);

        ProductPrice productPrice = ProductPrice.builder()
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .isActive(true)
                .product(product)
                .store(stores)
                .build();
        productPriceServiceImpl.create(productPrice);

        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getName())
                .description(product.getDescription())
                .price(productPrice.getPrice())
                .stock(productPrice.getStock())
                .store(StoreResponse.builder()
                        .id(storeResponse.getId())
                        .noSiup(storeResponse.getNoSiup())
                        .storeName(storeResponse.getStoreName())
                        .phone(storeResponse.getPhone())
                        .address(storeResponse.getAddress())
                        .build())
                .build();
    }
}
