package com.scaler.products.controller;

import com.scaler.products.authenticate.AuthenticateProduct;
import com.scaler.products.dto.CategoryDto;
import com.scaler.products.dto.ProductDto;
import com.scaler.products.dto.CreateProductRequestDto;
import com.scaler.products.dto.UserDto;
import com.scaler.products.exception.ProductNotFoundException;
import com.scaler.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products/")
public class ProductController {

    @Autowired
    @Qualifier(value = "selfproductService")
    private ProductService productService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthenticateProduct authenticateProduct;

    /**
     * This Controller used to find the Product details based on product id
     * End Points-> GET/products/{id}
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id, @RequestHeader String authToken) throws ProductNotFoundException {

        UserDto userdto=authenticateProduct.authenticateProduct(authToken);
        if(userdto==null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        // User will need to be authenticated before seeing the Product details
        ProductDto dto=productService.getProductById(id);

        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

    /**
     * This Controller used to create the Product details in the database.
     * End Points-> POST/products/
     * @param createProductRequestDto
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ProductDto createProduct(@RequestBody CreateProductRequestDto createProductRequestDto) {
        return productService.createProduct(createProductRequestDto.getTitle(), createProductRequestDto.getPrice(), createProductRequestDto.getDescription(), createProductRequestDto.getCategory(), createProductRequestDto.getImage());
    }

    /**
     * This Controller used to delete the Product details based on product id.
     * End Points-> DELETE/producs/{id}
     * @param id
     * @return
     * @throws ProductNotFoundException
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ProductDto deleteProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
        productService.deleteProduct(id);
        ProductDto product = productService.getProductById(id);
        // on making the Product as inactive ie deleting the Product
        if (product.getActive() == 1) {
            product.setActive(0);
        }
        return product;
    }

    /**
     * This Controller used to find the  Product details based on Category it is associated with
     * And display certain records on each page based on sorting of ceratin attributes.
     * End Points-> GET/products/category/{cateGoryType}/{pageNo}/{noOfRecords}
     * @param cateGoryType
     * @param pageNo
     * @param noOfRecords
     * @return
     */
    @RequestMapping(value = "category/{cateGoryType}/{pageNo}/{noOfRecords}", method = RequestMethod.GET)
    public List<ProductDto> getProductInSpecificCategory(@PathVariable(value = "cateGoryType", required = true) String cateGoryType, @PathVariable(value = "pageNo", required = false) int pageNo, @PathVariable(value = "noOfRecords", required = false) int noOfRecords) {
        return productService.getProductInCategory(cateGoryType, pageNo, noOfRecords);
    }

    /**
     * This Controller used to find All the  Categories for the Products.
     * End Points-> GET/products/categories
     * @return
     */
    @RequestMapping(value = "categories", method = RequestMethod.GET)
    public List<CategoryDto> getAllCategories() {
        return productService.getAllCategories();
    }

    /**
     * This Controller used to retrieve All the Products Present within the System.
     * AND
     * Display All the Products details if pagination & Sorting is not appplied .
     * else
     * Display only certain  Products details if pagination & Sorting is appplied.
     * End Points-> GET/products or GET/products/{pageNo}/{noOfRecords}
     * @param pageNo
     * @param noOfRecords
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/{pageNo}/{noOfRecords}", ""})
    public ResponseEntity<List<ProductDto>> getAllProducts(@PathVariable(value = "pageNo", required = false) Optional<String> pageNo, @PathVariable(value = "noOfRecords", required = false) Optional<String> noOfRecords) {
        List<ProductDto> products = new ArrayList<>();
        if (pageNo.isPresent() && noOfRecords.isPresent()) {
            products = productService.getAllProducts(pageNo.get(), noOfRecords.get());
        } else {
            products = productService.getAllProducts();
        }
        ResponseEntity<List<ProductDto>> response = new ResponseEntity<>(products, HttpStatus.ACCEPTED);
        return response;
    }

    /**
     * This Controller used to update the Product details for the given pid.
     * End Points-> PUT/products/{id}
     * @param id
     * @param createProductRequestDto
     * @return
     * @throws ProductNotFoundException
     */
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ProductDto updateProduct(@PathVariable("id") Long id, @RequestBody CreateProductRequestDto createProductRequestDto) throws ProductNotFoundException {
        return productService.updateProduct(id, createProductRequestDto.getTitle(), createProductRequestDto.getPrice(), createProductRequestDto.getDescription(), createProductRequestDto.getCategory(), createProductRequestDto.getImage());
    }

    /**
     * This Controller used to count No of Active Products in the System.
     * End Points-> GET/products/count
     * @return
     * @throws ProductNotFoundException
     */
    @RequestMapping(value = "count", method = RequestMethod.GET)
    public String countProducts() throws ProductNotFoundException {
        return productService.countProduct();
    }

    /**
     * This Controller used to to update the Product attribute details for the given pid which was modified in request by User.
     * End Points-> PATCH/products/{id}
     * @param id
     * @param createProductRequestDto
     * @return
     * @throws ProductNotFoundException
     */
    @RequestMapping(value = "{id}", method = RequestMethod.PATCH)
    public ProductDto updateSpecificFieldOfProduct(@PathVariable("id") Long id, @RequestBody CreateProductRequestDto createProductRequestDto) throws ProductNotFoundException {
        return productService.updateSpecificFieldOfProduct(id, createProductRequestDto.getTitle(), createProductRequestDto.getPrice(), createProductRequestDto.getDescription(), createProductRequestDto.getCategory(), createProductRequestDto.getImage());
    }
}