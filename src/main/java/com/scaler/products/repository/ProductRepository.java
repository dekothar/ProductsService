package com.scaler.products.repository;

import com.scaler.products.dto.Product;
import com.scaler.products.projection.productTitleAndId;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product save(Product product);

    Product findByIdIs(Long id);

    List<Product> findAll();

    @Query("select p from Product  p where p.category.title= :title and p.id = :id")
    Product getParticularProductName(@Param("title") String title, @Param("id") Long id);

    @Query("select p.title as product_title,p.id as product_id from Product p where p.category.id= :categoryId")
    List<productTitleAndId> getProductWithTitleAndId(@Param("categoryId") Long categoryId);

    List<Product> findAllByCategory_Title(String title);

    //List<Product> findAllByCategory_TitleLike(String title);

    int countProductsByIdAndActive(Long id, int active);

    boolean existsProductByActive(int actiive);
}
