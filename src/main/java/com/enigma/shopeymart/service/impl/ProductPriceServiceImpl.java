package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.entity.ProductPrice;
import com.enigma.shopeymart.repository.ProductPriceRepository;
import com.enigma.shopeymart.service.ProductPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductPriceServiceImpl implements ProductPriceService {
    private final ProductPriceRepository productPriceRepository;
    @Override
    public ProductPrice create(ProductPrice productPrice) {
        return productPriceRepository.save(productPrice);
    }
}
