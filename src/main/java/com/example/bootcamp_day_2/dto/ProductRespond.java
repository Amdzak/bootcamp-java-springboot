package com.example.bootcamp_day_2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRespond<T> {

    private String message;
    private T data;
    private String erros;

}
