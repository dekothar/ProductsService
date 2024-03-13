package com.scaler.products.service;

import com.scaler.products.dto.Category;
import com.scaler.products.dto.Product;
import com.scaler.products.exception.ProductNotFoundException;


import java.util.List;

public interface ProductService {

    // This api fetch all the products
    List<Product> getAllProducts();

    // This api fetch  the products based on productId
    Product getProduct(Long productId) throws ProductNotFoundException;

    // This api will create the Products
    Product createProduct(String title, double price, String description, String category, String image);

    // This api delete the Product based on productId
    void deleteProduct(Long id) throws ProductNotFoundException;

    // This api fetch all the products in a specific category
    List<Product> getProductInCategory(String categoryType);

    // This api fetch all the categories
    List<Category> getAllCategories();

    // This api updates details of the product for given product Id
    Product updateProduct(Long id, String title, double price, String description, String category, String image) throws ProductNotFoundException;

    // This api count no of Active Products in System
    String countProduct();

    // This api updates the fields of Products if it is different from Existing value present in system to that of provided in request by User
    Product updateSpecificFieldOfProduct(Long id, String title, double price, String description, String category, String image);

}
