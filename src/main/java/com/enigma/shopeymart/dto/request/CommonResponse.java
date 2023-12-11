package com.enigma.shopeymart.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CommonResponse<T>{
    private Integer statusCode;
    private String message;
    private T data;
}
