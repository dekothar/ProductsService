package com.scaler.products.controller;

import com.scaler.products.dto.Category;
import com.scaler.products.dto.Product;
import com.scaler.products.dto.CreateProductRequestDto;
import com.scaler.products.service.ProductService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class ProductController {

private ProductService productService;

private RestTemplate restTemplate;


public ProductController(ProductService productService,RestTemplate restTemplate){
 this.productService =productService;
 this.restTemplate=restTemplate;
}


//GET/producs/1 or GET/products/2
@RequestMapping(value = "/products/{id}",method = RequestMethod.GET)
public Product getProductById(@PathVariable("id") Long id){
 return productService.getProduct(id);

}

   // POST/products
    @RequestMapping(value = "/products/",method = RequestMethod.POST)
    public Product createProduct(@RequestBody CreateProductRequestDto createProductRequestDto){
        return productService.createProduct(createProductRequestDto.getTitle(),createProductRequestDto.getPrice(),createProductRequestDto.getDescription(),createProductRequestDto.getCategory(),createProductRequestDto.getImage());
    }


    @RequestMapping(value = "/products/{id}",method = RequestMethod.DELETE)
    public Product deleteProduct(@PathVariable("id") Long id){
      productService.deleteProduct(id);
      return getProductById(id);
    }

 @RequestMapping(value = "/products/category/{cateGoryType}",method = RequestMethod.GET)
 public List<Product> getProductInSpecificCategory(@PathVariable("cateGoryType") String cateGoryType){

  return getProductInSpecificCategory(cateGoryType);
 }

    @RequestMapping(value = "/products/categories",method = RequestMethod.GET)
    public List<Category> getAllCategories(){
        return productService.getAllCategories();

    }

    @RequestMapping(value = "/products",method = RequestMethod.GET)
    public List<Product> getAllProducts(){
        return productService.getAllProducts();

    }

    @RequestMapping(value="/products/{id}",method=RequestMethod.PUT)
    public Product updateProduct(@PathVariable("id") Long id,@RequestBody CreateProductRequestDto createProductRequestDto){
        return productService.updateProduct(id,createProductRequestDto.getTitle(),createProductRequestDto.getPrice(),createProductRequestDto.getDescription(),createProductRequestDto.getCategory(),createProductRequestDto.getImage());

    }

}
