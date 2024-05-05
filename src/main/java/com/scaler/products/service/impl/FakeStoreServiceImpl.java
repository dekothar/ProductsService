package com.scaler.products.service.impl;

import com.scaler.products.dto.CategoryDto;
import com.scaler.products.dto.FakeStoreProductDto;
import com.scaler.products.dto.ProductDto;
import com.scaler.products.exception.ProductNotFoundException;
import com.scaler.products.service.ProductService;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service("fakeStoreProductService")
public class FakeStoreServiceImpl implements ProductService {

    private static final String BASE_URL = "https://fakestoreapi.com/products";
    private RestTemplate restTemplate;
    private static final String PRODUCT_NOT_FOUND_EXCEPTION = "The User requesting for the Given Product doesn't Exist in the System please try some Other Products";

    public FakeStoreServiceImpl(RestTemplate temp) {
        this.restTemplate = temp;
    }

    /**
     * This method provides following functionality
     * 1.To Retrieve all the products by calling external Api ie FakeStoreService Api.
     * 2.Map the FakeStoreProductDetails to ProductDetails Dto.
     * 3.Display All the records Stored within ProductDetails Dto.
     * @return list of products
     */
    @Override
    public List<ProductDto> getAllProducts() {
        List<ProductDto> productDetails = new ArrayList<>();
        FakeStoreProductDto[] fakeProductDtos = restTemplate.getForObject(BASE_URL, FakeStoreProductDto[].class);
        for (FakeStoreProductDto dto : fakeProductDtos) {
            ProductDto product = dto.convertToProduct();
            productDetails.add(product);
        }
        return productDetails;
    }

    /**
     *
     * @param pgNo
     * @param noOfRecords
     * @return
     */
    @Override
    public List<ProductDto> getAllProducts(String pgNo, String noOfRecords) {
        return null;
    }

    /**
     * This method provides following functionality
     * 1.To Retrieve the products based on productId by calling external Api ie FakeStoreService Api.
     * 2.if not Present throws an ProductNotFoundException
     * 3.else Map the FakeStoreProductDetails to ProductDetails Dto.
     * 4.Display the records  based on ID Stored within ProductDetails Dto.
     * @param productId
     * @return
     * @throws ProductNotFoundException
     */
    @Override
    public ProductDto getProductById(Long productId) throws ProductNotFoundException {
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject(BASE_URL + "/" + productId, FakeStoreProductDto.class);
        if (fakeStoreProductDto == null || fakeStoreProductDto.getId() == null) {
            throw new ProductNotFoundException(PRODUCT_NOT_FOUND_EXCEPTION);
        }
        return fakeStoreProductDto.convertToProduct();
    }

    /**
     * This method provides following functionality
     * 1.creates the product details by calling external Api ie FakeStoreService Api.
     * 2.Map the FakeStoreProductDetails to ProductDetails Dto.
     * @param title
     * @param price
     * @param description
     * @param category
     * @param image
     * @return ProductDetails
     */
    @Override
    public ProductDto createProduct(String title, double price, String description, String category, String image) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        setFakeStoreDetails(title, price, description, category, image, fakeStoreProductDto);
        FakeStoreProductDto response = restTemplate.postForObject(BASE_URL, fakeStoreProductDto, FakeStoreProductDto.class);
        return response.convertToProduct();
    }

    /**
     * This util method set the Product details in respective attributes.
     * @param title
     * @param price
     * @param description
     * @param cateory
     * @param image
     * @param fakeStoreProductDto
     */
    private static void setFakeStoreDetails(String title, double price, String description, String cateory, String image, FakeStoreProductDto fakeStoreProductDto) {
        fakeStoreProductDto.setTitle(title);
        fakeStoreProductDto.setPrice(price);
        fakeStoreProductDto.setImage(image);
        fakeStoreProductDto.setDescription(description);
        fakeStoreProductDto.setCategory(cateory);
    }

    /**
     * This method provides following functionality
     * 1.To Retrieve the products based on productId by calling external Api ie FakeStoreService Api.
     * 2.else throws ProductNotFoundException.
     * 3.Delete the records temporarily from the FakeStoreService Api.
     * @param id
     * @throws ProductNotFoundException
     */
    @Override
    public void deleteProduct(Long id) throws ProductNotFoundException {
        restTemplate.delete(BASE_URL + "/" + id, this.getProductById(id));
    }

    @Override
    public List<ProductDto> getProductInCategory(String categoryType, int pgNo, int noOfRecords) {
        return null;
    }

    /*@Override
    public List<ProductDto> getProductInCategory(String categoryType) {
        List<ProductDto> productList = new ArrayList<>();
        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForEntity(BASE_URL + "category/" + categoryType, FakeStoreProductDto[].class).getBody();


        for (FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos) {
            if (fakeStoreProductDto.getCategory().equalsIgnoreCase(categoryType)) {
                ProductDto prod = fakeStoreProductDto.convertToProduct();
                productList.add(prod);
            }
        }

        return productList;

    }*/

    /**
     * This method provides following functionality
     * 1.Retrieves all the Categories by calling external Api ie FakeStoreService Api.
     * @return list of Categories
     */
    @Override
    public List<CategoryDto> getAllCategories() {
        String[] categories = restTemplate.getForEntity(BASE_URL + "/categories", String[].class).getBody();
        List<CategoryDto> categoryList = new ArrayList<>();
        for (String category : categories) {
            CategoryDto cate = new CategoryDto();
            cate.setTitle(category);
            categoryList.add(cate);
        }
        return categoryList;
    }

    /**
     * This method provides following functionality
     * 1.Retrieves the product details based on id by calling external Api ie FakeStoreService Api.
     * 2.Updates all the Product and category details.
     * 3.Map the FakeStoreProductDetails to ProductDetails Dto.
     * @param id
     * @param title
     * @param price
     * @param description
     * @param category
     * @param image
     * @return Product
     */
    @Override
    public ProductDto updateProduct(Long id, String title, double price, String description, String category, String image) {
        FakeStoreProductDto request = new FakeStoreProductDto();
        request.setId(id);
        setFakeStoreDetails(title, price, description, category, image, request);
        restTemplate.put(BASE_URL + id, request);
        return request.convertToProduct();
    }
}