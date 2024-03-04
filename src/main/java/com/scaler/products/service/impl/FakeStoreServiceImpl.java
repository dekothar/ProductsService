package com.scaler.products.service.impl;

import com.scaler.products.dto.Category;
import com.scaler.products.dto.FakeStoreProductDto;
import com.scaler.products.dto.Product;
import com.scaler.products.service.ProductService;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
public class FakeStoreServiceImpl implements ProductService
{

    private static final String BASE_URL="https://fakestoreapi.com/products";
    private RestTemplate restTemplate;

    public FakeStoreServiceImpl(RestTemplate temp){
        this.restTemplate=temp;
    }
    @Override
    public List<Product> getAllProducts() {
        List<Product> productDetails=new ArrayList<>();
        FakeStoreProductDto [] fakeProductDtos=restTemplate.getForObject(BASE_URL,FakeStoreProductDto[].class);
        for(FakeStoreProductDto dto:fakeProductDtos){
            Product product=dto.convertToProduct();
            productDetails.add(product);
        }
        return productDetails;

    }

    @Override
    public Product getProduct(Long productId) {
        FakeStoreProductDto fakeStoreProductDto= restTemplate.getForObject("https://fakestoreapi.com/products/" + productId, FakeStoreProductDto.class);
     return  fakeStoreProductDto.convertToProduct();
    }

    @Override
    public Product createProduct(String title,double price,String description,String cateory,String image){
        FakeStoreProductDto fakeStoreProductDto=new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(title);
        fakeStoreProductDto.setPrice(price);
        fakeStoreProductDto.setDescription(description);
        fakeStoreProductDto.setCategory(cateory);
        fakeStoreProductDto.setImage(image);
        FakeStoreProductDto response=restTemplate.postForObject(BASE_URL,fakeStoreProductDto,FakeStoreProductDto.class);
        return response.convertToProduct();
    }

    @Override
    public void deleteProduct(Long id) {
        restTemplate.delete(BASE_URL + "/" + id,getProduct(id));
    }

    @Override
    public List<Product> getProductInCategory(String categoryType) {
        List<Product> productList=new ArrayList<>();
        FakeStoreProductDto [] dto=restTemplate.getForEntity(BASE_URL +"category/" + categoryType,FakeStoreProductDto[].class).getBody();
        for(FakeStoreProductDto fake:dto){
            Product prod=fake.convertToProduct();
            productList.add(prod);
        }

         return productList;

    }

    @Override
    public List<Category> getAllCategories() {
        String [] categoriies=restTemplate.getForEntity(BASE_URL + "/categories",String[].class).getBody();
        List<Category> categories=new ArrayList<>();
        for(String category:categoriies){
            Category cate=new Category();
            cate.setTitle(category);
            categories.add(cate);
        }
        return categories;
    }

    @Override
    public Product updateProduct(Long id,String title,double price,String description,String cateory,String image) {

        FakeStoreProductDto request=new FakeStoreProductDto();
        request.setId(id);
        request.setTitle(title);
        request.setPrice(price);
        request.setImage(image);
        request.setDescription(description);
        request.setCategory(cateory);
        restTemplate.put(BASE_URL+id,request);
        return request.convertToProduct();

    }

}
