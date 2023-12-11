package com.enigma.shopeymart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductResponse {
    private String id;
    private String productName;
    private String description;
    private Long price;
    private Integer stock;
    private StoreResponse store;
}
