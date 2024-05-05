package com.scaler.products.service.impl;

import com.scaler.products.dto.CategoryDto;
import com.scaler.products.dto.ProductDto;
import com.scaler.products.exception.ProductNotFoundException;
import com.scaler.products.repository.CategoryRepository;
import com.scaler.products.repository.ProductRepository;
import com.scaler.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("selfproductService")
@Primary
public class ProductServiceImpl implements ProductService {

    private static final String PRODUCT_NOT_FOUND_EXCEPTION = "The User requesting for the Given Product doesn't Exist in the System please try some Other Products";
    private static final String TITLE = "title";
    private static final String PRICE = "price";

    private static final String TOTAL_COUNT_PRODUCT="Total No of active Products count are" + " ";
    private static final  String EMPTY_STRING=" ";

    private static final String PRODUCT_IDS="And there respective product ids are" + " ";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * This method provides following functionality
     * 1.To Retrieve all the products present in database.
     * 2.Display certain records on each page.
     * 3.Sort the records based on title in ascending order and price in descending order.
     * @param pgNo
     * @param noOfRecords
     * @return list of products
     */
    @Override
    public List<ProductDto> getAllProducts(final String pgNo, final String noOfRecords) {
        Sort sortByTitle = Sort.by(TITLE).ascending();
        Sort sortByPrice = Sort.by(PRICE).descending();
        Sort groupSort = sortByTitle.and(sortByPrice);
        Pageable page = PageRequest.of(Integer.parseInt(pgNo), Integer.parseInt(noOfRecords), groupSort);
        return productRepository.findAll(page).get().toList();
    }

    /**
     * This method provides following functionality
     * 1.To Retrieve all the products present in database.
     * @return list of products
     */
    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * This method provides following functionality
     * 1.To Retrieve the products based on productId from the database
     * 2.if not Present throws an ProductNotFoundException
     * @param productId
     * @return Product
     * @throws ProductNotFoundException
     */
    @Override
    public ProductDto getProductById(Long productId) throws ProductNotFoundException {
        return productRepository.findByIdIs(productId);
    }

    /**
     * This method provides following functionality
     * 1.creates the product details
     * 2. retrieve the category from database if present
     * 3.else create new Category details.
     * 4.Add the Category details in Product details.
     * 5.Save the Product Details in database.
     * @param title
     * @param price
     * @param description
     * @param category
     * @param image
     * @return Product
     */
    @Override
    public ProductDto createProduct(String title, double price, String description, String category, String image) {
        ProductDto product = setProductDetails(title, price, description, image);
        CategoryDto categoryFroDB = categoryRepository.findByTitle(category);
        if (categoryFroDB == null) {
            categoryFroDB = new CategoryDto();
            categoryFroDB.setTitle(category);
        }
        product.setCategory(categoryFroDB);
        return productRepository.save(product);
    }

    /**
     * This util method set the Product details in respective attributes.
     * @param title
     * @param price
     * @param description
     * @param image
     * @return ProductDto
     */
    private static ProductDto setProductDetails(final String title, final double price, final String description, final String image) {
        ProductDto product = new ProductDto();
        product.setTitle(title);
        product.setPrice(price);
        product.setImg(image);
        product.setActive(1);
        product.setDesc(description);
        product.setCreatedOn(new Date());
        product.setLastModified(new Date());
        return product;
    }

    /**
     * This method provides following functionality
     * 1.Retrieves the Product details based on ID from database if it is present.
     * 2.else throws ProductNotFoundException.
     * 3.mark the active field as 0 indicating that Product details doesn't exist in database.
     * @param id
     * @throws ProductNotFoundException
     */
    @Override
    public void deleteProduct(Long id) throws ProductNotFoundException {
        ProductDto product = productRepository.findByIdIs(id);
        if (product != null && product.getActive() == 1) {
            product.setActive(0);
        }
        productRepository.deleteById(id);
    }

    /**
     * This method provides following functionality
     * 1.Retrieves the Product details based on Category Type.
     * 2.Display all CategoryType.
     * @param categoryType
     * @return list of Products
     */
    @Override
    public List<ProductDto> getProductInCategory(String categoryType) {
        return productRepository.findAllByCategory_TitleLike(categoryType);
    }

