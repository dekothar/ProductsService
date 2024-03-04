package com.scaler.products.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateProductRequestDto {

    private String title;
    private String description;
    private double price;
    private String category;
    private String image;
}
