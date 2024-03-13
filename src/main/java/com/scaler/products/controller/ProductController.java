package com.scaler.products.controller;

import com.scaler.products.dto.Category;
import com.scaler.products.dto.ErrorsDto;
import com.scaler.products.dto.Product;
import com.scaler.products.dto.CreateProductRequestDto;
import com.scaler.products.exception.ProductNotFoundException;
import com.scaler.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    @Qualifier(value = "selfproductService")
    private ProductService productService;
    @Autowired
    private RestTemplate restTemplate;

    /*
     * This Controller used to find the Product details based on product Id
     * End Points-> GET/producs/{id}
     */
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public Product getProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
        return productService.getProduct(id);

    }

    /*
     * This Controller used to create the Product details in the System
     * End Points-> POST/products/
     */
    @RequestMapping(value = "/products/", method = RequestMethod.POST)
    public Product createProduct(@RequestBody CreateProductRequestDto createProductRequestDto) {
        return productService.createProduct(createProductRequestDto.getTitle(), createProductRequestDto.getPrice(), createProductRequestDto.getDescription(), createProductRequestDto.getCategory(), createProductRequestDto.getImage());
    }

    /*
     * This Controller used to delete the Product details based on product Id
     * End Points-> DELETE/producs/{id}
     */
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public Product deleteProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
        productService.deleteProduct(id);
        return getProductById(id);
    }

    /*
     * This Controller used to find the  Product details based on Category it is associated with
     * End Points-> GET/products/category/{cateGoryType}
     */
    @RequestMapping(value = "/products/category/{cateGoryType}", method = RequestMethod.GET)
    public List<Product> getProductInSpecificCategory(@PathVariable("cateGoryType") String cateGoryType) {
        return productService.getProductInCategory(cateGoryType);
    }

    /*
     * This Controller used to find All the  Categories for the Products
     * End Points-> GET/products/categories
     */
    @RequestMapping(value = "/products/categories", method = RequestMethod.GET)
    public List<Category> getAllCategories() {
        return productService.getAllCategories();
    }

    /*
     * This Controller used to find All the Products in the System
     * End Points-> GET/products
     */
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        ResponseEntity<List<Product>> response = new ResponseEntity<>(products, HttpStatus.ACCEPTED);
        return response;
    }

    /*
     * This Controller used to update the Product details for the given pid
     * End Points-> PUT/products/{id}
     */
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody CreateProductRequestDto createProductRequestDto) throws ProductNotFoundException {
        return productService.updateProduct(id, createProductRequestDto.getTitle(), createProductRequestDto.getPrice(), createProductRequestDto.getDescription(), createProductRequestDto.getCategory(), createProductRequestDto.getImage());
    }

    /*
     * This Controller used to count No of Active Products in the System
     * End Points-> GET/products/count
     */
    @RequestMapping(value = "/products/count", method = RequestMethod.GET)
    public String countProducts() throws ProductNotFoundException {
        return productService.countProduct();
    }

    /*
     * This Controller used to to update the Product attribute details for the given pid which was modified in request by User
     * End Points-> PATCH/products/{id}
     */
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PATCH)
    public Product updateSpecificFieldOfProduct(@PathVariable("id") Long id,@RequestBody CreateProductRequestDto createProductRequestDto) throws ProductNotFoundException {
        return productService.updateSpecificFieldOfProduct(id,createProductRequestDto.getTitle(), createProductRequestDto.getPrice(), createProductRequestDto.getDescription(), createProductRequestDto.getCategory(), createProductRequestDto.getImage());
    }
}
