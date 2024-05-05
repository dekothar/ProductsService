package com.scaler.products.service;

import com.scaler.products.dto.CategoryDto;
import com.scaler.products.dto.ProductDto;
import com.scaler.products.exception.ProductNotFoundException;


import java.util.List;

public interface ProductService {

    // This api fetches all the products and display certain records on page with sorting based on attributes.
    List<ProductDto> getAllProducts(final String pgNo, final String noOfRecords);

    // This api fetches all the products.
    List<ProductDto> getAllProducts();

    // This api fetches the products based on productId.
    ProductDto getProductById(final Long productId) throws ProductNotFoundException;

    // This api will create the Products.
    ProductDto createProduct(final String title, final double price, final String description, final String category, final String image);

    // This api used to delete the Product based on productId.
    void deleteProduct(final Long id) throws ProductNotFoundException;

    // This api fetches all the products in a specific category.
    List<ProductDto> getProductInCategory(final String categoryType);

    // This api fetches all the products in a specific category and display certain categories on each page.
    List<ProductDto> getProductInCategory(final String categoryType, final int pgNo, final int noOfRecords);

    // This api fetches all the categories.
    List<CategoryDto> getAllCategories();

    // This api used to update  the details of the product for given product id.
    ProductDto updateProduct(final Long id, final String title, final double price, final String description, final String category, final String image) throws ProductNotFoundException;

    // This api count no of Active Products in System.
    default String countProduct() {
        System.out.println("It will count no of Active Products");
        return null;
    }

    // This api updates the fields of Products if it is different from Existing value present in system to that of provided in request by User.
    default ProductDto updateSpecificFieldOfProduct(final Long id, final String title, final double price, final String description, final String category, final String image) throws ProductNotFoundException {
        System.out.println("Update Specific Field Of Product");
        return new ProductDto();
    }
}