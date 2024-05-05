package com.scaler.products.dto;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class ProductDto extends BaseModelDto {

    private String title;
    private String desc;
    private double price;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private CategoryDto category;
    private String img;
}
