package com.enigma.shopeymart.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "m_product_price")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, columnDefinition = "int check (stock > 0)")
    private Integer stock;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(nullable = false, columnDefinition = "bigint check (price > 0)")
    private Long price;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;
}