    /**
     * This method provides following functionality
     * 1.Retrieves the Product details based on Category Type.
     * 2.Display certain records ie CategoryType on each page.
     * @param categoryType
     * @param pgNo
     * @param noOfRecords
     * @return list of Products
     */
    @Override
    public List<ProductDto> getProductInCategory(String categoryType, int pgNo, int noOfRecords) {
        Pageable page = PageRequest.of(pgNo, noOfRecords);
        return productRepository.findAllByCategory_Title(categoryType, page);
    }

    /**
     * This method provides following functionality
     * 1.Retrieves all the Categories from the database.
     * @return list of Categories
     */
    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * This method provides following functionality
     * 1.Retrieves the product details based on id from the database if present.
     * 2.else throw the productNotFound Exception.
     * 3.Updates all the Product and category details.
     * 4.Save the details in database.
     * @param id
     * @param title
     * @param price
     * @param description
     * @param category
     * @param image
     * @return Products
     * @throws ProductNotFoundException
     */
    @Override
    public ProductDto updateProduct(Long id, String title, double price, String description, String category, String image) throws ProductNotFoundException {
        ProductDto existingProduct = productRepository.findByIdIs(id);
        if (existingProduct != null) {
            existingProduct.setTitle(title);
            existingProduct.setPrice(price);
            existingProduct.setDesc(description);
            existingProduct.setLastModified(new Date());
            if (existingProduct.getCategory() != null) {
                existingProduct.getCategory().setTitle(category);
            }
            existingProduct.setImg(image);
        } else {
            throw new ProductNotFoundException(PRODUCT_NOT_FOUND_EXCEPTION);
        }
        return productRepository.save(existingProduct);
    }

    /**
     * This method provides following functionality
     * 1.Retrieves all the Products details from the database.
     * 2.Using Stream Api filter out the Product Details which are active.
     * 3.Store the respective ProductIds for the Active Products.
     * 3.Display no of Products which are active with their ProductID.
     * @return String
     */
    @Override
    public String countProduct() {
        List<ProductDto> activeProducts=productRepository.findAll().stream().filter(x->x.getActive()==1).toList();
        long activeProductsCount = activeProducts.stream().count();
        StringBuilder result = new StringBuilder();
        List<Long> product_id=new ArrayList<>();
        activeProducts.forEach(x->product_id.add(x.getId()));
        result.append(TOTAL_COUNT_PRODUCT).append(activeProductsCount).append(EMPTY_STRING).append(PRODUCT_IDS).append(product_id);
        return result.toString();
    }

    /**
     * This method provides following functionality
     * 1.Retrieves the product details based on id from the database if present.
     * 2.else throw the productNotFound Exception.
     * 3.search for each field against field updated by user and only Update the modified field other field will remain as it is.
     * 4.If no field is modified than records will not be saved in database.and msg will be displayed as no field was modified.
     * 5.Other-Wise modified field be updated in database.
     * @param id
     * @param title
     * @param price
     * @param description
     * @param category
     * @param image
     * @return Product
     */
    @Override
    public ProductDto updateSpecificFieldOfProduct(Long id, String title, double price, String description, String category, String image) throws ProductNotFoundException {
        ProductDto existingProduct = productRepository.findByIdIs(id);
        int fieldUpdated = 0;
        if (!existingProduct.getTitle().equalsIgnoreCase(title)) {
            existingProduct.setTitle(title);
            fieldUpdated++;
        }
        if (existingProduct.getPrice() != price) {
            existingProduct.setPrice(price);
            fieldUpdated++;
        }
        if (!existingProduct.getDesc().equalsIgnoreCase(description)) {
            existingProduct.setTitle(description);
            fieldUpdated++;
        }
        if (!existingProduct.getCategory().getTitle().equalsIgnoreCase(category)) {
            existingProduct.getCategory().setTitle(category);
            fieldUpdated++;
        }
        if (!existingProduct.getImg().equalsIgnoreCase(image)) {
            existingProduct.setImg(image);
            fieldUpdated++;
        }
        if (fieldUpdated != 0) {
            existingProduct.setLastModified(new Date());
            return productRepository.save(existingProduct);
        }
        return null;
    }
}