package com.enigma.shopeymart.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "m_posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Posts {
    @Id
    private Integer id;
    private String title;
    private String body;
    @Column(name = "user_id")
    private Integer userId;
}
