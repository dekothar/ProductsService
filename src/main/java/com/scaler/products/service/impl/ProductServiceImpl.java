package com.scaler.products.service.impl;

import com.scaler.products.dto.Category;
import com.scaler.products.dto.Product;
import com.scaler.products.exception.ProductNotFoundException;
import com.scaler.products.repository.CategoryRepository;
import com.scaler.products.repository.ProductRepository;
import com.scaler.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("selfproductService")
@Primary
public class ProductServiceImpl implements ProductService {

    private static final String PRODUCT_NOT_FOUND_EXCEPTION = "The User requesting for the Given Product doesn't Exist in the System please try some Other Products";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long productId) throws ProductNotFoundException {
        return productRepository.findByIdIs(productId);
    }

    @Override
    public Product createProduct(String title, double price, String description, String category, String image) {

        Product product = new Product();
        product.setTitle(title);
        product.setPrice(price);
        product.setImage(image);
        product.setActive(1);
        product.setDescription(description);
        product.setCreatedOn(new Date());
        product.setLastModified(new Date());

        Category categoryFroDB = categoryRepository.findByTitle(category);
        if (categoryFroDB == null) {
            categoryFroDB = new Category();
            categoryFroDB.setTitle(category);
        }
        product.setCategory(categoryFroDB);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) throws ProductNotFoundException {
        Product product = productRepository.findByIdIs(id);
        if (product != null && product.getActive() == 1) {
            product.setActive(0);
        }
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getProductInCategory(String categoryType) {
        return productRepository.findAllByCategory_Title(categoryType);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Product updateProduct(Long id, String title, double price, String description, String category, String image) throws ProductNotFoundException {
        Product existingProduct = productRepository.findByIdIs(id);
        if (existingProduct != null) {
            existingProduct.setTitle(title);
            existingProduct.setPrice(price);
            existingProduct.setDescription(description);
            existingProduct.setLastModified(new Date());
            if (existingProduct.getCategory() != null) {
                existingProduct.getCategory().setTitle(category);
            }
            existingProduct.setImage(image);
        } else {
            throw new ProductNotFoundException(PRODUCT_NOT_FOUND_EXCEPTION);
        }
        return productRepository.save(existingProduct);
    }

    @Override
    public String countProduct() {
        List<Product> products = productRepository.findAll();
        StringBuilder product_id = new StringBuilder();
        int count = 0;
        List<String> activeProductId = new ArrayList<>();
        for (Product product : products) {
            count = productRepository.countProductsByIdAndActive(product.getId(), product.getActive());
            if (product.getActive() == 1) {
                activeProductId.add(product.getId().toString());
            }

        }
        product_id.append("Total No of products is ").append(count).append(" product id").append(activeProductId);
        return product_id.toString();
    }

    @Override
    public Product updateSpecificFieldOfProduct(Long id, String title, double price, String description, String category, String image) {
        Product existingProduct = productRepository.findByIdIs(id);
        int fieldUpdated = 0;
        if (!existingProduct.getTitle().equalsIgnoreCase(title)) {
            existingProduct.setTitle(title);
            fieldUpdated++;
        }
        if (existingProduct.getPrice() != price) {
            existingProduct.setPrice(price);
            fieldUpdated++;
        }
        if (!existingProduct.getDescription().equalsIgnoreCase(description)) {
            existingProduct.setTitle(description);
            fieldUpdated++;
        }
        if (!existingProduct.getCategory().getTitle().equalsIgnoreCase(category)) {
            existingProduct.getCategory().setTitle(category);
            fieldUpdated++;
        }
        if (!existingProduct.getImage().equalsIgnoreCase(image)) {
            existingProduct.setImage(image);
            fieldUpdated++;
        }
        if (fieldUpdated != 0) {
            existingProduct.setLastModified(new Date());
            return productRepository.save(existingProduct);
        }
        return null;
    }


}
