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
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if (product.isPresent()) {
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
                .productPrices(product.getProductPrices())
                .build()).collect(Collectors.toList());
    }

    @Override
    public ProductResponse update(ProductRequest productRequest) {
        ProductResponse currentProduct = getById(productRequest.getProductId());
        if (currentProduct != null) {
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
        else System.out.println("Data with ID " + id + " not exist");
    }

    @Transactional(rollbackOn = Exception.class)
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
                .store(storeResponse.toBuilder()
                        .id(storeResponse.getId())
                        .noSiup(storeResponse.getNoSiup())
                        .storeName(storeResponse.getStoreName())
                        .phone(storeResponse.getPhone())
                        .address(storeResponse.getAddress())
                        .build())
                .build();
    }

    @Override
    public Page<ProductResponse> getAllByNameOrPrice(String name, Long maxPrice, Integer page, Integer size) {
        // pencarian berdasarkan product name
        // Specification untuk menentukan kriteria pencarian, disini criteria pencarian ditandakan dengan root, root yang dimaksud adalah entity product
        Specification<Product> productSpecification = (root, query, criteriaBuilder) -> {
            //Join digunakan untuk merelasikan antara product dan product price
            Join<Product, ProductPrice> productPrices = root.join("productPrices");
            //Predicate digunakan untuk menggunakan LIKE dimana nanti kita akan menggunakan kondisi pencarian parameter
            //disini kita akan mencari nama product atau harga yang sama atau harga dibawahnya, makanya menggunakan lessThanOrEquals
            List<Predicate> predicates = new ArrayList<>();
            if (name != null)
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            if (maxPrice != null)
                predicates.add(criteriaBuilder.lessThanOrEqualTo(productPrices.get("price"), maxPrice));
            //kode return mengembalikan query dimana pada dasarnya kita membangun klausa where yang sudah ditentukan dari predicate atau kriteria
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        //Pagination
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAll(productSpecification, pageable);

        // untuk mencari product yang pricenya active
        // ini digunakan untuk menyimpan response product yang baru
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products.getContent()) {
            // for disini digunakan untuk mengiterasi daftar product yang disimpan dalam object
            Optional<ProductPrice> productPrice = product.getProductPrices() // optional ini untuk mencari harga yang aktif
                    .stream()
                    .filter(ProductPrice::getIsActive).findFirst();
            if (productPrice.isEmpty())
                continue; // kondisi ini digunakan untuk memeriksa apakah productPrice kosong atau tidak, kalau kosong maka di skip
            Store store = productPrice.get().getStore(); // ini digunakan untuk jika harga product yang aktif ditemukan di store
            productResponses.add(toProductResponse(store, product, productPrice.get()));
        }

        // pagination
        return new PageImpl<>(productResponses, pageable, products.getTotalElements());
    }

    // extract method
    private static ProductResponse toProductResponse(Store store, Product product, ProductPrice productPrice) {
        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getName())
                .description(product.getDescription())
                .price(productPrice.getPrice())
                .stock(productPrice.getStock())
                .store(StoreResponse.builder()
                        .id(store.getId())
                        .storeName(store.getName())
                        .noSiup(store.getNoSiup())
                        .phone(store.getMobilePhone())
                        .address(store.getAddress())
                        .build())
                .build();
    }
}
