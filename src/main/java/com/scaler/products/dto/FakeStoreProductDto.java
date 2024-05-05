package com.scaler.products.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FakeStoreProductDto {

    private Long id;
    private String title;
    private double price;
    private String category;
    private String description;
    private String image;

    public ProductDto convertToProduct() {
        ProductDto product = new ProductDto();
        product.setId(id);
        product.setTitle(title);
        product.setPrice(price);
        product.setDesc(description);
        product.setImg(image);
        CategoryDto category = new CategoryDto();
        category.setTitle(this.category);
        product.setCategory(category);
        product.setActive(1);
        product.setCreatedOn(new Date());
        product.setLastModified(new Date());
        return product;
    }
}
