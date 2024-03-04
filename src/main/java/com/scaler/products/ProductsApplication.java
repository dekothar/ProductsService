package com.scaler.products;

import com.scaler.products.controller.ProductController;
import com.scaler.products.service.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductsApplication {

    private ProductService productService;
    private ProductController productController;
    public ProductsApplication(ProductController p, ProductService p1){
        this.productController=p;
        this.productService=p1;
    }
    public static void main(String[] args) {

        SpringApplication.run(ProductsApplication.class, args);
    }

}
